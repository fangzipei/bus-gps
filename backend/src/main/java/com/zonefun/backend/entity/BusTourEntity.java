package com.zonefun.backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 公交车运行情况
 * </p>
 *
 * @author ZoneFun
 * @since 2023-03-10
 */
@Getter
@Setter
@TableName("bus_tour")
@ApiModel(value = "BusTourEntity对象", description = "公交车运行情况")
public class BusTourEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("工号")
    private String driverId;

    @ApiModelProperty("公交车号码")
    private String busNo;

    @ApiModelProperty("公交班次")
    private Integer busSchedule;

    @ApiModelProperty("日期")
    private LocalDateTime tourDate;

    @ApiModelProperty("驶向(1-上行,2-下行)")
    private String headingType;

    @ApiModelProperty("现在的站台")
    private String nowStop;

    @ApiModelProperty("下一个站台")
    private String nextStop;

    @ApiModelProperty("是否结束了(1-未结束,2-已结束)")
    private String isEnd;


}
