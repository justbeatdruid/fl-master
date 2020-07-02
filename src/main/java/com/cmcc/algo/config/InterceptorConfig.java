package com.cmcc.algo.config;

import com.cmcc.algo.interceptor.SysInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 拦截器配置类
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

     @Autowired
     private SysInterceptor sysInterceptor;


     @Override
     public void addInterceptors(InterceptorRegistry registry) {
          registry.addInterceptor(sysInterceptor).addPathPatterns("/**")// 添加需要拦截请求的路径
                  // 去除需要拦截请求的路径
                  .excludePathPatterns("/swagger-resources/**", "/webjars/**", "/swagger-ui.html/**", "/doc.html/**");
     }

     @Override
     public void addResourceHandlers(ResourceHandlerRegistry registry) {
          registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
          registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
          registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
     }
}
