package com.cmcc.algo.controller;

import com.cmcc.algo.common.annotation.SysLog;
import com.cmcc.algo.common.utils.R;
import com.cmcc.algo.common.validator.ValidatorUtils;
import com.cmcc.algo.entity.FederationEntity;
import com.cmcc.algo.service.IFederationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * <p>
 * 联邦信息表 前端控制器
 * </p>
 *
 * @author hjy
 * @since 2020-05-25
 */
@RestController
@RequestMapping("/federation")
public class FederationController {
    @Value("${requesturls.url}")
    private String url;

    @Autowired
    private IFederationService federationService;

    /**
     * list federations
     */
    @GetMapping("")
    public R federations(@RequestParam Map<String, Object> params) {

        List<Map<String, Object>> page = federationService.queryFederations(params);

        return R.ok().put("data", page);
    }

    @GetMapping("/{id}")
    public R get(@PathVariable("id") String id) {

        //FederationEntity data = federationService.queryFederationById(id);
        FederationEntity data = federationService.getById(id);

        return R.ok().put("data", data);
    }

    /**
      * create a new federation
      */
    @SysLog("save federation")
    @PostMapping("")
    public R save(@RequestBody FederationEntity federation){
        //ValidatorUtils.validateEntity(federation, AddGroup.class);

        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        //federation.setCreateUserId("");
        federation.setUuid(uuid);
        federationService.saveFederation(federation);
        return R.ok();
    }

    /**
     * delete a federation
     */
    @SysLog("delete federation")
    @DeleteMapping("/{id}")
    public R delete(@PathVariable(name = "id") long id){
        federationService.removeById(id);
        return R.ok();
    }

    /**
     * update a federation
     */
    @PutMapping("")
    public R update(@RequestBody FederationEntity federation) throws Exception {
        if (federation == null) {
          throw new Exception("federation entity is null");
        }
        long id = federation.getId();
        FederationEntity newFederation = federationService.getById(id);
        if (newFederation == null) {
          throw new Exception("get from database federation entity is null");
        }
	if (federation.getName() != null) {
	    newFederation.setName(federation.getName());
	}
        federationService.saveFederation(newFederation);
	return R.ok();
    }
}
