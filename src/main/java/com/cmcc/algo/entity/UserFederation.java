package com.cmcc.algo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @PackageName: com.cmcc.algo.entity
 * @ClassName: UserFederation
 * @Author: lc
 * @Description: 用户联邦关系实体类
 */
@Data
@TableName("tb_user_federation")
public class UserFederation implements Serializable {

     private static final long serialVersionUID = 1L;

     @TableId(value = "id", type = IdType.AUTO)
     private Integer id;

     /**
      * 用户ID
      */
     @TableField(value = "user_id")
     private Integer userId;

     /**
      * 联邦UUID
      */
     @TableField(value = "federation_uuid")
     private String federationUUid;

     /**
      * 表示用户在联邦的角色,0表示host,1表示guest
      */
     @TableField(value = "status")
     private String status;

     @TableField(exist = false)
     private User user;
}
