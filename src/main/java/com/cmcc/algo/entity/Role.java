package com.cmcc.algo.entity;

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
     private Integer id;
     private String name; // 角色名称
     private String keyword; // 角色关键字，用于权限控制
     private String description; // 描述
     private Set<User> users = new HashSet<>(0);
     private LinkedHashSet<Menu> menus = new LinkedHashSet<>(0);
}
