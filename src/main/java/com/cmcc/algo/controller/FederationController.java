package com.cmcc.algo.controller;

//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.cmcc.algo.common.annotation.SysLog;
import com.cmcc.algo.common.APIException;
import com.cmcc.algo.common.utils.TokenManager;
import com.cmcc.algo.dto.FederationDto;
import com.cmcc.algo.entity.FederationEntity;
import com.cmcc.algo.mapper.FederationRepository;
import com.cmcc.algo.vo.FederationVo;
import com.cmcc.algo.vo.DataFormatVo;
import com.cmcc.algo.service.IFederationService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
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
@Slf4j
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
    public List<FederationVo> federations(@RequestHeader String token,
                                          @RequestParam Map<String, Object> params) {
        String userId = "";
        try {
            userId = TokenManager.parseJWT(token).getId();
            log.info("get user id", userId);
        }catch(Exception e) {
            log.error("cannot parse token", e.getMessage(), e);
            throw new APIException("token无效");
        }

        params.put("userId", userId);
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
    public FederationVo get(@RequestHeader String token,
                            @PathVariable("id") String id) {
        String userId = "";
        try {
            userId = TokenManager.parseJWT(token).getId();
            log.info("get user id", userId);
        }catch(Exception e) {
            log.error("cannot parse token", e.getMessage(), e);
            throw new APIException("token无效");
        }

        //FederationEntity data = federationService.queryFederationById(id);
        FederationEntity federation = federationRepository.findByUuid(id);
        if (federation == null) {
            throw new APIException(String.format("联邦UUID%s不存在", id));
        }

        // TODO
        boolean isHosts = true;
        if (federation.getGuest() != userId || !isHosts) {
            throw new APIException("没有操作权限");
        }

        return getFederationVo(federation);
    }

    /**
      * create a new federation
      */
    @SysLog("save federation")
    @PostMapping("")
    @Transactional
    public FederationVo save(@RequestHeader String token,
                             @Valid @RequestBody FederationDto federationDto){
        if (federationDto==null) {
            throw new APIException("请求数据为空");
        }
        String userId = "";
        try {
            userId = TokenManager.parseJWT(token).getId();
            log.info("get user id", userId);
        }catch(Exception e) {
            log.error("cannot parse token", e.getMessage(), e);
            throw new APIException("token无效");
        }
        FederationEntity federation = fromFederationDto(federationDto);
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
        federation.setGuest(userId);
        federation.setHosts("");
        federation.setStatus(new Integer(0));

        if (federation.getType() == null) {
            federation.setType(new Boolean(true));
        }
        federationRepository.save(federation);

        FederationVo federationVo = getFederationVo(federation);
        return federationVo;
    }

    /**
     * delete a federation
     */
    @SysLog("delete federation")
    @DeleteMapping("/{id}")
    @Transactional
    public void delete(@RequestHeader String token,
                       @PathVariable(name = "id") String id){
        String userId = "";
        try {
            userId = TokenManager.parseJWT(token).getId();
            log.info("get user id", userId);
        }catch(Exception e) {
            log.error("cannot parse token", e.getMessage(), e);
            throw new APIException("token无效");
        }
        FederationEntity removedFederation = federationRepository.findByUuid(id);
        if (removedFederation == null) {
            throw new APIException(String.format("联邦UUID%s不存在", id));
        }

        if (removedFederation.getGuest() != userId) {
            throw new APIException("没有操作权限");
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
    public FederationVo update(@RequestHeader String token,
                               @PathVariable(name = "id") String id,
                               @RequestBody FederationDto federationDto) throws Exception {
        if (federationDto == null) {
            throw new APIException("请求数据为空");
        }

        String userId = "";
        try {
            userId = TokenManager.parseJWT(token).getId();
            log.info("get user id", userId);
        }catch(Exception e) {
            log.error("cannot parse token", e.getMessage(), e);
            throw new APIException("token无效");
        }

        FederationEntity federation = fromFederationDto(federationDto);

        FederationEntity updatedFederation = federationRepository.findByUuid(id);
        if (updatedFederation == null) {
            throw new APIException(String.format("联邦UUID%s不存在", id));
        }

        if (updatedFederation.getGuest() != userId) {
            throw new APIException("没有操作权限");
        }

        List<FederationEntity> list = federationRepository.findByName(federation.getName());
        if(CollectionUtils.isNotEmpty(list)){
            throw new APIException("名字重复，请重试。");
        }
        // attributes update permitted
        if (federation.getName() != null) {
            updatedFederation.setName(federation.getName());
        }
        if (federation.getDescription() != null) {
            updatedFederation.setDescription(federation.getDescription());
        }
        if (federation.getDataFormat() != null) {
            updatedFederation.setDataFormat(federation.getDataFormat());
        }
        if (federation.getAlgorithmId() != null) {
            updatedFederation.setAlgorithmId(federation.getAlgorithmId());
        }
        if (federation.getParam() != null) {
            updatedFederation.setParam(federation.getParam());
        }

        federationRepository.save(updatedFederation);
        return getFederationVo(updatedFederation);
    }

    /**
     * update a federation
     */
    @PutMapping("/{uuid}/ready")
    @SysLog("update federation status to ready")
    @Transactional
    public FederationVo update(@RequestHeader String token,
                               @PathVariable(name = "uuid") String uuid) throws Exception {
        String userId = "";
        try {
            userId = TokenManager.parseJWT(token).getId();
            log.info("get user id", userId);
        }catch(Exception e) {
            log.error("cannot parse token", e.getMessage(), e);
            throw new APIException("token无效");
        }

        FederationEntity updatedFederation = federationRepository.findByUuid(uuid);
        if (updatedFederation == null) {
            throw new APIException(String.format("联邦UUID%s不存在", uuid));
        }
        if (updatedFederation.getGuest() != userId) {
            throw new APIException("没有操作权限");
        }
        if (updatedFederation.getStatus() != 0) {
            throw new APIException(String.format("不能就绪状态%s的联邦", getReadableStatusFromCode(updatedFederation.getStatus())));
        }
        // attributes update permitted
        updatedFederation.setStatus(1);
        federationRepository.save(updatedFederation);
        return getFederationVo(updatedFederation);
    }

    public static FederationEntity fromFederationDto(FederationDto federationDto) {
        FederationEntity federation = new FederationEntity();
        BeanUtils.copyProperties(federationDto, federation);
        federation.setDataFormat(JSON.toJSONString(federationDto.getDataFormat()));
        federation.setParam(JSON.toJSONString(federationDto.getParam()));
        return federation;
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
        federationVo.setDisplayStatus(getReadableStatusFromCode(status));
        federationVo.setDataFormat(JSON.parseObject(federation.getDataFormat(), DataFormatVo.class));
        federationVo.setParam(JSON.parseObject(federation.getParam(), new TypeReference<LinkedHashMap<String, Double>>(){}));
        return federationVo;
    }

    public static String getReadableStatusFromCode(Integer status) {
        switch(status) {
        case 0:
            return new String("等待");
        case 1:
            return new String("就绪");
        case 2:
            return new String("运行中");
        case 3:
            return new String("成功");
        case 4:
            return new String("失败");
        default:
            return new String("未知");
        }
    }
}
