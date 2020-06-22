package com.cmcc.algo.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cmcc.algo.common.APIException;
import com.cmcc.algo.common.CommonResult;
import com.cmcc.algo.common.ResultCode;
import com.cmcc.algo.common.utils.TokenManager;
import com.cmcc.algo.entity.User;
import com.cmcc.algo.entity.UserFederation;
import com.cmcc.algo.service.IUserFederationService;
import com.cmcc.algo.service.IUserService;
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
@Slf4j
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
      * @param
      * @param federationUUid
      * @return
      */
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
               throw new APIException(ResultCode.FORBIDDEN, "重复申请或已被拒绝加入联邦！");
          }
          UserFederation userFederation = new UserFederation();
          userFederation.setUserId(Integer.parseInt(userId));
          userFederation.setFederationUUid(federationUUid);
          userFederation.setStatus("0");
          userFederationService.save(userFederation);
          return CommonResult.success("申请加入成功，待审批！");
     }

     /**
      * 审批操作
      *
      * @param userId
      * @param federationUUid
      * @param type
      * @return
      */
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
     }


     /**
      * 我的联邦成员列表
      *
      * @param status
      * @return
      */
     @GetMapping("/list")
     public CommonResult list(@RequestParam String federationUUid, @RequestParam String status) {
          if (status.equals("0") || status.equals("1")) {
               QueryWrapper queryWrapper = new QueryWrapper();
               queryWrapper.eq("status", status);
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
          return CommonResult.fail(ResultCode.NOT_FOUND);
     }

     /**
      * 删除联邦现有用户
      *
      * @param
      * @return
      */
     @DeleteMapping("/delete")
     @Transactional(rollbackFor = Exception.class)
     public CommonResult delUser(@RequestBody Map<String, String> params) {
          String userId = params.get("userId");
          String federationUUid = params.get("federationUUid");
          QueryWrapper queryWrapper = new QueryWrapper();
          queryWrapper.eq("user_id", userId);
          queryWrapper.eq("federation_uuid", federationUUid);
          List<UserFederation> userFederationList = userFederationService.list(queryWrapper);
          if (userFederationList.get(0).getStatus().equals("4")) {
               throw new APIException(ResultCode.FORBIDDEN, "参数异常,请核对");
          }
          //4:删除退出联邦
          userFederationList.get(0).setStatus("4");
          userFederationService.updateById(userFederationList.get(0));
          return CommonResult.success("删除成功");
     }

}
