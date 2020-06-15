package com.cmcc.algo.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cmcc.algo.common.CommonResult;
import com.cmcc.algo.entity.FederationEntity;
import com.cmcc.algo.entity.User;
import com.cmcc.algo.entity.UserFederation;
import com.cmcc.algo.service.IFederationService;
import com.cmcc.algo.service.IUserFederationService;
import com.cmcc.algo.service.IUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 用户信息表 前端控制器
 * </p>
 *
 * @author hjy
 * @since 2020-05-25
 */
@RestController
@RequestMapping("/user")
public class UserController {

     @Autowired
     IUserService userService;

     @Autowired
     IFederationService federationService;

     @Autowired
     IUserFederationService userFederationService;


     /**
      * 登录
      *
      * @param loginUser
      * @return
      */
     @PostMapping("/login")
     public CommonResult login(@RequestBody User loginUser) {

          if (StringUtils.isEmpty(loginUser.getUsername()) || StringUtils.isEmpty(loginUser.getPassword())) {
               return CommonResult.fail("用户名或者密码不能为空");
          }
          User user = userService.userLogin(loginUser.getUsername(), loginUser.getPassword());
          return CommonResult.success("登陆成功！", user);
     }

     /**
      * 查询用户列表展示
      *
      * @param user
      * @return
      */
     @GetMapping("/list")
     @Transactional
     public CommonResult List(User user) {
          QueryWrapper queryWrapper = new QueryWrapper();
          if (org.apache.commons.lang.StringUtils.isNotBlank(user.getUsername())) {
               queryWrapper.eq("username", user.getUsername());
          }
          queryWrapper.eq("del_flag", 0);
          IPage<User> page = userService.page(new Page<>(), queryWrapper);
          for (User guestUser : page.getRecords()) {
               List<FederationEntity> list = federationService.findListByGuest(guestUser.getId().toString());
               guestUser.setFederationList(list);

               List<UserFederation> list1 = userFederationService.list(new QueryWrapper<UserFederation>().eq("user_id", guestUser.getId().toString()));
               List<FederationEntity> list2 = new ArrayList<>();
               for (UserFederation userFederation : list1) {
                    FederationEntity federationEntity = federationService.getOne(Long.valueOf(userFederation.getFederationId()));
                    list2.add(federationEntity);
               }
               guestUser.setJoinFederation(list2);
          }
          return CommonResult.success("查询成功", page);
     }

     /**
      * 注销用户
      *
      * @param userId
      * @return
      */
     @DeleteMapping("/del/{userId}")
     @Transactional
     public CommonResult delFlag(@PathVariable(name = "userId") String userId) {
          User user = userService.findById(userId);
          if (user == null) {
               return CommonResult.fail("参数有误,请核对");
          }
          user.setDelFlag(1);
          userService.updateById(user);
          return CommonResult.success("注销成功");
     }

     /**
      * 用户基础信息详情
      *
      * @param userId
      * @return
      */
     @GetMapping("/detail/{userId}")
     public CommonResult getDetail(@PathVariable(name = "userId") String userId) {
          User user = userService.findById(userId);
          return CommonResult.success("查询成功", user);
     }

}
