package com.cmcc.algo.service;

import com.cmcc.algo.entity.FederationEntity;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 联邦信息表 服务类
 * </p>
 *
 * @author hjy
 * @since 2020-05-25
 */
public interface IFederationService {

     List<FederationEntity> queryFederations(Map<String, Object> params);

     List<FederationEntity> findListByGuest(String guest);

     FederationEntity getOne(Long id);

}
