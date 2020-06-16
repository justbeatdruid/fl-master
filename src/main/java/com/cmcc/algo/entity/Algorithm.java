package com.cmcc.algo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
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
public class Algorithm implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 算法ID
     */
    private Integer id;

    /**
     * 算法名
     */
    private String name;

    private String displayName;

    private Integer federationType;

    private Integer algorithmType;

    private String param;

    /**
     * 算法描述
     */
    private String desc;

    /**
     * 算法模板名
     */
    private String template;


}
