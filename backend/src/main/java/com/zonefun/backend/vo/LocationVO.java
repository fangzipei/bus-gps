package com.zonefun.backend.vo;

import jdk.jfr.Description;

import java.io.Serializable;

/**
 * @ClassName LocationVO
 * @Description 位置信息
 * @Date 2023/3/11 10:07
 * @Author ZoneFang
 */
public class LocationVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Description("经度")
    private String longitude;

    @Description("纬度")
    private String latitude;
}
