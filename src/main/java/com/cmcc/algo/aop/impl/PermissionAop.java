package com.cmcc.algo.aop.impl;


import com.cmcc.algo.aop.bean.PermissionCode;
import com.cmcc.algo.common.APIException;
import com.cmcc.algo.common.ResultCode;
import com.cmcc.algo.constant.LoginConstant;
import com.cmcc.algo.entity.User;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.servlet.http.HttpSession;
import java.util.Set;

@Aspect
@Component
public class PermissionAop {

     @Autowired
     private HttpSession session;

     @Around("@annotation(permissionCode)") // 权限的判断只能使用@Around
     public Object hanlder(ProceedingJoinPoint point, PermissionCode permissionCode) {


          // 1.获取当前登录用户的权限
          User user = (User) session.getAttribute(LoginConstant.SESSION_USER);
          if (user == null) {
               throw new APIException(ResultCode.FORBIDDEN, "数据异常,请重新登录", new int[0]);
          }
          Set permission = user.getPermissionCode();

          // 2.获取用户正在访问方法的权限
          String pCode = permissionCode.value();

          // 获取方法形参
          Object result = null;

          // 3.判断用户的权限集合中是否包含该pCode
          if (permission.contains(pCode)) {
               // 有权限 往下调用
               try {
                    // 调用Controller
                    result = point.proceed(point.getArgs());
               } catch (Throwable throwable) {
                    throwable.printStackTrace();
               }
          } else {
               // 没有权限
               throw new APIException(ResultCode.FORBIDDEN, "你没有权限访问这个资源", new int[0]);
          }
          return result;
     }
}
