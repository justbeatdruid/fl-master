package com.cmcc.algo.controller;

import com.cmcc.algo.entity.Dataset;
import com.cmcc.algo.mapper.DatasetRepository;
import com.cmcc.algo.vo.DatasetVo;

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
@RestController
@RequestMapping("/datasets")
public class DatasetController {

    @Autowired
    private DatasetRepository datasetRepository;
    /**
     * list datasets
     */
    @GetMapping("")
    public List<DatasetVo> datasets(@RequestParam Map<String, Object> params) {

        List<Dataset> page = datasetRepository.findAll();
        List<DatasetVo> datasets = new ArrayList<DatasetVo>(page.size());
        //for (int i=0; i<page.size(); i++) {
        //    datasets.set(i, getDatasetVo(page.get(i)));
        //}
        for ( Dataset dataset : page) {
            datasets.add(getDatasetVo(dataset));
        }
        return datasets;
    }

    public static DatasetVo getDatasetVo(Dataset dataset) {
        DatasetVo datasetVo = new DatasetVo();
        datasetVo.setName(dataset.getName());
        datasetVo.setUpdatedAt(dataset.getUpdatedAt());
        datasetVo.setSize(dataset.getSize());
        return datasetVo;
    }
}
