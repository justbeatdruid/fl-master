package com.cmcc.algo.entity;

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
     private Integer id;
     private String permission; //权限id
     private String name; // 菜单名称
     private String linkUrl; // 访问路径
     private String redirectUrl; //跳转路径
     private String path;//菜单项所对应的路由路径
     private Integer priority; // 优先级（用于排序）
     private String description; // 描述
     private String icon;//图标
     private Set<Role> roles = new HashSet<Role>(0);//角色集合
     private Integer parentMenuId;//父菜单id
}
