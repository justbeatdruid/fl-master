package com.cmcc.algo.controller;


import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.cmcc.algo.common.APIException;
import com.cmcc.algo.common.CommonResult;
import com.cmcc.algo.common.ResultCode;
import com.cmcc.algo.common.utils.TokenManager;
import com.cmcc.algo.entity.Algorithm;
import com.cmcc.algo.service.IAlgorithmService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 * 算法信息表 前端控制器
 * </p>
 *
 * @author hjy
 * @since 2020-05-25
 */
@Slf4j
@Api(tags = "算法接口")
@RestController
@RequestMapping("/algorithm")
public class AlgorithmController {
    @Autowired
    IAlgorithmService algorithmService;

    @ApiOperation(value = "获取算法列表", notes = "获取算法列表")
    @PostMapping("/list")
    public CommonResult getAlgorithmList(@RequestHeader String token,  @RequestBody String request){
        String userId = "";
        try {
            userId = TokenManager.parseJWT(token).getId();
            log.info("get user id", userId);
        }catch(Exception e) {
            log.error("cannot parse token", e.getMessage(), e);
            throw new APIException("token无效");
        }

        JSONObject json = Optional.ofNullable(JSONUtil.parseObj(request)).orElseThrow(()->new APIException(ResultCode.PARAMETER_CHECK_ERROR, "请求参数为空"));
        Integer federationType = Optional.ofNullable(json.getInt("federation_type")).orElseThrow(()->new APIException(ResultCode.PARAMETER_CHECK_ERROR, "联邦学习类型为空"));
        Integer algorithmType = Optional.ofNullable(json.getInt("algorithm_type")).orElseThrow(()->new APIException(ResultCode.PARAMETER_CHECK_ERROR, "算法类型为空"));

        List<Algorithm> algorithms = Optional.ofNullable(algorithmService.list(Wrappers.<Algorithm>lambdaQuery()
                .eq(Algorithm::getFederationType, federationType)
                .eq(Algorithm::getAlgorithmType, algorithmType))).orElse(new ArrayList<>());

        return CommonResult.success(algorithms);
    }
}
