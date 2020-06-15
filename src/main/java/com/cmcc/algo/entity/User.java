package com.cmcc.algo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 用户信息表
 * </p>
 *
 * @author hjy
 * @since 2020-05-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_user")
public class User implements Serializable {

     private static final long serialVersionUID = 1L;

     /**
      * 用户ID
      */
     @TableId(value = "id", type = IdType.AUTO)
     private Integer id;

     /**
      * 物理机ID
      */
     @Column(name = "party_id")
     private Integer partyId;

     /**
      * 用户名
      */
     private String username;

     /**
      * 用户密码
      */
     private String password;

     /**
      * 用户电话
      */
     private String phone;

     /**
      * 用户邮箱
      */
     private String email;

     /**
      * 公司电话
      */
     private String companyPhone;

     /**
      * 公司名
      */
     private String companyName;

     /**
      * 通讯地址
      */
     private String address;

     /**
      * 对应角色集合
      */
     @TableField(exist = false)
     private Set<Role> roles = new HashSet<>(0);

     /**
      * token
      */
     @TableField(exist = false)
     private String token;

     /**
      * 删除标志位，0为正常，1为注销状态
      */
     private Integer delFlag;

     /**
      * 创建的联邦
      */
     @TableField(exist = false)
     private List<FederationEntity> federationList;

     /**
      * 参与的联邦
      */
     @TableField(exist = false)
     private List<FederationEntity> joinFederation;

}
