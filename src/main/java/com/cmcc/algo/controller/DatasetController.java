package com.cmcc.algo.controller;

import com.cmcc.algo.common.APIException;
import com.cmcc.algo.common.utils.TokenManager;
import com.cmcc.algo.entity.Dataset;
import com.cmcc.algo.entity.User;
import com.cmcc.algo.mapper.DatasetRepository;
import com.cmcc.algo.service.IUserService;
import com.cmcc.algo.vo.DatasetVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hjy
 * @since 2020-05-25
 */
@Slf4j
@Api(tags = "数据集接口")
@RestController
@RequestMapping("/datasets")
public class DatasetController {

    @Autowired
    private DatasetRepository datasetRepository;
    @Autowired
    IUserService userService;
    /**
     * list datasets
     */
    @GetMapping("")
    @ApiOperation(value = "获取数据集列表", notes = "获取数据集列表")
    public List<DatasetVo> datasets(@RequestParam Map<String, Object> params,
                                    @RequestHeader String token) {
        Integer partyId = -1;
        try {
            String userId = TokenManager.parseJWT(token).getId();
            //User user = userService.findById(userId);
            partyId = Integer.valueOf(userId);;
        }catch(Exception e) {
            log.error("cannot parse token", e.getMessage(), e);
            throw new APIException("token无效");
        }
        if (partyId == -1) {
            throw new APIException("无法获取partyId");
        }

        List<Dataset> page = datasetRepository.findByPartyId(partyId);
        List<DatasetVo> datasets = new ArrayList<DatasetVo>(page.size());
        //for (int i=0; i<page.size(); i++) {
        //    datasets.set(i, getDatasetVo(page.get(i)));
        //}
        for ( Dataset dataset : page) {
            datasets.add(getDatasetVo(dataset));
        }
        return datasets;
    }

    public DatasetVo getDatasetVo(Dataset dataset) {
        DatasetVo datasetVo = new DatasetVo();
        datasetVo.setId(dataset.getId());
        datasetVo.setName(dataset.getName());
        datasetVo.setUpdatedAt(dataset.getUpdatedAt());
        datasetVo.setSize(dataset.getSize());
        datasetVo.setRows(dataset.getRows());
        return datasetVo;
    }
}
