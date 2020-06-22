package com.cmcc.algo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * 菜单实体类
 */
@Data
@TableName("tb_menu")
public class Menu implements Serializable {
     private static final long serialVersionUID = 1L;

     @TableId(value = "id", type = IdType.AUTO)
     private Integer id;

     /**
      * 权限
      */
     @TableField(value = "permissioncode")
     private String permissionCode;

     /**
      * 菜单名称
      */
     @TableField(value = "name")
     private String name; // 菜单名称

     /**
      * 菜单项所对应的路由路径
      */
     @TableField(value = "path")
     private String path;//菜单项所对应的路由路径

     /**
      * 描述
      */
     @TableField(value = "description")
     private String description; // 描述
     
}
