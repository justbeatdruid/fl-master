package com.cmcc.algo.controller;


import com.cmcc.algo.common.CommonResult;
import com.cmcc.algo.entity.User;
import com.cmcc.algo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping(value = "/list")
    public CommonResult getUserList(){
        List<User> userList = userService.list();
        return CommonResult.success(userList);
    }
}
