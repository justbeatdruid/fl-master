package com.cmcc.algo.service.impl;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cmcc.algo.common.APIException;
import com.cmcc.algo.common.CommonResult;
import com.cmcc.algo.common.ResultCode;
import com.cmcc.algo.common.utils.TimeUtils;
import com.cmcc.algo.config.AgentConfig;
import com.cmcc.algo.entity.Train;
import com.cmcc.algo.mapper.TrainMapper;
import com.cmcc.algo.service.IFederationDatasetService;
import com.cmcc.algo.service.ITrainService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.cmcc.algo.constant.URLConstant.SUBMIT_TRAIN_TASK_URL;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author hjy
 * @since 2020-05-25
 */
@Service
@Slf4j
public class TrainServiceImpl extends ServiceImpl<TrainMapper, Train> implements ITrainService {
    @Autowired
    ITrainService trainService;

    @Autowired
    IFederationDatasetService federationDatasetService;

    @Autowired
    AgentConfig agentConfig;

    @Override
    public IPage getTrainList(String federationUuid, long pageNum, long step) {
        IPage resultPage = trainService.page(new Page(pageNum, step), Wrappers.<Train>lambdaQuery()
                .eq(Train::getFederationUuid, federationUuid)
                .orderByDesc(Train::getStartTime));

        List<Train> result = resultPage.getRecords();
        result.forEach(x -> {
            if (x.getStatus() == 0)
                x.setDuration(TimeUtils.getDurationStrByTimestamp(System.currentTimeMillis() - x.getStartTimestamp()));
        });

        return resultPage;
    }

    @Override
    public String submitTrainJob(String federationUuid, String userId) {
        // 上传训练数据
        List<String> responseList = federationDatasetService.uploadData(federationUuid, (short) 0);
        responseList.forEach(x -> {
            if (!JSONUtil.parseObj(x).getBool("success")) {
                throw new APIException(ResultCode.NOT_FOUND, "上传数据失败", JSONUtil.parseObj(x).get("data"));
            }
        });

        String submitUrl = agentConfig.getAgentUrl(userId) + SUBMIT_TRAIN_TASK_URL;
        String request = JSONUtil.toJsonStr(new JSONObject().putOnce("federationUuid", federationUuid));

        return HttpUtil.post(submitUrl, request);
    }
}
