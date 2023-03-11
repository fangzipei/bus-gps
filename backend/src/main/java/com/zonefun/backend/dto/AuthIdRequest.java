package com.zonefun.backend.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @ClassName AuthIdRequest
 * @Description 权限ID
 * @Date 2022/10/18 9:59
 * @Author ZoneFang
 */
@Data
public class AuthIdRequest implements Serializable {
    @ApiModelProperty(value = "自增主键，无意义")
    @NotNull(message = "参数不全！")
    private Integer authId;

    private static final long serialVersionUID = 1L;
}