package com.cmcc.algo.common.utils;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Calendar;
import java.util.Date;

public class TokenManager {

     static class Constant {
          public static final String JWT_ID = "5236A";        //jwtid
          public static final String JWT_SECERT = "vbrd0000df7adsab41r1asv413c0005d";    //密匙
//          public static final long JWT_TTL = 5 * 1000;     //token有效时间
     }

     private static SecretKey generalKey() {
          byte[] encodedKey = Base64.decode(Constant.JWT_SECERT);
          SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
          return key;
     }


     /**
      * 签发JWT
      *
      * @return
      * @throws Exception
      */

     private static Date getTodayZeroTime() {
          Calendar calendar = Calendar.getInstance();
          calendar.setTime(new Date());
          calendar.set(Calendar.HOUR_OF_DAY, 23);
          calendar.set(Calendar.MINUTE, 59);
          calendar.set(Calendar.SECOND, 59);
          return calendar.getTime();
     }

     public static String createJWT(String id, String openId, boolean ttl) {
          //jjwt已经发封装好了所有的请求头，这里是指定签名算法
          SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
          //生成jwt的时间
          long nowMillis = System.currentTimeMillis();
          Date now = new Date(nowMillis);
          //获取服务器端的密钥，这个不能贡献出去，贡献出去后就可以随意伪造了
          SecretKey secretKey = generalKey();
          JwtBuilder builder = Jwts.builder().setId(id).setSubject(openId)
                  .setIssuedAt(now).signWith(signatureAlgorithm, secretKey);
          if (ttl) {
               Date zeroTime = getTodayZeroTime();
               long expMillis = nowMillis + (zeroTime.getTime() - nowMillis);
               Date expDate = new Date(expMillis);
               builder.setExpiration(expDate);
          }
/*          if (ttlMillis >= 0) {
               long expMillis = nowMillis + ttlMillis;
               Date expDate = new Date(expMillis);
               builder.setExpiration(expDate);
          }*/
          return builder.compact();
     }

     /**
      * 解析JWT字符串
      *
      * @param jwt
      * @return
      * @throws Exception
      */
     public static Claims parseJWT(String jwt) throws Exception {
          SecretKey secretKey = generalKey();
          return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwt).getBody();
     }

}
