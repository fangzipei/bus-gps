package com.zonefun.backend.dto;

import jdk.jfr.Description;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName EndTourRequest
 * @Description 强制终止未完成的行程
 * @Date 2023/3/11 15:05
 * @Author ZoneFang
 */
@Data
public class EndTourRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @Description("线路")
    private String busNo;

    @Description("工号")
    private String driverId;
}
