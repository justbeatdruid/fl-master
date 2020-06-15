package com.cmcc.algo.dto;

import lombok.Data;

import java.io.Serializable;
/**
 * <p>
 * 联邦信息类
 * </p>
 *
 * @author hjy
 * @since 2020-05-25
 */
@Data
public class DataFormat implements Serializable {

    private static final long serialVersionUID = 1L;

    private String identity;

    private String label;

    private String feature;
}
