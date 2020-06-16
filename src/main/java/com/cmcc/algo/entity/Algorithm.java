package com.cmcc.algo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 算法信息表
 * </p>
 *
 * @author hjy
 * @since 2020-05-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_algorithm")
@ApiModel
public class Algorithm implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 算法ID
     */
    @ApiModelProperty(value = "自增id")
    private Integer id;

    /**
     * 算法名
     */
    @ApiModelProperty(value = "算法名")
    private String algorithmName;

    @ApiModelProperty(value = "算法中文名")
    private String displayName;

    @ApiModelProperty(value = "联邦学习类型（0：纵向，1：横向）")
    private Integer federationType;

    @ApiModelProperty(value = "算法类型（0：分类，1：回归）")
    private Integer algorithmType;

    @ApiModelProperty(value = "算法参数")
    private String param;

    /**
     * 算法描述
     */
    @ApiModelProperty(value = "算法描述")
    private String algorithmDesc;

    /**
     * 算法模板名
     */
    @ApiModelProperty(value = "算法模板名")
    private String template;


}
