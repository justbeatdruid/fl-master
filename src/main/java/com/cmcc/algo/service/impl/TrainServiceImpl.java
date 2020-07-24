package com.cmcc.algo.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cmcc.algo.common.CommonResult;
import com.cmcc.algo.common.utils.TimeUtils;
import com.cmcc.algo.entity.Train;
import com.cmcc.algo.mapper.TrainMapper;
import com.cmcc.algo.service.ITrainService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hjy
 * @since 2020-05-25
 */
@Service
public class TrainServiceImpl extends ServiceImpl<TrainMapper, Train> implements ITrainService {
    @Autowired
    ITrainService trainService;

    @Override
    public IPage getTrainList(String federationUuid, long pageNum, long step){
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
}
