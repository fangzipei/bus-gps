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
 * 公交站台信息
 * </p>
 *
 * @author ZoneFun
 * @since 2023-03-10
 */
@Getter
@Setter
@TableName("bus_stop")
@ApiModel(value = "BusStopEntity对象", description = "公交站台信息")
public class BusStopEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("公交车号码")
    private String busNo;

    @ApiModelProperty("站台顺序")
    private Integer sequence;

    @ApiModelProperty("站台名")
    private String stopName;

    @ApiModelProperty("驶向(1-上行,2-下行)")
    private String headingType;


}
