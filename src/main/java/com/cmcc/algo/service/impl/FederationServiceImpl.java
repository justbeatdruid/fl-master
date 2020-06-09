package com.cmcc.algo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cmcc.algo.entity.FederationEntity;
import com.cmcc.algo.mapper.FederationMapper;
import com.cmcc.algo.service.IFederationService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

//import com.cmcc.algo.service.*;

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
        public List<FederationEntity> queryFederations(Map<String, Object> params) {
                String name = (String)params.get("name");

                //List<Map<String, Object>> maps = this.listMaps(new QueryWrapper<FederationEntity>());
                List<FederationEntity> maps = this.list(
                        new QueryWrapper<FederationEntity>()
                                .like(StringUtils.isNotBlank(name), "name", name)
                );

                return maps;
        }

        @Override
        public FederationEntity saveFederation(FederationEntity federation) {
                //short short0 = 0;
                //federation.setId(new Short(short0));
                federation.setId((short) 0);
                federation.setCreatedAt(new Date());

                federation.setGuest("");
                federation.setHosts("");
                federation.setStatus(new Integer(0));
                this.save(federation);
                return federation;
        }
}
