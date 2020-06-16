package com.cmcc.algo.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class Statistic implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;

    private Short value;

    public Statistic(String name, Short value) {
        this.name = name;
        this.value = value;
    }
}
