package com.zonefun.backend.dto;

import jdk.jfr.Description;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName SavePositionResponse
 * @Description 保存地址反惨
 * @Date 2023/3/12 18:20
 * @Author ZoneFang
 */
@Data
@AllArgsConstructor
public class SavePositionResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    @Description("当前行程是否结束了 1-未结束 2-已结束")
    private String isEnd;

    @Description("速度")
    private double velocity;
}
