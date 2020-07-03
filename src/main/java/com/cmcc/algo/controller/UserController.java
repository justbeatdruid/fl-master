package com.cmcc.algo.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cmcc.algo.common.APIException;
import com.cmcc.algo.common.CommonResult;
import com.cmcc.algo.common.ResultCode;
import com.cmcc.algo.constant.LoginConstant;
import com.cmcc.algo.entity.*;
import com.cmcc.algo.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.RoleResult;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * <p>
 * 用户信息表 前端控制器
 * </p>
 *
 * @author hjy
 * @since 2020-05-25
 */
@Api(tags = "用户管理接口")
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

     @Autowired
     IUserService userService;

     @Autowired
     IFederationService federationService;

     @Autowired
     IUserFederationService userFederationService;

     @Autowired
     IRoleService roleService;

     @Autowired
     IRoleMenuService roleMenuService;

     @Autowired
     IMenuService menuService;

     /**
      * 登录
      *
      * @param loginUser
      * @return
      */
     @ApiOperation(value = "用户登录", notes = "根据用户名密码进行登录")
     @PostMapping("/login")
     public CommonResult login(@RequestBody User loginUser, HttpSession session) {

          if (StringUtils.isEmpty(loginUser.getUsername()) || StringUtils.isEmpty(loginUser.getPassword())) {
               return CommonResult.fail("用户名或者密码不能为空");
          }
          User user = userService.userLogin(loginUser.getUsername(), loginUser.getPassword());
          if (user.getDelFlag().equals(3)) {
               throw new APIException(ResultCode.FORBIDDEN, "已注销,不可登录!!!", new int[0]);
          }
          //查询用户权限列表
          Set permissionSet = new HashSet();
          List<Role> roleList = roleService.list(new QueryWrapper<Role>().eq("rolename", user.getRole()));
          for (Role role : roleList) {
               List<RoleMenu> roleMenuList = roleMenuService.list(
                       new QueryWrapper<RoleMenu>().eq("role_id", role.getId()));
               for (RoleMenu roleMenu : roleMenuList) {
                    Menu menu = menuService.getById(roleMenu.getMenuId());
                    if (menu == null) {
                         return CommonResult.success("资源不存在", new int[0]);
                    }
                    if (StringUtils.isNotEmpty(menu.getPermissionCode())) {
                         permissionSet.add(menu.getPermissionCode());
                    }
               }
          }
          user.setPermissionCode(permissionSet);
          session.setAttribute(LoginConstant.SESSION_USER, user);
//          QueryWrapper queryWrapper = new QueryWrapper();
//          queryWrapper.eq("user_id", user.getId());
//          List<UserFederation> userFederationList = userFederationService.list(queryWrapper);
//          Set roles = new HashSet();
//          for (UserFederation userFederation : userFederationList) {
//               if (userFederation.getStatus().equals("0")) {
//                    roles.add("host");
//               } else if (userFederation.getStatus().equals("1")) {
//                    roles.add("guest");
//               } else {
//                    roles.add("");
//               }
//               user.setRoles(roles);
//          }
          return CommonResult.success("登陆成功！", user);
     }

     /**
      * 用户注册
      *
      * @param registerUser
      * @return
      */
     @PostMapping("/register")
     public CommonResult register(@RequestBody User registerUser) {

          if (StringUtils.isEmpty(registerUser.getUsername()) || StringUtils.isEmpty(registerUser.getPassword())) {
               return CommonResult.fail("用户名或者密码不能为空");
          }
          User user = userService.userRegister(registerUser.getUsername(), registerUser.getPassword());
          userService.save(user);
          return CommonResult.success("注册成功！", user);
     }

     /**
      * 注销用户
      *
      * @param userId
      * @return
      */
     @ApiOperation("注销")
     @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "String", paramType = "path")
     @DeleteMapping("/del/{userId}")
     @Transactional(rollbackFor = Exception.class)
     public CommonResult delFlag(@PathVariable("userId") String userId) {
          User user = userService.findById(userId);
          if (user == null) {
               return CommonResult.fail(ResultCode.NOT_FOUND, new int[0]);
          }
          if (user.getDelFlag().equals(3)) {
               throw new APIException(ResultCode.REQUEST_ERROR, "用户已注销", new int[0]);
          }
          //0:默认,3:注销
          user.setDelFlag(3);
          userService.updateById(user);
          return CommonResult.success("注销成功", new int[0]);
     }

     /**
      * 查询用户列表展示
      *
      * @param
      * @return
      */
//     @PermissionCode("user:list")
     @ApiOperation(value = "用户列表展示")
     @GetMapping("/list")
     public CommonResult list() {
          QueryWrapper<User> queryWrapper = new QueryWrapper();
          queryWrapper.eq("del_flag", 0);
          IPage<User> page = userService.page(new Page<>(), queryWrapper);
          for (User user : page.getRecords()) {
               List<FederationEntity> guestList = federationService.findListByGuest(String.valueOf(user.getId()));
               user.setCreatedFederation(guestList);
               QueryWrapper<UserFederation> queryWrapper1 = new QueryWrapper<>();
               queryWrapper1.eq("user_id", String.valueOf(user.getId()));
               queryWrapper1.eq("status", "0");
               List<UserFederation> userFederationList = userFederationService.list(queryWrapper1);
               List<FederationEntity> list = new ArrayList<>();
               for (UserFederation userFederation : userFederationList) {
                    FederationEntity federationEntity = federationService.getOne(userFederation.getFederationUUid());
                    if (federationEntity != null) {
                         list.add(federationEntity);
                    }
               }
               user.setPartakeFederation(list);
          }
          return CommonResult.success("查询成功", page);
     }

     /**
      * 用户搜索
      *
      * @return
      */
     @ApiOperation(value = "用户搜索")
     @ApiImplicitParam(name = "username", value = "用户名", required = true, dataType = "String", paramType = "path")
     @GetMapping("/select")
     public CommonResult searchUser(@RequestParam String username) {
          QueryWrapper queryWrapper = new QueryWrapper();
          if (org.apache.commons.lang.StringUtils.isNotBlank(username)) {
               queryWrapper.like("username", username);
          }
          IPage<User> page = userService.page(new Page<>(), queryWrapper);
          for (User user : page.getRecords()) {
               List<FederationEntity> guestList = federationService.findListByGuest(String.valueOf(user.getId()));
               user.setCreatedFederation(guestList);
               QueryWrapper<UserFederation> queryWrapper1 = new QueryWrapper<>();
               queryWrapper1.eq("user_id", String.valueOf(user.getId()));
               queryWrapper1.eq("status", "0");
               List<UserFederation> userFederationList = userFederationService.list(queryWrapper1);
               List<FederationEntity> list = new ArrayList<>();
               for (UserFederation userFederation : userFederationList) {
                    FederationEntity federationEntity = federationService.getOne(userFederation.getFederationUUid());
                    if (federationEntity != null) {
                         list.add(federationEntity);
                    }
               }
               user.setPartakeFederation(list);
          }
          return CommonResult.success("查询成功", page);
     }


     /**
      * 用户基础信息详情
      *
      * @param userId
      * @return
      */
     @ApiOperation("用户信息详情")
     @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "String", paramType = "path")
     @GetMapping("/detail/{userId}")
     public CommonResult getDetail(@PathVariable(name = "userId") String userId) {
          User user = userService.findById(userId);
          return CommonResult.success("查询成功", user);
     }

}
