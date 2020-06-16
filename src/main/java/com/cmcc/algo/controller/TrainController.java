package com.cmcc.algo.controller;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.IdUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cmcc.algo.common.APIException;
import com.cmcc.algo.common.Builder;
import com.cmcc.algo.common.CommonResult;
import com.cmcc.algo.common.ResultCode;
import com.cmcc.algo.common.utils.TokenManager;
import com.cmcc.algo.config.AgentConfig;
import com.cmcc.algo.entity.FederationEntity;
import com.cmcc.algo.entity.Train;
//import com.cmcc.algo.service.IFederationService;
import com.cmcc.algo.mapper.FederationRepository;
import com.cmcc.algo.service.ITrainService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static com.cmcc.algo.constant.PageConstant.PAGENUM;
import static com.cmcc.algo.constant.PageConstant.STEP;
import static com.cmcc.algo.constant.URLConstant.SUBMIT_TRAIN_TASK_URL;
import static com.cmcc.algo.constant.CommonConstant.DATE_FORMAT;

/**
 * <p>
 *  前端控制器
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
    AgentConfig agentConfig;

    @ApiOperation(value = "查询训练记录", notes = "查询训练记录")
    @ApiImplicitParams({@ApiImplicitParam(name = "token", value = "头部token信息"),
                        @ApiImplicitParam(name = "request", value = "请求jsonStr，包括'federationUuid'和分页参数（可选）'pageNum','step'")})
    @PostMapping("/list")
    public CommonResult getTrainTaskList(@RequestHeader String token,  @RequestBody String request){
        String userId = "";
        try {
            userId = TokenManager.parseJWT(token).getId();
            log.info("get user id", userId);
        }catch(Exception e) {
            log.error("cannot parse token", e.getMessage(), e);
            throw new APIException("token无效");
        }

        long pageNum = Optional.ofNullable(JSONUtil.parseObj(request).getLong(PAGENUM)).orElse(1L);
        long step = Optional.ofNullable(JSONUtil.parseObj(request).getLong(STEP)).orElse(10L);
        String federationUuid = Optional.ofNullable(JSONUtil.parseObj(request).getStr("federationUuid")).orElseThrow(() -> new APIException(ResultCode.NOT_FOUND, "联邦UUID为空"));

        IPage result = trainService.page(new Page(pageNum, step), Wrappers.<Train>lambdaQuery()
                .eq(Train::getFederationUuid, federationUuid)
                .orderByDesc(Train::getStartTime));
        return CommonResult.success(result.getRecords(), result.getTotal(), result.getCurrent(), result.getSize());
    }

    @ApiOperation(value = "开始训练", notes = "开始训练")
    @ApiImplicitParams({@ApiImplicitParam(name = "token", value = "头部token信息"),
                        @ApiImplicitParam(name = "federationUuid", value = "联邦UUID")})
    @PostMapping("/submit")
    @Transactional(rollbackFor = Exception.class)
    public Boolean submitTrainTask(@RequestHeader String token, @RequestBody String federationUuid){
        String userId = "";
        try {
            userId = TokenManager.parseJWT(token).getId();
            log.info("get user id", userId);
        }catch(Exception e) {
            log.error("cannot parse token", e.getMessage(), e);
            throw new APIException("token无效");
        }

        // 向Agent提交训练任务
        String submitUrl = agentConfig.getAgentUrl()+SUBMIT_TRAIN_TASK_URL;

        FederationEntity federationEntity = federationRepository.findByUuid(federationUuid);

        //TODO 数据集参数和训练参数放在一个map中

        Train train = Builder.of(Train::new)
                .with(Train::setFederationUuid, federationUuid)
                .with(Train::setUuid, IdUtil.randomUUID())
                .with(Train::setAlgorithmId, federationEntity.getAlgorithmId())
                .with(Train::setStatus, 0)
                .with(Train::setStartTime, LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_FORMAT)))
                .with(Train::setTrainParam, federationEntity.getParam())
                .build();

        if (!trainService.save(train)) {
            log.warn("train record is failed to save, probably because db connection error");
            throw new APIException(ResultCode.NOT_FOUND, "保存失败");
        }

        String str = JSONUtil.toJsonStr(train);
        String responseBody = HttpUtil.post(submitUrl,str);

        if (!(boolean) JSONUtil.parseObj(responseBody).get("success")) {
            log.warn("train task is failed to submit, the error detail is in agent log");
            throw new APIException(ResultCode.NOT_FOUND, "提交agent失败");
        }
        return true;
    }
}
