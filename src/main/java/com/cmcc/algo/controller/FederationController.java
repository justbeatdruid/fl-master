package com.cmcc.algo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.cmcc.algo.common.annotation.SysLog;
import com.cmcc.algo.common.APIException;
import com.cmcc.algo.common.utils.TokenManager;
import com.cmcc.algo.dto.FederationDto;
import com.cmcc.algo.dto.Statistic;
import com.cmcc.algo.entity.Dataset;
import com.cmcc.algo.entity.FederationEntity;
import com.cmcc.algo.entity.FederationDataset;
import com.cmcc.algo.entity.User;
import com.cmcc.algo.entity.UserFederation;
import com.cmcc.algo.mapper.DatasetRepository;
import com.cmcc.algo.mapper.FederationRepository;
import com.cmcc.algo.mapper.FederationDatasetRepository;
import com.cmcc.algo.vo.FederationVo;
import com.cmcc.algo.vo.FederationDatasetVo;
import com.cmcc.algo.vo.DataFormatVo;
import com.cmcc.algo.service.IFederationDatasetService;
import com.cmcc.algo.service.IFederationService;
import com.cmcc.algo.service.IUserFederationService;
import com.cmcc.algo.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
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
    private IUserService userService;
    @Autowired
    private FederationRepository federationRepository;
    @Autowired
    private DatasetRepository datasetRepository;
    @Autowired
    private FederationDatasetRepository federationDatasetRepository;
    @Autowired
    private IUserFederationService userFederationService;
    @Autowired
    private IFederationDatasetService federationDatasetService;

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
        } catch (Exception e) {
            log.error("cannot parse token", e.getMessage(), e);
            throw new APIException("token无效");
        }
        if (userId == null) {
            throw new APIException("获取用户ID异常");
        }

        //List<FederationEntity> page = federationService.queryFederations(params, userId);
        List<FederationEntity> page = new ArrayList<FederationEntity>();
        String name = (String) params.get("name");
        Boolean pri = Boolean.parseBoolean((String) params.get("private"));
        List<String> uuidList = new ArrayList<String>();

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id", userId);
        List<UserFederation> userFederationList = userFederationService.list(queryWrapper);

        uuidList = new ArrayList<String>(userFederationList.size());
        for (UserFederation userFederation : userFederationList) {
            uuidList.add(userFederation.getFederationUUid());
        }

        if (StringUtils.isNotBlank(name) && !pri) {
            page = federationRepository.findByNameLike('%' + name + '%');
        } else if (StringUtils.isNotBlank(name) && pri) {
            page = federationRepository.findByNameLikeAndUuidIn('%' + name + '%', uuidList);
        } else if (pri) {
            page = federationRepository.findByUuidIn(uuidList);
        } else {
            page = federationRepository.findAll();
        }

        List<FederationVo> federations = new ArrayList<FederationVo>(page.size());
        //for (int i=0; i<page.size(); i++) {
        //    federations.set(i, getFederationVo(page.get(i)));
        //}
        for (FederationEntity federation : page) {
            FederationVo federationVo = getFederationVo(federation);
            if (federationVo.getGuest().equals(userId)) {
                federationVo.setRole("创建者");
            } else if (uuidList.contains(federationVo.getUuid())) {
                federationVo.setRole("参与者");
            } else {
                federationVo.setRole("");
            }
            federations.add(federationVo);

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
        } catch (Exception e) {
            log.error("cannot parse token", e.getMessage(), e);
            throw new APIException("token无效");
        }

        //FederationEntity data = federationService.queryFederationById(id);
        FederationEntity federation = federationRepository.findByUuid(id);
        if (federation == null) {
            throw new APIException(String.format("联邦UUID%s不存在", id));
        }

        // TODO
        /*
        boolean isHosts = true;
        if (federation.getGuest().equals( userId) || !isHosts) {
            throw new APIException("没有操作权限");
        }
        */
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("federation_uuid", id);
        List<UserFederation> userFederationList = userFederationService.list(queryWrapper);

        List<String> userList = new ArrayList<String>(userFederationList.size());
        for (UserFederation userFederation : userFederationList) {
            userList.add(userFederation.getUserId().toString());
        }
        FederationVo federationVo = getFederationVo(federation);
        if (federationVo.getGuest().equals(userId)) {
            federationVo.setRole("创建者");
        } else if (userList.contains(userId)) {
            federationVo.setRole("参与者");
        } else {
            federationVo.setRole("");
        }
        federationVo.setTrainDatasetPrepared(federationDatasetService.datasetPrepared(id, new Short((short) 0)));
        federationVo.setPredictDatasetPrepared(federationDatasetService.datasetPrepared(id, new Short((short) 1)));
        return federationVo;
    }

    /**
     * create a new federation
     */
    @SysLog("save federation")
    @PostMapping("")
    @Transactional
    public FederationVo save(@RequestHeader String token,
                             @Valid @RequestBody FederationDto federationDto) {
        if (federationDto == null) {
            throw new APIException("请求数据为空");
        }
        String userId = "";
        try {
            userId = TokenManager.parseJWT(token).getId();
            log.info("get user id", userId);
        } catch (Exception e) {
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
        if (CollectionUtils.isNotEmpty(list)) {
            throw new APIException("名字重复，请重试。");
        }

        federation.setId((short) 0);
        federation.setCreatedAt(new Date());
        federation.setGuest(userId);
        federation.setHosts("");
        federation.setStatus(new Integer(0));
        federation.setUserCount(new Short((short) 1));

        if (federation.getType() == null) {
            federation.setType(new Boolean(true));
        }
        federationRepository.save(federation);

        UserFederation userFederation = new UserFederation();
        userFederation.setUserId(Integer.parseInt(userId));
        userFederation.setFederationUUid(federation.getUuid());
        userFederation.setStatus("1");
        userFederationService.save(userFederation);

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
                       @PathVariable(name = "id") String id) {
        String userId = "";
        try {
            userId = TokenManager.parseJWT(token).getId();
            log.info("get user id", userId);
        } catch (Exception e) {
            log.error("cannot parse token", e.getMessage(), e);
            throw new APIException("token无效");
        }
        FederationEntity removedFederation = federationRepository.findByUuid(id);
        if (removedFederation == null) {
            throw new APIException(String.format("联邦UUID%s不存在", id));
        }

        /*
        if (!removedFederation.getGuest().equals(userId)) {
            throw new APIException("没有操作权限");
        }
        */
        federationRepository.deleteByUuid(id);
        //TODO need to delete records in tb_federation_dataset
        federationDatasetRepository.removeByFederationUuid(id);
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
                               @RequestBody FederationDto federationDto) throws APIException {
        if (federationDto == null) {
            throw new APIException("请求数据为空");
        }

        String userId = "";
        try {
            userId = TokenManager.parseJWT(token).getId();
            log.info("get user id", userId);
        } catch (Exception e) {
            log.error("cannot parse token", e.getMessage(), e);
            throw new APIException("token无效");
        }

        FederationEntity federation = fromFederationDto(federationDto);

        FederationEntity updatedFederation = federationRepository.findByUuid(id);
        if (updatedFederation == null) {
            throw new APIException(String.format("联邦UUID%s不存在", id));
        }

        /*
        if (!updatedFederation.getGuest().equals(userId)) {
            throw new APIException("没有操作权限");
        }
        */
        if (federation.getName() != null && !federation.getName().equals(updatedFederation.getName())) {
            List<FederationEntity> list = federationRepository.findByName(federation.getName());
            if (CollectionUtils.isNotEmpty(list)) {
                throw new APIException("名字重复，请重试。");
            }
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
                               @RequestParam Map<String, Object> params,
                               @PathVariable(name = "uuid") String uuid) throws APIException {
        String userId = "";
        try {
            userId = TokenManager.parseJWT(token).getId();
            log.info("get user id", userId);
        } catch (Exception e) {
            log.error("cannot parse token", e.getMessage(), e);
            throw new APIException("token无效");
        }

        /*
        String typeParam = (String) params.get("type");
        if (typeParam == null) {
            throw new APIException("没有找到数据类型参数");
        }
        if (!typeParam.equals("0") && !typeParam.equals("1")) {
            throw new APIException("错误的联邦类型参数");
        }
        Short type = Short.parseShort(typeParam);
        */

        FederationEntity updatedFederation = federationRepository.findByUuid(uuid);
        if (updatedFederation == null) {
            throw new APIException(String.format("联邦UUID%s不存在", uuid));
        }
        /*
        if (!updatedFederation.getGuest().equals(userId)) {
            throw new APIException("没有操作权限");
        }
        */
        Integer status = updatedFederation.getStatus();
        if (status != 0 && status != 3 && status != 4) {
            throw new APIException(String.format("不能就绪状态%s的联邦", getReadableStatusFromCode(updatedFederation.getStatus())));
        }

        // upload data when train or predict
        // federationDatasetService.uploadData(uuid, type);
        // attributes update permitted
        updatedFederation.setStatus(1);
        federationRepository.save(updatedFederation);
        return getFederationVo(updatedFederation);
    }

    /**
     * list federation datasets
     */
    @GetMapping("/{uuid}/datasets")
    @SysLog("query datasets")
    public List<FederationDatasetVo> getdatasets(@RequestHeader String token,
                                                 @RequestParam Map<String, Object> params,
                                                 @PathVariable(name = "uuid") String uuid) throws APIException {
        Integer partyId = -1;
        try {
            String userId = TokenManager.parseJWT(token).getId();
            log.info("get user id", userId);
            partyId = Integer.valueOf(userId);
            ;
        } catch (Exception e) {
            log.error("cannot parse token", e.getMessage(), e);
            throw new APIException("token无效");
        }
        if (partyId == -1) {
            throw new APIException("无法获取partyId");
        }

        String typeParam = (String) params.get("type");
        if (typeParam == null) {
            throw new APIException("没有找到数据类型参数");
        }
        if (!typeParam.equals("0") && !typeParam.equals("1")) {
            throw new APIException("错误的联邦类型参数");
        }
        Short type = Short.parseShort(typeParam);
        // Step 1: get all federation parties
        FederationEntity federation = federationRepository.findByUuid(uuid);
        if (federation == null) {
            throw new APIException(String.format("联邦UUID%s不存在", uuid));
        }
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("federation_uuid", uuid);
        List<UserFederation> userFederationList = userFederationService.list(queryWrapper);
        Map<Integer, Boolean> datasetMap = new HashMap<Integer, Boolean>();
        for (UserFederation uf : userFederationList) {
            datasetMap.put(uf.getUserId(), new Boolean(false));
        }

        // Step 2: get all existed federation datasets
        List<FederationDataset> federationDatasets = federationDatasetRepository.findByFederationUuidAndType(uuid, type);

        //Step 3: merge dataset with parties
        for (FederationDataset fd : federationDatasets) {
            datasetMap.remove(fd.getPartyId());
        }
        for (Integer datasetPartyId : datasetMap.keySet()) {
            federationDatasets.add(new FederationDataset(uuid, datasetPartyId));
        }

        List<FederationDatasetVo> datasets = new ArrayList<FederationDatasetVo>(federationDatasets.size());
        for (FederationDataset federationDataset : federationDatasets) {
            datasets.add(getFederationDatasetVo(federationDataset, partyId));
        }
        return datasets;
    }

    /**
     * update a federation dataset
     */
    @PostMapping("/{uuid}/datasets")
    @SysLog("update federation dataset")
    @Transactional
    public FederationDataset updateDataset(@RequestHeader String token,
                                           @RequestParam Map<String, Object> params,
                                           @RequestBody Dataset inputDataset,
                                           @PathVariable(name = "uuid") String uuid) throws APIException {
        Integer partyId = -1;
        try {
            String userId = TokenManager.parseJWT(token).getId();
            log.info("get user id", userId);
            partyId = Integer.valueOf(userId);
            ;
        } catch (Exception e) {
            log.error("cannot parse token", e.getMessage(), e);
            throw new APIException("token无效");
        }
        if (partyId == -1) {
            throw new APIException("无法获取partyId");
        }

        String typeParam = (String) params.get("type");
        if (typeParam == null) {
            throw new APIException("没有找到数据类型参数");
        }
        if (!typeParam.equals("0") && !typeParam.equals("1")) {
            throw new APIException("错误的联邦类型参数");
        }
        Short type = Short.parseShort(typeParam);

        FederationEntity federation = federationRepository.findByUuid(uuid);
        if (federation == null) {
            throw new APIException(String.format("联邦UUID%s不存在", uuid));
        }

        Dataset dataset = datasetRepository.findById(inputDataset.getId());
        if (dataset == null) {
            throw new APIException(String.format(String.format("数据集%s不存在", inputDataset.getName())));
        }
        if (dataset.getPartyId().intValue() != partyId.intValue()) {
            throw new APIException(String.format(String.format("数据集不属于当前用户。当前用户：%d。数据集用户%d。", partyId, dataset.getPartyId())));
        }

        FederationDataset federationDataset = new FederationDataset(uuid, partyId);
        federationDataset.setName(dataset.getName());
        federationDataset.setUpdatedAt(dataset.getUpdatedAt());
        federationDataset.setSize(dataset.getSize());
        federationDataset.setRows(dataset.getRows());
        federationDataset.setType(type);
        federationDatasetRepository.removeByFederationUuidAndPartyIdAndType(uuid, partyId, type);
        federationDataset = federationDatasetRepository.save(federationDataset);
        return federationDataset;
    }

    /**
     * update a federation
     */
    @DeleteMapping("/{uuid}/datasets")
    @SysLog("update federation dataset")
    @Transactional
    public void deleteDataset(@RequestHeader String token,
                              @RequestParam Map<String, Object> params,
                              @PathVariable(name = "uuid") String uuid) throws APIException {
        Integer partyId = -1;
        try {
            String userId = TokenManager.parseJWT(token).getId();
            log.info("get user id", userId);
            partyId = Integer.valueOf(userId);
            ;
        } catch (Exception e) {
            log.error("cannot parse token", e.getMessage(), e);
            throw new APIException("token无效");
        }
        if (partyId == -1) {
            throw new APIException("无法获取partyId");
        }

        String typeParam = (String) params.get("type");
        if (typeParam == null) {
            throw new APIException("没有找到数据类型参数");
        }
        if (!typeParam.equals("0") && !typeParam.equals("1")) {
            throw new APIException("错误的联邦类型参数");
        }
        Short type = Short.parseShort(typeParam);

        FederationEntity federation = federationRepository.findByUuid(uuid);
        if (federation == null) {
            throw new APIException(String.format("联邦UUID%s不存在", uuid));
        }
        federationDatasetRepository.removeByFederationUuidAndPartyIdAndType(uuid, partyId, type);

        return;
    }

    /**
     * federation statistic
     */
    @GetMapping("/statistic")
    @SysLog("get federations statistic")
    public List<Statistic> statistic() throws Exception {
        Map<Integer, Short> statistic = new HashMap<Integer, Short>();
        List<FederationEntity> page = federationRepository.findAll();
        Integer[] statusList = {0, 1, 2, 3, 4};
        for (Integer s : statusList) {
            statistic.put(s, new Short((short) 0));
        }
        for (FederationEntity federation : page) {
            Integer status = federation.getStatus();
            if (statistic.get(status) == null) {
                statistic.put(status, new Short((short) 1));
            } else {
                statistic.put(status, new Short((short) (statistic.get(status) + 1)));
            }
        }

        List<Statistic> result = new ArrayList<Statistic>(statistic.size());
        for (Integer s : statusList) {
            result.add(new Statistic(getReadableStatusFromCode(s), statistic.get(s)));
        }
        result.add(new Statistic("所有", new Short((short) page.size())));
        return result;
    }

    public FederationEntity fromFederationDto(FederationDto federationDto) {
        FederationEntity federation = new FederationEntity();
        BeanUtils.copyProperties(federationDto, federation);
        federation.setDataFormat(JSON.toJSONString(federationDto.getDataFormat()));
        federation.setParam(JSON.toJSONString(federationDto.getParam()));
        return federation;
    }

    public FederationVo getFederationVo(FederationEntity federation) {
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
        federationVo.setParam(JSON.parseObject(federation.getParam(), new TypeReference<LinkedHashMap<String, Double>>() {
        }));

        String username = "用户信息丢失";
        User user = userService.findById(federation.getGuest());
        if (user != null) {
            username = user.getUsername();
        }
        federationVo.setGuestName(username);
        return federationVo;
    }

    public FederationDatasetVo getFederationDatasetVo(FederationDataset federationDataset, Integer partyId) {
        FederationDatasetVo federationDatasetVo = new FederationDatasetVo();
        if (federationDataset == null) {
            return federationDatasetVo;
        }
        BeanUtils.copyProperties(federationDataset, federationDatasetVo);
        federationDatasetVo.setEditable(new Boolean(partyId.equals(federationDataset.getPartyId())));
        return federationDatasetVo;
    }

    public static String getReadableStatusFromCode(Integer status) {
        switch (status) {
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
