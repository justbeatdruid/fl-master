package com.cmcc.algo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cmcc.algo.common.annotation.SysLog;
import com.cmcc.algo.common.APIException;
//import com.cmcc.algo.common.validator.ValidatorUtils;
import com.cmcc.algo.entity.FederationEntity;
import com.cmcc.algo.service.IFederationService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * <p>
 * 联邦信息表 前端控制器
 * </p>
 *
 * @author hjy
 * @since 2020-05-25
 */
@RestController
@RequestMapping("/federations")
public class FederationController {
    @Value("${requesturls.url}")
    private String url;

    @Autowired
    private IFederationService federationService;

    /**
     * list federations
     */
    @GetMapping("")
    public List<Map<String, Object>> federations(@RequestParam Map<String, Object> params) {

        List<Map<String, Object>> page = federationService.queryFederations(params);

        return page;
    }

    @GetMapping("/{id}")
    public FederationEntity get(@PathVariable("id") String id) {

        //FederationEntity data = federationService.queryFederationById(id);
        FederationEntity data = federationService.getById(id);

        return data;
    }

    /**
      * create a new federation
      */
    @SysLog("save federation")
    @PostMapping("")
    public FederationEntity save(@RequestBody FederationEntity federation){
        if (federation==null) {
            throw new APIException("请求数据为空");
        }
        //ValidatorUtils.validateEntity(federation, AddGroup.class);
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        federation.setUuid(uuid);
        List<FederationEntity> list = federationService.list(new QueryWrapper<FederationEntity>()
                        .eq("name", federation.getName())
                        .or().eq("uuid", federation.getUuid()));
        if(CollectionUtils.isNotEmpty(list)){
            throw new APIException("名字重复，请重试。");
        }

        federationService.saveFederation(federation);
        return federation;
    }

    /**
     * delete a federation
     */
    @SysLog("delete federation")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable(name = "id") long id){
        FederationEntity removedFederation = federationService.getById(id);
        if (removedFederation == null) {
            throw new APIException(String.format("联邦ID%d不存在", id));
        }
        federationService.removeById(id);
        return;
    }

    /**
     * update a federation
     */
    @PutMapping("")
    public FederationEntity update(@RequestBody FederationEntity federation) throws Exception {
        if (federation == null) {
            throw new APIException("请求数据为空");
        }
        long id = federation.getId();
        FederationEntity updatedFederation = federationService.getById(id);
        if (updatedFederation == null) {
            throw new APIException(String.format("联邦ID%d不存在", id));
        }
        // attributes update permitted
        if (federation.getName() != null) {
            updatedFederation.setName(federation.getName());
        }
        List<FederationEntity> list = federationService.list(new QueryWrapper<FederationEntity>()
                        .eq("name", federation.getName()));
        if(CollectionUtils.isNotEmpty(list)){
            throw new APIException("名字重复，请重试。");
        }
        federationService.saveFederation(updatedFederation);
        return updatedFederation;
    }
}
