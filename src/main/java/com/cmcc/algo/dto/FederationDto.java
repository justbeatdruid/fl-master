package com.cmcc.algo.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

import java.io.Serializable;
import java.util.Date;
/**
 * <p>
 * 联邦信息类
 * </p>
 *
 * @author hjy
 * @since 2020-05-25
 */
@Data
public class FederationDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private static int[] typeRange = {0,1};

    /**
     * 联邦名称
     */
    @NotBlank(message="名字不能为空")
    private String name;

    /**
     * 联邦类型(0:横向联邦，1:纵向联邦)
     */
    private Boolean type;

    /**
     * 联邦描述
     */
    private String description;

    /**
     * 数据格式
     */
    private DataFormatDto dataFormat;

    /**
     * 算法ID
     */
    private Integer algorithmId;

    /**
     * 联邦参数（同训练参数）
     */
    private String param;


}
