package com.cmcc.algo.mapper;

import com.cmcc.algo.entity.FederationEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 联邦信息表 Mapper 接口
 * </p>
 *
 * @author hjy
 * @since 2020-05-25
 */
@Repository
public interface FederationRepository extends JpaRepository<FederationEntity, Long> {
    FederationEntity findByUuid(String uuid);
    void deleteByUuid(String uuid);
    List<FederationEntity> findByName(String name);
    List<FederationEntity> findByNameLike(String name);
}
