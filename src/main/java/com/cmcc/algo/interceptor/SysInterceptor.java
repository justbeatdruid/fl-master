package com.cmcc.algo.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.cmcc.algo.common.APIException;
import com.cmcc.algo.common.CommonResult;
import com.cmcc.algo.common.CommonResultMessage;
import com.cmcc.algo.common.ResultCode;
import com.cmcc.algo.common.utils.TokenManager;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.crypto.Data;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * 拦截器
 */
@Component
public class SysInterceptor extends HandlerInterceptorAdapter {

     private final static Set<String> DO_NOT_URI_SET = new HashSet<>();

     static {
          DO_NOT_URI_SET.add("/com/cmcc/algo/datafusion/api/v1/user/login");
          DO_NOT_URI_SET.add("/com/cmcc/algo/datafusion/api/v1/user/register");
//          System.out.println("-------------->加载拦截器");
     }

     private boolean errorReturn(HttpServletResponse response, String errMsg, int errCode) throws IOException {
          CommonResult rd = CommonResultMessage.fail(errMsg, errCode);
          response.setContentType("application/json");
          PrintWriter pw = response.getWriter();
          pw.write(JSONObject.toJSONString(rd));
          pw.flush();
          pw.close();
          return false;
     }

     @Override
     public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
          String requestUrl = request.getRequestURI();
          String token = request.getHeader("token");
          if (!DO_NOT_URI_SET.contains(requestUrl)) {
               if (StringUtils.isEmpty(token)) {
                    throw new APIException(ResultCode.FORBIDDEN, "无效token");
               }
               Claims cla = null;
               try {
                    cla = TokenManager.parseJWT(token);
               } catch (Exception e) {
                    throw new APIException(ResultCode.FORBIDDEN,"token失效,请重新登录！");
               }
               String openId = cla.getSubject();
               if (StringUtils.isEmpty(openId)) {
                    throw new APIException(ResultCode.FORBIDDEN, "无效token");
               }
               Date d = cla.getExpiration();
               Date now = new Date();
               if (d == null) {
                    throw new APIException(ResultCode.FORBIDDEN, "无效token");
               } else if (now.getTime() > d.getTime()) {
                    throw new APIException(ResultCode.FORBIDDEN, "Token超时,请重新登录");
               }
          }
          return true;
     }
}
