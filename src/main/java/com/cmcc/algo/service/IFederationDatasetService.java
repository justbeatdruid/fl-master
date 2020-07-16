package com.cmcc.algo.service;

import com.cmcc.algo.entity.Dataset;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hjy
 * @since 2020-05-25
 */
public interface IFederationDatasetService {

    void uploadData(String federationUuid, Short dataType);

    boolean datasetPrepared(String federationUuid, Short dataType);
}
