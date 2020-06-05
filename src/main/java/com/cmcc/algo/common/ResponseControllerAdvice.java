package com.cmcc.algo.common;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ClassUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.MethodParameter;
import org.springframework.data.util.CastUtils;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice(basePackages = {"com.cmcc.algo.controller"})
public class ResponseControllerAdvice implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> aClass) {
        // 如果接口返回的类型本身就是CommonResult那就没有必要进行额外的操作，返回false
        return !returnType.getGenericParameterType().equals(CommonResult.class);
    }

    @Override
    public Object beforeBodyWrite(Object data, MethodParameter returnType, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest request, ServerHttpResponse response) {
        // String类型不能直接包装，所以要进行些特别的处理
        if (returnType.getGenericParameterType().equals(String.class)) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                // 将数据包装在CommonResult里后，再转换为json字符串响应给前端
                return objectMapper.writeValueAsString(CommonResult.success(data));
            } catch (JsonProcessingException e) {
                throw new APIException("返回String类型错误");
            }
        }
//        if (returnType.getGenericParameterType().equals(IPage.class)) {
//            IPage result = Convert.convert(IPage.class, data);
//            CommonResult.success(result.getRecords(), result.getTotal(), result.getCurrent(), result.getSize());
//        }
        // 将原本的数据包装在CommonResult里
        return CommonResult.success(data);
    }
}
