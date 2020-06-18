package com.cmcc.algo.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cmcc.algo.common.APIException;
import com.cmcc.algo.common.utils.TokenManager;

import com.cmcc.algo.entity.User;
import com.cmcc.algo.mapper.UserMapper;
import com.cmcc.algo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;


/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author hjy
 * @since 2020-05-25
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

     @Autowired
     private UserMapper userMapper;


     @Override
     public User userLogin(String username, String password) {
          User user = userMapper.findByUserName(username);
          if (user == null) {
               throw new APIException("用户不存在！");
          }
          if (!username.equals(user.getUsername()) || !password.equals(user.getPassword())) {
               throw new APIException("用户名或者密码无效！");
          }
          String token = TokenManager.createJWT(String.valueOf(user.getId()), username, true);
          user.setToken(token);
          return user;
     }

     @Override
     public User findById(String userId) {
          return userMapper.findById(userId);
     }


     @Override
     public User userRegister(String username, String password) {
          if (userMapper.findByUserName(username) != null) {
               throw new APIException("用户已存在！");
          }
          User user = new User();
          user.setUuid(UUID.randomUUID().toString().replaceAll("-", ""));
          user.setUsername(username);
          user.setPassword(password);
          return user;
     }

}
