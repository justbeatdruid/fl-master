package com.cmcc.algo.service;

import com.baomidou.mybatisplus.extension.service.IService;
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
public interface IFederationService extends IService<FederationEntity> {
	List<Map<String, Object>> queryFederations(Map<String, Object> params);

	FederationEntity queryFederationById(String id);

	FederationEntity saveFederation(FederationEntity federation);
}
