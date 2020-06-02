package com.cmcc.algo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cmcc.algo.common.validator.group.AddGroup;
import com.cmcc.algo.common.validator.group.UpdateGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
/**
 * <p>
 * 联邦信息表
 * </p>
 *
 * @author hjy
 * @since 2020-05-25
 */
//@EqualsAndHashCode(callSuper = false)
//@Accessors(chain = true)
@Data
@TableName("tb_federation")
public class FederationEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 联邦ID
     */
    @TableId
    private Long id;

    /**
     * 联邦名称
     */
    @NotBlank(message="名字不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String name;

    /**
     *联邦唯一ID
     */
    @NotBlank(message="uuid不能为空")
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
    private String hosts;

    /**
     * 联邦状态(0:等待，1:就绪，2:运行中，3:成功，4:失败)
     */
    private Boolean status;

    /**
     * 数据格式
     */
    private String dataFormat;

    /**
     * 算法ID
     */
    private Integer algorithmId;

    /**
     * 联邦参数（同训练参数）
     */
    private String param;


}
