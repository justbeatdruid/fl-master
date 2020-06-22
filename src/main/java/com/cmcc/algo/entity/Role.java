package com.cmcc.algo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 角色实体类
 */
@Data
@TableName("tb_role")
public class Role implements Serializable {
     private static final long serialVersionUID = 1L;

     @TableId(value = "id", type = IdType.AUTO)
     private Integer id;

     @TableField(value = "rolename")
     private String roleName;

     @TableField(value = "keyword")
     private String keyword;

     @TableField(value = "description")
     private String description;

}
