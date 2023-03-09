package com.zonefun.backend.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DelRedisKeyRequest {

    @ApiModelProperty(value = "redis锁key")
    private String key;
}
