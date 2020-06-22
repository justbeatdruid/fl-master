package com.cmcc.algo.vo;

import lombok.Data;
//import lombok.EqualsAndHashCode;
//import lombok.experimental.Accessors;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author hjy
 * @since 2020-05-25
 */
//@EqualsAndHashCode(callSuper = false)
//@Accessors(chain = true)
@ApiModel
@Data
public class FederationDatasetVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 数据ID
     */
    @ApiModelProperty(value = "自增id")
    private Short id;

    /**
     * 上传文件路径
     */
    @ApiModelProperty(value = "文件名字")
    private String name;

    /**
     * 文件更新时间
     */
    @ApiModelProperty(value = "文件更新时间")
    private Date updatedAt;

    /**
     * 数据大小
     */
    @ApiModelProperty(value = "数据大小")
    private String size;

    /**
     * 数据行数
     */
    @ApiModelProperty(value = "数据行数")
    private Integer rows;

    /**
     * 数据party
     */
    @ApiModelProperty(value = "partyId")
    private Integer partyId;

    /**
     * 联邦ID
     */
    @ApiModelProperty(value = "联邦ID")
    private String federationUuid;

    /**
     * 数据类别
     */
    @ApiModelProperty(value = "数据类别")
    private Short type;

    /**
     * 是否可编辑
     */
    @ApiModelProperty(value = "是否可编辑")
    private Boolean editable;

}
