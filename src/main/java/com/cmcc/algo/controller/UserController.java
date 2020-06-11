package com.cmcc.algo.controller;


import com.alibaba.druid.util.StringUtils;
import com.cmcc.algo.common.CommonResult;
import com.cmcc.algo.common.exception.CustomException;
import com.cmcc.algo.common.exception.CustomExceptionType;
import com.cmcc.algo.entity.User;
import com.cmcc.algo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


     @PostMapping("/login")
     public CommonResult login(@RequestBody User loginUser) {

          if (StringUtils.isEmpty(loginUser.getUsername()) || StringUtils.isEmpty(loginUser.getPassword())) {
               return CommonResult.fail(new CustomException(CustomExceptionType.USER_INPUT_ERROR, "用户名或者密码不能为空"));
          }
          User user = userService.userLogin(loginUser.getUsername(), loginUser.getPassword());
          return CommonResult.success("登陆成功！",user);
     }

}
