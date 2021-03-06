package com.cmcc.algo.mapper;

import com.cmcc.algo.entity.Dataset;

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
public interface DatasetRepository extends JpaRepository<Dataset, Long> {
    Dataset findById(Short Id);
    List<Dataset> findByPartyId(Integer partyId);
    List<Dataset> findByNameAndPartyId(String name, Integer partyId);
}
