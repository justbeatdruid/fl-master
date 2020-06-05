package com.cmcc.algo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cmcc.algo.common.CommonResult;
import com.cmcc.algo.common.utils.PageUtil;
import com.cmcc.algo.entity.Train;
import com.cmcc.algo.service.ITrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hjy
 * @since 2020-05-25
 */
@RestController
public class TrainController {
    @Autowired
    ITrainService trainService;

    @PostMapping("/train/list")
    public CommonResult getTrainTaskList(@RequestBody String request){
        IPage result = trainService.page(PageUtil.getPageByRequest(request));
        return CommonResult.success(result.getRecords(), result.getTotal(), result.getCurrent(), result.getSize());
    }
}
