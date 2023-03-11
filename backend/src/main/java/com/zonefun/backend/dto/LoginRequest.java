package com.zonefun.backend.dto;

import jdk.jfr.Description;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName LoginRequest
 * @Description 登录请求
 * @Date 2023/3/11 10:27
 * @Author ZoneFang
 */
@Data
public class LoginRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @Description("公交号码")
    private String busNo;

    @Description("司机工号")
    private String driverId;

    @Description("当前经度")
    private String longitude;

    @Description("当前纬度")
    private String latitude;
}
