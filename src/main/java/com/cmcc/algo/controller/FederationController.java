package com.cmcc.algo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cmcc.algo.common.annotation.SysLog;
import com.cmcc.algo.common.APIException;
import com.cmcc.algo.dto.FederationDto;
import com.cmcc.algo.entity.FederationEntity;
import com.cmcc.algo.vo.FederationVo;
import com.cmcc.algo.service.IFederationService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

import javax.validation.Valid; 

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
    public List<FederationVo> federations(@RequestParam Map<String, Object> params) {

        List<FederationEntity> page = federationService.queryFederations(params);
        List<FederationVo> federations = new ArrayList<FederationVo>(page.size());
        //for (int i=0; i<page.size(); i++) {
        //    federations.set(i, getFederationVo(page.get(i)));
        //}
        for ( FederationEntity federation : page) {
            federations.add(getFederationVo(federation));
        }
        return federations;
    }

    @GetMapping("/{id}")
    public FederationVo get(@PathVariable("id") String id) {

        //FederationEntity data = federationService.queryFederationById(id);
        FederationEntity federation = federationService.getById(id);

        return getFederationVo(federation);
    }

    /**
      * create a new federation
      */
    @SysLog("save federation")
    @PostMapping("")
    public FederationVo save(@Valid @RequestBody FederationDto federationDto){
        if (federationDto==null) {
            throw new APIException("请求数据为空");
        }
        FederationEntity federation = new FederationEntity();
        BeanUtils.copyProperties(federationDto, federation);
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        federation.setUuid(uuid);
        List<FederationEntity> list = federationService.list(new QueryWrapper<FederationEntity>()
                        .eq("name", federation.getName())
                        .or().eq("uuid", federation.getUuid()));
        if(CollectionUtils.isNotEmpty(list)){
            throw new APIException("名字重复，请重试。");
        }

        federationService.saveFederation(federation);

        return getFederationVo(federation);
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
    public FederationVo update(@RequestBody FederationEntity federation) throws Exception {
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
        return getFederationVo(updatedFederation);
    }

    public static FederationVo getFederationVo(FederationEntity federation) {
        FederationVo federationVo = new FederationVo();
        BeanUtils.copyProperties(federation, federationVo);
        switch(federation.getStatus()) {
        case 0:
            federationVo.setDisplayStatus("等待");
            break;
        case 1:
            federationVo.setDisplayStatus("就绪");
            break;
        case 2:
            federationVo.setDisplayStatus("运行中");
            break;
        case 3:
            federationVo.setDisplayStatus("成功");
            break;
        case 4:
            federationVo.setDisplayStatus("失败");
            break;
        default:
            federationVo.setDisplayStatus("未知");
            break;
        }
        return federationVo;
    }
}
