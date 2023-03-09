package com.zonefun.backend.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 全日志表
 * </p>
 *
 * @author ZoneFun
 * @since 2022-11-24
 */
@Getter
@Setter
@TableName("all_log")
@ApiModel(value = "AllLog对象", description = "全日志表")
public class AllLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("自增主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("请求url")
    private String url;

    @ApiModelProperty("请求类型")
    private String method;

    @ApiModelProperty("请求者ip")
    private String ip;

    @ApiModelProperty("请求描述")
    private String description;

    @ApiModelProperty("请求参数")
    private String parameter;

    @ApiModelProperty("请求返回参数")
    private String result;

    @ApiModelProperty("请求耗时(ms)")
    private long spendTime;

    @ApiModelProperty("请求用户")
    private String createAccount;

    @ApiModelProperty("请求时间")
    private LocalDateTime startTime;

    @ApiModelProperty("创建日期")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;


}
