package com.cmcc.algo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cmcc.algo.mapper.FederationMapper;
import com.cmcc.algo.entity.FederationEntity;
//import com.cmcc.algo.service.*;
import com.cmcc.algo.service.IFederationService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 联邦信息表 服务实现类
 * </p>
 *
 * @author hjy
 * @since 2020-05-25
 */
@Service("federationService")
public class FederationServiceImpl extends ServiceImpl<FederationMapper, FederationEntity> implements IFederationService {
	@Autowired
	private IFederationService federationService;
	@Autowired
	private FederationMapper federationMapper;
	@Override
	public List<Map<String, Object>> queryFederations(Map<String, Object> params) {
                String name = (String)params.get("name");

		//List<Map<String, Object>> maps = this.listMaps(new QueryWrapper<FederationEntity>());
                List<Map<String, Object>> maps = this.listMaps(
                        new QueryWrapper<FederationEntity>()
                                .eq(StringUtils.isNotBlank(name), "name", name)
                );

		return maps;
	}

	@Override
	public FederationEntity queryFederationById(String id) {
		return federationMapper.queryById(id);
	}

	@Override
	public FederationEntity saveFederation(FederationEntity federation) {
		federation.setCreatedAt(new Date());
		this.save(federation);
		return federation;
	}
}
