package com.cmcc.algo.controller;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.IdUtil;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cmcc.algo.common.APIException;
import com.cmcc.algo.common.Builder;
import com.cmcc.algo.common.CommonResult;
import com.cmcc.algo.common.ResultCode;
import com.cmcc.algo.common.utils.TimeUtils;
import com.cmcc.algo.common.utils.TokenManager;
import com.cmcc.algo.config.AgentConfig;
import com.cmcc.algo.entity.FederationDataset;
import com.cmcc.algo.entity.FederationEntity;
import com.cmcc.algo.entity.Train;
//import com.cmcc.algo.service.IFederationService;
import com.cmcc.algo.entity.UserFederation;
import com.cmcc.algo.mapper.FederationDatasetRepository;
import com.cmcc.algo.mapper.FederationRepository;
import com.cmcc.algo.service.IFederationDatasetService;
import com.cmcc.algo.service.ITrainService;
import com.cmcc.algo.service.IUserFederationService;
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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.cmcc.algo.constant.PageConstant.PAGENUM;
import static com.cmcc.algo.constant.PageConstant.STEP;
import static com.cmcc.algo.constant.URLConstant.SUBMIT_TRAIN_TASK_URL;
import static com.cmcc.algo.constant.CommonConstant.DATE_FORMAT;

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
    IUserFederationService userFederationService;

    @Autowired
    FederationDatasetRepository federationDatasetRepository;

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

        IPage resultPage = trainService.page(new Page(pageNum, step), Wrappers.<Train>lambdaQuery()
                .eq(Train::getFederationUuid, federationUuid)
                .orderByDesc(Train::getStartTime));

        List<Train> result = resultPage.getRecords();
        result.forEach(x -> {
            if (x.getStatus() == 0)
                x.setDuration(TimeUtils.getDurationStrByTimestamp(System.currentTimeMillis() - x.getStartTimestamp()));
        });

        return CommonResult.success(result, resultPage.getTotal(), resultPage.getCurrent(), resultPage.getSize());
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

        List<Integer> userList = userFederationService.list(Wrappers.<UserFederation>lambdaQuery().eq(UserFederation::getFederationUUid, federationUuid))
                .stream().map(x -> x.getUserId()).collect(Collectors.toList());
        List<FederationDataset> federationDatasetList = federationDatasetRepository.findByFederationUuidAndAndPartyIdIn(federationUuid, userList);
        if (federationDatasetList.size() != userList.size()) {
            throw new APIException(ResultCode.NOT_FOUND,"联邦用户未全部上传数据集");
        }

        String submitUrl = agentConfig.getAgentUrl(userId) + SUBMIT_TRAIN_TASK_URL;
        String responseBody = HttpUtil.post(submitUrl, request);

        if (!JSONUtil.parseObj(responseBody).getBool("success")) {
            log.warn("train task is failed to submit, the error detail is in agent log");
            throw new APIException(ResultCode.NOT_FOUND, "提交agent失败", JSONUtil.parseObj(responseBody).getStr("message"));
        }
        return true;
    }
}
