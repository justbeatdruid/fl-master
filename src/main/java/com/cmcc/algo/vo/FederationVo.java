package com.cmcc.algo.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;
/**
 * <p>
 * 联邦信息类
 * </p>
 *
 * @author hjy
 * @since 2020-05-25
 */
@Data
public class FederationVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 联邦名称
     */
    private String name;

    /**
     *联邦唯一ID
     */
    private String uuid;

    /**
     * 联邦创建时间
     */
    private Date createdAt;

    /**
     * 联邦类型(0:横向联邦，1:纵向联邦)
     */
    private Boolean type;

    /**
     * 联邦描述
     */
    private String description;

    /**
     * 联邦所有者
     */
    private String guest;

    /**
     * 联邦参与者列表
     */
    private String[] hosts;

    /**
     * 联邦状态(0:等待，1:就绪，2:运行中，3:成功，4:失败)
     */
    private Integer status;

    /**
     * 联邦显示状态(0:等待，1:就绪，2:运行中，3:成功，4:失败)
     */
    private String displayStatus;

    /**
     * 数据格式
     */
    private DataFormatVo dataFormat;

    /**
     * 算法ID
     */
    private Integer algorithmId;

    /**
     * 联邦参数（同训练参数）
     */
    private Map<String, Double> param;

    /**
     * 联邦创建者名字
     */
    private String guestName;

    /**
     * 联邦成员数
     */
    private Short userCount;

    /**
     * 当前用户在联邦中的角色
     */
    private String role;

    /**
     * 当前用户是否可加入联邦
     */
    private Boolean enterable;

    /**
     * 
     */
    //@ApiModelProperty(value = "训练数据就绪")
    private Boolean trainDatasetPrepared;

    /**
     * 
     */
    //@ApiModelProperty(value = "预测数据就绪")
    private Boolean predictDatasetPrepared;
}
