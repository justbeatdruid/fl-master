package com.cmcc.algo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cmcc.algo.entity.Train;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hjy
 * @since 2020-05-25
 */
public interface ITrainService extends IService<Train> {
    public IPage getTrainList(String federationUuid, long pageNum, long step);
}
