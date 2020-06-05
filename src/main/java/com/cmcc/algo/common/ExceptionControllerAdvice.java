package com.cmcc.algo.common;

import cn.hutool.json.JSONException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionControllerAdvice {
    @ExceptionHandler(APIException.class)
    public CommonResult handleAPIException(APIException e){
        return CommonResult.fail(e.getResultCode(),e.getMessage(),e.getData());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CommonResult handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        return CommonResult.fail(ResultCode.NOT_FOUND, "参数校验失败", e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }

    @ExceptionHandler(JSONException.class)
    public CommonResult handleJSONException(JSONException e){
        return CommonResult.fail(ResultCode.NOT_FOUND, "参数转换失败", e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public CommonResult handleException(Exception e){
        return CommonResult.fail(ResultCode.SYSTEM_ERROR, e.getMessage());
    }
}
