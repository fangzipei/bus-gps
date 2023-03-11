package com.zonefun.backend.dto;

import jdk.jfr.Description;

import java.io.Serializable;

/**
 * @ClassName SavePositionRequest
 * @Description 保存地点
 * @Date 2023/3/11 15:26
 * @Author ZoneFang
 */
public class SavePositionRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @Description("经度")
    private String longitude;

    @Description("维度")
    private String latitude;

    @Description("路线编号")
    private int tourId;
}
