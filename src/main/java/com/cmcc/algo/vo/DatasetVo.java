package com.cmcc.algo.vo;

import lombok.Data;

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
public class DatasetVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Short Id;
    /**
     * 上传文件路径
     */
    private String name;

    /**
     * 文件更新时间
     */
    private Date updatedAt;

    /**
     * 数据大小
     */
    private String size;

    /**
     * 数据行数
     */
    private Integer rows;
}
