package com.cmcc.algo.controller;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cmcc.algo.common.APIException;
import com.cmcc.algo.common.CommonResult;
import com.cmcc.algo.common.ResultCode;
import com.cmcc.algo.common.utils.TimeUtils;
import com.cmcc.algo.common.utils.TokenManager;
import com.cmcc.algo.config.AgentConfig;
import com.cmcc.algo.entity.Train;
import com.cmcc.algo.mapper.FederationRepository;
import com.cmcc.algo.service.IFederationDatasetService;
import com.cmcc.algo.service.IFederationService;
import com.cmcc.algo.service.ITrainService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.cmcc.algo.constant.PageConstant.PAGENUM;
import static com.cmcc.algo.constant.PageConstant.STEP;
import static com.cmcc.algo.constant.URLConstant.SUBMIT_TRAIN_TASK_URL;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author hjy
 * @since 2020-05-25
 */
@Api(tags = "训练任务接口")
@Slf4j
@RestController
@RequestMapping("/train")
public class TrainController {
    @Autowired
    ITrainService trainService;

    @Autowired
    FederationRepository federationRepository;

    @Autowired
    IFederationService federationService;

    @Autowired
    IFederationDatasetService federationDatasetService;

    @Autowired
    AgentConfig agentConfig;

    @ApiOperation(value = "查询训练记录", notes = "查询训练记录")
    @ApiImplicitParams({@ApiImplicitParam(name = "token", value = "头部token信息"),
            @ApiImplicitParam(name = "request", value = "请求jsonStr，包括'federationUuid'和分页参数（可选）'pageNum','step'")})
    @PostMapping("/list")
    public CommonResult getTrainTaskList(@RequestHeader String token, @RequestBody String request) {
        String userId = "";
        try {
            userId = TokenManager.parseJWT(token).getId();
            log.info("get user id", userId);
        } catch (Exception e) {
            log.error("cannot parse token", e.getMessage(), e);
            throw new APIException("token无效");
        }

        long pageNum = Optional.ofNullable(JSONUtil.parseObj(request).getLong(PAGENUM)).orElse(1L);
        long step = Optional.ofNullable(JSONUtil.parseObj(request).getLong(STEP)).orElse(10L);
        String federationUuid = Optional.ofNullable(JSONUtil.parseObj(request).getStr("federationUuid")).orElseThrow(() -> new APIException(ResultCode.NOT_FOUND, "联邦UUID为空"));

        IPage resultPage = trainService.getTrainList(federationUuid, pageNum, step);

        return CommonResult.success(resultPage.getRecords(), resultPage.getTotal(), resultPage.getCurrent(), resultPage.getSize());
    }

    @ApiOperation(value = "开始训练", notes = "开始训练")
    @ApiImplicitParams({@ApiImplicitParam(name = "token", value = "头部token信息"),
            @ApiImplicitParam(name = "request", value = "包含federationUuid")})
    @PostMapping("/submit")
    @Transactional(rollbackFor = Exception.class)
    public Boolean submitTrainTask(@RequestHeader String token, @RequestBody String request) {
        String userId = "";
        try {
            userId = TokenManager.parseJWT(token).getId();
            log.info("get user id", userId);
        } catch (Exception e) {
            log.error("cannot parse token", e.getMessage(), e);
            throw new APIException("token无效");
        }
        // 判断数据是否准备完成
        String federationUuid = JSONUtil.parseObj(request).getStr("federationUuid");

        if (!federationDatasetService.datasetPrepared(federationUuid, (short) 0)) {
            throw new APIException(ResultCode.NOT_FOUND,"联邦用户未全部上传数据集");
        }

        if (federationService.getOne(federationUuid).getUserCount() == 1) {
            throw new APIException(ResultCode.NOT_FOUND,"联邦用户数为1，无法训练");
        }

        String responseBody = trainService.submitTrainJob(federationUuid, userId);

        if (!JSONUtil.parseObj(responseBody).getBool("success")) {
            log.warn("train task is failed to submit, the error detail is in agent log");
            throw new APIException(ResultCode.NOT_FOUND, "提交agent失败", JSONUtil.parseObj(responseBody).getStr("message"));
        }
        return true;
    }
}
