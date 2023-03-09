package com.zonefun.algorithm.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName AuthRequest
 * @Description 权限
 * @Date 2022/10/18 9:59
 * @Author ZoneFang
 */
@Data
public class AuthRequest implements Serializable {
    @ApiModelProperty(value = "自增主键，无意义")
    private Integer authId;

    @ApiModelProperty(value = "权限名称")
    @NotEmpty(message = "参数不全！")
    private String authName;

    @ApiModelProperty(value = "权限url")
    @NotEmpty(message = "参数不全！")
    private String authUrl;

    @ApiModelProperty(value = "删除状态（0=未删除(默认) 1=已删除)")
    private String isDeleted;

    @ApiModelProperty(value = "创建日期")
    private Date createTime;

    @ApiModelProperty(value = "更新日期")
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}