package com.zonefun.backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 公交车位置
 * </p>
 *
 * @author ZoneFun
 * @since 2023-03-10
 */
@Getter
@Setter
@TableName("bus_position")
@ApiModel(value = "BusPositionEntity对象", description = "公交车位置")
public class BusPositionEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("运行编号")
    private Integer tourId;

    @ApiModelProperty("经度")
    private String longitude;

    @ApiModelProperty("西经还是东经")
    private String longitudeType;

    @ApiModelProperty("纬度")
    private String latitude;

    @ApiModelProperty("南纬还是北纬")
    private String latitudeType;

    @ApiModelProperty("海拔")
    private String height;

    @ApiModelProperty("时间")
    private long time;

    @ApiModelProperty("速度(km/h)")
    private Integer velocity;


}
