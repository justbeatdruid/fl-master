package com.cmcc.algo.common.utils;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;

/**
 * 短信发送工具类，返回生成的随机验证码
 */
public class SMSUtil {
     public static String send(String phone) {
          DefaultProfile profile = DefaultProfile.getProfile(
                  "cn-hangzhou",
                  "LTAI4Fsw25f3u8ZSNMLArGWg",
                  "PIB9b7m42T6Tyd1GLGhxaKVAZcN1Z8");
          IAcsClient client = new DefaultAcsClient(profile);
          CommonRequest request = new CommonRequest();
          request.setSysMethod(MethodType.POST);
          request.setSysDomain("dysmsapi.aliyuncs.com");
          request.setSysVersion("2017-05-25");
          request.setSysAction("SendSms");
          request.putQueryParameter("RegionId", "cn-hangzhou");
          request.putQueryParameter("PhoneNumbers", phone);
          request.putQueryParameter("SignName", "多元数据System");
          request.putQueryParameter("TemplateCode", "SMS_196147907");
          String verifyCode = StringUtils.getVerifyCode();
          request.putQueryParameter("TemplateParam","{\"code\":" + verifyCode + "}");
          try {
               CommonResponse response = client.getCommonResponse(request);
               System.out.println(response.getData());
          } catch (ServerException e) {
               e.printStackTrace();
          } catch (ClientException e) {
               e.printStackTrace();
          }
          return verifyCode;
     }

     public static void main(String[] args) {
          System.out.println(send("************"));
     }
}