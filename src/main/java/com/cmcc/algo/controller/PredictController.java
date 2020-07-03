package com.cmcc.algo.controller;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
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
import com.cmcc.algo.entity.Predict;
import com.cmcc.algo.entity.Train;
import com.cmcc.algo.service.IPredictService;
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
import java.util.Map;
import java.util.Optional;

import static com.cmcc.algo.constant.PageConstant.PAGENUM;
import static com.cmcc.algo.constant.PageConstant.STEP;
import static com.cmcc.algo.constant.URLConstant.EXPORT_DATA_URL;
import static com.cmcc.algo.constant.URLConstant.SUBMIT_PREDICT_TASK_URL;
import static com.cmcc.algo.constant.CommonConstant.DATE_FORMAT;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hjy
 * @since 2020-05-25
 */
@Api(tags = "预测任务接口")
@Slf4j
@RestController
@RequestMapping("/predict")
public class PredictController {
    @Autowired
    IPredictService predictService;

    @Autowired
    ITrainService trainService;

    @Autowired
    AgentConfig agentConfig;

    @ApiOperation(value = "查询预测记录", notes = "查询预测记录")
    @ApiImplicitParams({@ApiImplicitParam(name = "token", value = "头部token信息"),
                        @ApiImplicitParam(name = "request", value = "请求jsonStr，包括'federationUuid'和分页参数（可选）'pageNum','step'")})
    @PostMapping("/list")
    public CommonResult getPredictTaskList(@RequestHeader String token, @RequestBody String request){
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

        IPage result = predictService.page(new Page(pageNum, step), Wrappers.<Predict>lambdaQuery()
                .eq(Predict::getFederationUuid, federationUuid)
                .orderByDesc(Predict::getStartTime));
        return CommonResult.success(result.getRecords(), result.getTotal(), result.getCurrent(), result.getSize());
    }

    @ApiOperation(value = "开始预测", notes = "开始预测")
    @ApiImplicitParams({@ApiImplicitParam(name = "token", value = "头部token信息"),
                        @ApiImplicitParam(name = "federationUuid", value = "联邦UUID")})
    @PostMapping("/submit")
    @Transactional(rollbackFor = Exception.class)
    public boolean submitPredictTask(@RequestHeader String token, @RequestBody String federationUuid){
        String userId = "";
        try {
            userId = TokenManager.parseJWT(token).getId();
            log.info("get user id", userId);
        }catch(Exception e) {
            log.error("cannot parse token", e.getMessage(), e);
            throw new APIException("token无效");
        }

        // 向Agent提交训练任务
        String submitUrl = agentConfig.getAgentUrl(userId)+SUBMIT_PREDICT_TASK_URL;

        Train train = trainService.getOne(Wrappers.<Train>lambdaQuery()
                .eq(Train::getFederationUuid, federationUuid)
                .orderByDesc(Train::getStartTime)
                .last("limit 1"));

        //TODO 添加数据参数
//        Map<String, Object> paramMap = MapUtil.newHashMap();
//        JSONObject model = JSONUtil.parseObj(train.getModel());
//        if (ObjectUtil.isEmpty(model.get("modelId")) || ObjectUtil.isEmpty(model.get("modelVersion"))) {
//            log.warn("model doesn't exists, perhaps you didn't finish this training job");
//            throw new APIException(ResultCode.NOT_FOUND, "训练任务未执行成功，模型未生成");
//        }
//        paramMap.put("conf", train.getTrainParam());
//        paramMap.put("model", model);
//
//        Predict predict = Builder.of(Predict::new)
//                .with(Predict::setFederationUuid, federationUuid)
//                .with(Predict::setTrainUuid, train.getUuid())
//                .with(Predict::setStatus, 0)
//                .with(Predict::setAlgorithmId, train.getAlgorithmId())
//                .with(Predict::setStartTime, LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_FORMAT)))
//                .with(Predict::setPredictParam, JSONUtil.toJsonStr(paramMap))
//                .build();
//
//        if (!predictService.save(predict)) {
//            log.warn("predict record is failed to save, probably because db connection error");
//            throw new APIException(ResultCode.NOT_FOUND, "保存失败");
//        }
//
//        String str = JSONUtil.toJsonStr(predict);
//        String responseBody = HttpUtil.post(submitUrl, str);

        String responseBody = "{\n\t\"code\": 200,\n\t\"data\": null,\n\t\"ext\": null,\n\t\"message\": \"请求成功\",\n\t\"pageInfo\": null,\n\t\"success\": true\n}";
        if (!(boolean) JSONUtil.parseObj(responseBody).get("success")) {
            log.warn("predict task is failed to submit, the error detail is in agent log");
            throw new APIException(ResultCode.NOT_FOUND, "提交agent失败");
        }
        return true;
    }

    @ApiOperation(value = "导出结果", notes = "导出结果")
    @ApiImplicitParams({@ApiImplicitParam(name = "token", value = "头部token信息"),
                        @ApiImplicitParam(name = "predictUuid", value = "预测记录UUID")})
    @PostMapping("/export")
    public String exportResult(@RequestHeader String token, @RequestBody String predictUuid){
        String userId = "";
        try {
            userId = TokenManager.parseJWT(token).getId();
            log.info("get user id", userId);
        }catch(Exception e) {
            log.error("cannot parse token", e.getMessage(), e);
            throw new APIException("token无效");
        }

//        String exportUrl = agentConfig.getAgentUrl() + EXPORT_DATA_URL;
//
//        Predict predict = predictService.getOne(Wrappers.<Predict>lambdaQuery().eq(Predict::getUuid, predictUuid));
//
//        JSONObject request = new JSONObject().putOnce("jobId", predict.getJobId()).putOnce("outputPath", predict.getOutputPath());
//        String responseBody = HttpUtil.post(exportUrl, JSONUtil.toJsonStr(request));

        String responseBody = "{\n\t\"code\": 200,\n\t\"data\": null,\n\t\"ext\": null,\n\t\"message\": \"请求成功\",\n\t\"pageInfo\": null,\n\t\"success\": true\n}";
        if (!(boolean) JSONUtil.parseObj(responseBody).get("success")) {
            log.warn("export data is failed, the error detail is in agent log");
            throw new APIException(ResultCode.NOT_FOUND, "数据导出失败");
        }
        return JSONUtil.parseObj(responseBody).get("data").toString();
    }
}
