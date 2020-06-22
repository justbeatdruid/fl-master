package com.cmcc.algo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


@Data
@TableName("tb_role_menu")
public class RoleMenu {

     @TableId(value = "id", type = IdType.AUTO)
     private Integer id;

     @TableField(value = "role_id")
     private Integer roleId;

     @TableField(value = "menu_id")
     private String menuId;

}
