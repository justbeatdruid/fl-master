package com.cmcc.algo.service.impl;

import com.cmcc.algo.entity.FederationEntity;
import com.cmcc.algo.mapper.FederationRepository;
import com.cmcc.algo.service.IFederationService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
//@Service("federationService")
@Service
public class FederationServiceImpl implements IFederationService {
     @Autowired
     private FederationRepository federationRepository;

     @Override
     public List<FederationEntity> queryFederations(Map<String, Object> params) {
          String name = (String) params.get("name");
          Boolean pri = Boolean.parseBoolean((String) params.get("private"));

          //List<Map<String, Object>> maps = this.listMaps(new QueryWrapper<FederationEntity>());
          //List<FederationEntity> maps = this.list(
          //        new QueryWrapper<FederationEntity>()
          //                .like(StringUtils.isNotBlank(name), "name", name)
          //);
          if (name != null && name != "") {
               return federationRepository.findByNameLike(name);
          }
          return federationRepository.findAll();
     }

     @Override
     public List<FederationEntity> findListByGuest(String guest) {
          if (StringUtils.isNotBlank(guest)) {
               return federationRepository.findListByGuest(guest);
          }
          return federationRepository.findAll();
     }

     @Override
     public FederationEntity getOne(Long id) {
          return federationRepository.getOne(id);
     }

}
