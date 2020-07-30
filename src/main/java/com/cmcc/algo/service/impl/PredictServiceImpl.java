package com.cmcc.algo.service.impl;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cmcc.algo.common.APIException;
import com.cmcc.algo.common.ResultCode;
import com.cmcc.algo.common.utils.TimeUtils;
import com.cmcc.algo.config.AgentConfig;
import com.cmcc.algo.entity.Predict;
import com.cmcc.algo.mapper.PredictMapper;
import com.cmcc.algo.service.IFederationDatasetService;
import com.cmcc.algo.service.IPredictService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.cmcc.algo.constant.URLConstant.EXPORT_DATA_URL;
import static com.cmcc.algo.constant.URLConstant.SUBMIT_PREDICT_TASK_URL;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hjy
 * @since 2020-05-25
 */
@Service
public class PredictServiceImpl extends ServiceImpl<PredictMapper, Predict> implements IPredictService {
    @Autowired
    IPredictService predictService;

    @Autowired
    IFederationDatasetService federationDatasetService;

    @Autowired
    AgentConfig agentConfig;

    @Override
    public IPage getPredictList(String federationUuid, long pageNum, long step) {
        IPage resultPage = predictService.page(new Page(pageNum, step), Wrappers.<Predict>lambdaQuery()
                .eq(Predict::getFederationUuid, federationUuid)
                .orderByDesc(Predict::getStartTime));

        List<Predict> result = resultPage.getRecords();
        result.forEach(x -> {
            if (x.getStatus() == 0)
                x.setDuration(TimeUtils.getDurationStrByTimestamp(System.currentTimeMillis() - x.getStartTimestamp()));
        });
        return resultPage;
    }

    @Override
    public String submitPredictJob(String federationUuid, String userId) {
        // 上传训练数据
        List<String> responseList = federationDatasetService.uploadData(federationUuid, (short) 1);;
        responseList.forEach(x -> {
            if (!JSONUtil.parseObj(x).getBool("success")) {
                throw new APIException(ResultCode.NOT_FOUND, "上传数据失败", JSONUtil.parseObj(x).get("data"));
            }
        });

        String submitUrl = agentConfig.getAgentUrl(userId) + SUBMIT_PREDICT_TASK_URL;
        String request = JSONUtil.toJsonStr(new JSONObject().putOnce("federationUuid", federationUuid));

        return HttpUtil.post(submitUrl, request);
    }

    @Override
    public String exportData(String request, String userId) {
        String exportUrl = agentConfig.getAgentUrl(userId) + EXPORT_DATA_URL;
        return HttpUtil.post(exportUrl, request);
    }
}
