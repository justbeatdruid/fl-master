package com.cmcc.algo.service.impl;

//import com.cmcc.algo.entity.Dataset;
//import com.cmcc.algo.mapper.DatasetRepository;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.cmcc.algo.common.APIException;
import com.cmcc.algo.config.AgentConfig;
import com.cmcc.algo.entity.FederationDataset;
import com.cmcc.algo.entity.UserFederation;
import com.cmcc.algo.service.IFederationDatasetService;
import com.cmcc.algo.service.IUserFederationService;
import com.cmcc.algo.mapper.FederationDatasetRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
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
@Slf4j
public class FederationDatasetServiceImpl implements IFederationDatasetService {

    @Autowired
    private FederationDatasetRepository federationDatasetRepository;
    @Autowired
    private AgentConfig agentConfig;
    @Autowired
    private IUserFederationService userFederationService;

    @Override
    public List<String> uploadData(String federationUuid, Short dataType) {
        List<String> responseList = new ArrayList<>();
        List<FederationDataset> federationDatasets = federationDatasetRepository.findByFederationUuidAndType(federationUuid, dataType);
        for ( FederationDataset federationDataset : federationDatasets ) {
            responseList.add(uploadPartyData(federationUuid, federationDataset.getPartyId(), dataType));
        }
        return responseList;
    }

    public String uploadPartyData(String federationUuid, Integer partyId, Short dataType) {
//        RestTemplate restTemplate = new RestTemplate();
        String url = agentConfig.getAgentUrl(partyId) + "/com/cmcc/datafusion/agent/dataset/upload";
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        map.add("federationUuid", federationUuid);
        map.add("dataType", dataType.toString());
        map.add("partyId", partyId.toString());
//        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
//        ResponseEntity<String> response = restTemplate.postForEntity(url, request , String.class);
//        if (response.getStatusCodeValue() / 100 != 2) {
//            throw new APIException(String.format("request for upload data error: %s", response.toString()));
//        }
        JSONObject requestObj = new JSONObject();
        requestObj.putAll(map);
        String request = JSONUtil.toJsonStr(requestObj);

        String responseBody = HttpUtil.post(url, request);
        log.info(responseBody);
        return responseBody;
    }

    @Override
    public boolean datasetPrepared(String federationUuid, Short dataType) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("federation_uuid", federationUuid);
        try {
            List<UserFederation> userFederationList = userFederationService.list(queryWrapper);
        
            for ( UserFederation userFederation : userFederationList ) {
                FederationDataset federationDataset = federationDatasetRepository.findByFederationUuidAndPartyIdAndType(federationUuid, Integer.valueOf(userFederation.getUserId()), dataType);
                if (federationDataset == null || federationDataset.getName() == null || federationDataset.getName().isEmpty()) {
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
