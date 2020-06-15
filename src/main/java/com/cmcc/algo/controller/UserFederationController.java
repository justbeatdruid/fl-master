package com.cmcc.algo.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cmcc.algo.common.APIException;
import com.cmcc.algo.common.CommonResult;
import com.cmcc.algo.entity.User;
import com.cmcc.algo.entity.UserFederation;
import com.cmcc.algo.service.IUserFederationService;
import com.cmcc.algo.service.IUserService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @PackageName: com.cmcc.algo.controller
 * @ClassName: UserFederationController
 * @Author: lc
 * @Description: //TODO
 */
@RestController
@RequestMapping("/userFederation")
public class UserFederationController {

     @Autowired
     private IUserFederationService userFederationService;

     @Autowired
     private IUserService userService;

     /**
      * 申请加入联邦
      *
      * @param userId
      * @param federationId
      * @return
      */
     @PostMapping("/apply")
     public CommonResult apply(String userId, String federationId) {
          QueryWrapper queryWrapper = new QueryWrapper();
          queryWrapper.eq("user_id", userId);
          queryWrapper.eq("federation_id", federationId);
          List<UserFederation> userFederationList = userFederationService.list(queryWrapper);
          if (userFederationList.size() > 1) {
               throw new APIException("重复申请！");
          }
          UserFederation userFederation = new UserFederation();
          userFederation.setUserId(Integer.parseInt(userId));
          userFederation.setFederationId(federationId);
          userFederation.setStatus("0");
          userFederationService.save(userFederation);
          return CommonResult.success("申请加入成功，待审批！");
     }

     /**
      * 审批操作
      *
      * @param userId
      * @param federationId
      * @param type
      * @return
      */
     @PostMapping("/access")
     public CommonResult access(@RequestParam(name = "userId") String userId,
                                @RequestParam(name = "federationId") String federationId,
                                @RequestParam(name = "type") String type) {
          QueryWrapper queryWrapper = new QueryWrapper();
          queryWrapper.eq("user_id", userId);
          queryWrapper.eq("federation_id", federationId);
          List<UserFederation> userFederationList = userFederationService.list(queryWrapper);
          if (CollectionUtils.isEmpty(userFederationList)) {
               throw new APIException("数据异常");
          }
          if (userFederationList.size() > 1) {
               throw new APIException("重复申请！");
          }
          //默认:0，同意:1，不同意:2
          switch (type) {
               case "1":
                    userFederationList.get(0).setStatus("1");
                    break;
               case "2":
                    userFederationList.get(0).setStatus("2");
                    break;
               default:
                    throw new APIException("参数异常");

          }
          userFederationService.updateById(userFederationList.get(0));
          return CommonResult.success("加入成功");
     }

     /**
      * 申请成员列表
      *
      * @param federationId
      * @return
      */
     @PostMapping("/list")
     public CommonResult list(@RequestParam(name = "federationId") String federationId) {
          QueryWrapper queryWrapper = new QueryWrapper();
          queryWrapper.eq("federation_id", federationId);
          List<UserFederation> userFederationList = userFederationService.list(queryWrapper);
          if (CollectionUtils.isEmpty(userFederationList)) {
               throw new APIException("无申请用户");
          }
          for (UserFederation x : userFederationList) {
               User user = userService.findById(x.getUserId().toString());
               x.setUser(user);
          }
          return CommonResult.success(userFederationList);
     }

}
