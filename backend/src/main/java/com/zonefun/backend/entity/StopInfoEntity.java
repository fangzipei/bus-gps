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
 * 站台信息
 * </p>
 *
 * @author ZoneFun
 * @since 2023-03-10
 */
@Getter
@Setter
@TableName("stop_info")
@ApiModel(value = "StopInfoEntity对象", description = "站台信息")
public class StopInfoEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("站台名")
    private String stopName;

    @ApiModelProperty("经度")
    private String longitude;

    @ApiModelProperty("西经还是东经")
    private String longitudeType;

    @ApiModelProperty("纬度")
    private String latitude;

    @ApiModelProperty("南纬还是北纬")
    private String latitudeType;


}
