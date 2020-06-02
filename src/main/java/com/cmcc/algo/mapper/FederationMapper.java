package com.cmcc.algo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cmcc.algo.entity.FederationEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 联邦信息表 Mapper 接口
 * </p>
 *
 * @author hjy
 * @since 2020-05-25
 */
@Mapper
public interface FederationMapper extends BaseMapper<FederationEntity> {

    FederationEntity queryById(String id);

}
