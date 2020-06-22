package com.cmcc.algo.mapper;

import com.cmcc.algo.entity.FederationDataset;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hjy
 * @since 2020-05-25
 */
@Repository
public interface FederationDatasetRepository extends JpaRepository<FederationDataset, Long> {
    List<FederationDataset> findByFederationUuidAndType(String federationUuid, Short type);
    void removeByFederationUuidAndPartyId(String federationUuid, Integer partyId);
}
