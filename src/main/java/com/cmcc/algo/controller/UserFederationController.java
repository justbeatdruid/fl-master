package com.cmcc.algo.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cmcc.algo.common.APIException;
import com.cmcc.algo.common.CommonResult;
import com.cmcc.algo.common.ResultCode;
import com.cmcc.algo.common.utils.TokenManager;
import com.cmcc.algo.entity.User;
import com.cmcc.algo.entity.UserFederation;
import com.cmcc.algo.service.IFederationService;
import com.cmcc.algo.service.IUserFederationService;
import com.cmcc.algo.service.IUserService;
import io.swagger.annotations.*;
import jodd.db.QueryMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @PackageName: com.cmcc.algo.controller
 * @ClassName: UserFederationController
 * @Author: lc
 * @Description: //TODO
 */
@Api(tags = "联邦成员管理操作类")
@Slf4j
@RestController
@RequestMapping("/userFederation")
public class UserFederationController {

     @Autowired
     private IUserFederationService userFederationService;

     @Autowired
     private IUserService userService;

     @Autowired
     private IFederationService federationService;

     /**
      * 申请加入联邦
      *
      * @param
      * @param
      * @return
      */
     @ApiOperation(value = "申请加入联邦")
     @ApiImplicitParams({@ApiImplicitParam(name = "token", value = "头部token信息", paramType = "header", required = false),
             @ApiImplicitParam(name = "federationUUid", value = "联邦UUID", paramType = "body", required = true),
             @ApiImplicitParam(name = "params", value = "Map", paramType = "body", required = false)})
     @PostMapping("/apply")
     @Transactional(rollbackFor = Exception.class)
     public CommonResult apply(@RequestHeader String token, @RequestBody Map<String, String> params) {
          String userId = "";
          try {
               userId = TokenManager.parseJWT(token).getId();
               log.info("get user id", userId);
          } catch (Exception e) {
               log.error("cannot parse token", e.getMessage(), e);
               throw new APIException("token无效");
          }
          String federationUUid = params.get("federationUUid");
          QueryWrapper queryWrapper = new QueryWrapper();
          queryWrapper.eq("user_id", userId);
          queryWrapper.eq("federation_uuid", federationUUid);
          List<UserFederation> userFederationList = userFederationService.list(queryWrapper);
          if (userFederationList.size() > 0) {
               throw new APIException(ResultCode.FORBIDDEN, "重复申请!");
          }
          UserFederation userFederation = new UserFederation();
          userFederation.setUserId(Integer.parseInt(userId));
          userFederation.setFederationUUid(federationUUid);
          userFederation.setStatus("0");
          userFederationService.save(userFederation);
          federationService.userCountIncrease(federationUUid);
          return CommonResult.success("申请加入成功", new int[0]);
     }

     /**
      * 退出联邦
      *
      * @param token
      * @param federationUUid
      * @return
      */
     @ApiOperation(value = "退出联邦")
     @ApiImplicitParams({@ApiImplicitParam(name = "token", value = "头部token信息", paramType = "header", required = false),
             @ApiImplicitParam(name = "federationUUid", value = "联邦UUID", dataType = "String", paramType = "path", required = true)
     })
     @DeleteMapping("/logout")
     public CommonResult logout(@RequestHeader String token, @RequestParam String federationUUid) {
          String userId = "";
          try {
               userId = TokenManager.parseJWT(token).getId();
               log.info("get user id", userId);
          } catch (Exception e) {
               log.error("cannot parse token", e.getMessage(), e);
               throw new APIException("token无效");
          }
          QueryWrapper queryWrapper = new QueryWrapper();
          queryWrapper.eq("user_id", userId);
          queryWrapper.eq("federation_uuid", federationUUid);
          UserFederation userFederation = userFederationService.getOne(queryWrapper);
          if (userFederation == null) {
               return CommonResult.success("资源为空", new int[0]);
          }
          userFederationService.remove(queryWrapper);
          federationService.userCountDecrease(federationUUid);
          return CommonResult.success("退出成功", new int[0]);
     }

/**
 * 审批操作
 *
 * @param userId
 * @param federationUUid
 * @param type
 * @return
 *//*
     @PutMapping("/access")
     @Transactional(rollbackFor = Exception.class)
     public CommonResult access(@RequestBody Map<String, String> params) {
          String userId = params.get("userId");
          String federationUUid = params.get("federationUUid");
          String type = params.get("type");
          QueryWrapper queryWrapper = new QueryWrapper();
          queryWrapper.eq("user_id", userId);
          queryWrapper.eq("federation_uuid", federationUUid);
          List<UserFederation> userFederationList = userFederationService.list(queryWrapper);
          if (CollectionUtils.isEmpty(userFederationList)) {
               throw new APIException(ResultCode.NOT_FOUND, "数据异常");
          }
          if (userFederationList.get(0).getStatus().equals("1") || userFederationList.get(0).getStatus().equals("2")) {
               throw new APIException(ResultCode.REQUEST_ERROR, "已审批,重复操作!");
          }
          //默认:0,同意:1,拒绝:2
          switch (type) {
               case "1":
                    userFederationList.get(0).setStatus("1");
                    userFederationService.updateById(userFederationList.get(0));
                    return CommonResult.success("审批成功,已加入");
               case "2":
                    userFederationList.get(0).setStatus("2");
                    userFederationService.updateById(userFederationList.get(0));
                    return CommonResult.success("已拒绝加入！");
               default:
                    throw new APIException(ResultCode.FORBIDDEN, "参数异常！！！");
          }
     }*/


     /**
      * 我的联邦成员列表
      *
      * @param federationUUid
      * @return
      */
     @ApiOperation(value = "我的联邦成员列表")
     @ApiImplicitParam(name = "federationUUid", value = "联邦UUID", required = true, dataType = "String", paramType = "path")
     @GetMapping("/list")
     public CommonResult list(@RequestParam String federationUUid) {
          QueryWrapper queryWrapper = new QueryWrapper();
          queryWrapper.eq("federation_uuid", federationUUid);
          List<UserFederation> userFederationList = userFederationService.list(queryWrapper);
          if (CollectionUtils.isEmpty(userFederationList)) {
               int[] zero = new int[0];
               return CommonResult.success("数据为空", zero);
          }
          for (UserFederation userFederation : userFederationList) {
               User user = userService.findById(userFederation.getUserId().toString());
               userFederation.setUser(user);
          }
          return CommonResult.success(userFederationList);
     }

     /**
      * 删除联邦现有用户
      *
      * @param
      * @return
      */
     @ApiOperation(value = "删除联邦现有用户")
     @ApiImplicitParams({@ApiImplicitParam(name = "userId", value = "用户ID", paramType = "body", required = true),
             @ApiImplicitParam(name = "federationUUid", value = "联邦UUID", paramType = "body", required = true),
             @ApiImplicitParam(name = "params", value = "Map", paramType = "body", required = false)})
     @DeleteMapping("/delete")
     @Transactional(rollbackFor = Exception.class)
     public CommonResult delUser(@RequestBody Map<String, String> params) {
          String userId = params.get("userId");
          String federationUUid = params.get("federationUUid");
          QueryWrapper queryWrapper = new QueryWrapper();
          queryWrapper.eq("user_id", userId);
          queryWrapper.eq("federation_uuid", federationUUid);
          UserFederation userFederation = userFederationService.getOne(queryWrapper);
          if (userFederation == null) {
               return CommonResult.success("此成员不存在", new int[0]);
          }
          userFederationService.remove(queryWrapper);
          return CommonResult.success("删除成功", new int[0]);
     }

}
