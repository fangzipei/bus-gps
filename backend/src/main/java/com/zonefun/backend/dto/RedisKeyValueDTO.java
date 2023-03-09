package com.zonefun.backend.dto;

import jdk.jfr.Description;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName RedisKeyValueDTO
 * @Description redis键值对
 * @Date 2022/7/27 8:45
 * @Author ZoneFang
 */
@Data
@AllArgsConstructor
public class RedisKeyValueDTO implements Serializable {
    @Description("键")
    private String key;

    @Description("值")
    private String value;
}
