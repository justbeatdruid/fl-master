package com.cmcc.algo.controller;

//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cmcc.algo.common.annotation.SysLog;
import com.cmcc.algo.common.APIException;
import com.cmcc.algo.dto.FederationDto;
import com.cmcc.algo.entity.FederationEntity;
import com.cmcc.algo.mapper.FederationRepository;
import com.cmcc.algo.vo.FederationVo;
import com.cmcc.algo.service.IFederationService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;
import java.util.Date;

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
    @Autowired
    private FederationRepository federationRepository;

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
        FederationEntity federation = federationRepository.findByUuid(id);
        if (federation == null) {
            throw new APIException(String.format("联邦UUID%s不存在", id));
        }

        return getFederationVo(federation);
    }

    /**
      * create a new federation
      */
    @SysLog("save federation")
    @PostMapping("")
    @Transactional
    public FederationVo save(@Valid @RequestBody FederationDto federationDto){
        if (federationDto==null) {
            throw new APIException("请求数据为空");
        }
        FederationEntity federation = new FederationEntity();
        BeanUtils.copyProperties(federationDto, federation);
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        federation.setUuid(uuid);
        /*
        List<FederationEntity> list = federationService.list(new QueryWrapper<FederationEntity>()
                        .eq("name", federation.getName())
                        .or().eq("uuid", federation.getUuid()));
        */
        List<FederationEntity> list = federationRepository.findByName(federation.getName());
        if(CollectionUtils.isNotEmpty(list)){
            throw new APIException("名字重复，请重试。");
        }

        federation.setId((short) 0);
        federation.setCreatedAt(new Date());
        federation.setGuest("");
        federation.setHosts("");
        federation.setStatus(new Integer(0));
        federationRepository.save(federation);

        return getFederationVo(federation);
    }

    /**
     * delete a federation
     */
    @SysLog("delete federation")
    @DeleteMapping("/{id}")
    @Transactional
    public void delete(@PathVariable(name = "id") String id){
        FederationEntity removedFederation = federationRepository.findByUuid(id);
        if (removedFederation == null) {
            throw new APIException(String.format("联邦UUID%s不存在", id));
        }
        federationRepository.deleteByUuid(id);
        return;
    }

    /**
     * update a federation
     */
    @PutMapping("/{id}")
    @SysLog("update federation")
    @Transactional
    public FederationVo update(@PathVariable(name = "id") String id, @RequestBody FederationDto federation) throws Exception {
        if (federation == null) {
            throw new APIException("请求数据为空");
        }
        FederationEntity updatedFederation = federationRepository.findByUuid(id);
        if (updatedFederation == null) {
            throw new APIException(String.format("联邦UUID%s不存在", id));
        }
        // attributes update permitted
        if (federation.getName() != null) {
            updatedFederation.setName(federation.getName());
        }
        List<FederationEntity> list = federationRepository.findByName(federation.getName());
        if(CollectionUtils.isNotEmpty(list)){
            throw new APIException("名字重复，请重试。");
        }
        federationRepository.save(updatedFederation);
        return getFederationVo(updatedFederation);
    }

    public static FederationVo getFederationVo(FederationEntity federation) {
        FederationVo federationVo = new FederationVo();
        if (federation == null) {
            return federationVo;
        }
        BeanUtils.copyProperties(federation, federationVo);
        Integer status = federation.getStatus();
        if (status == null) {
          status = -1;
        }
        switch(status) {
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
