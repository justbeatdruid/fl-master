package com.cmcc.algo.entity;

import lombok.Data;
//import lombok.EqualsAndHashCode;
//import lombok.experimental.Accessors;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

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
@Data
@Entity
@Table(name = "tb_dataset")
public class Dataset implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 数据ID
     */
    @Id
    @GeneratedValue
    private Short id;

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
     * 数据party
     */
    private String partyId;
}
