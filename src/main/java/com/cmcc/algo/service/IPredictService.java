package com.cmcc.algo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cmcc.algo.entity.Predict;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hjy
 * @since 2020-05-25
 */
public interface IPredictService extends IService<Predict> {
    IPage getPredictList(String federationUuid, long pageNum, long step);

    String submitPredictJob(String federationUuid, String userId);

    String exportData(String request, String userId);
}
