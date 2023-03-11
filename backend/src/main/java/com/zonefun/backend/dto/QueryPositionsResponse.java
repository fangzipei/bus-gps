package com.zonefun.backend.dto;

import com.zonefun.backend.entity.BusPositionEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName QueryPositionsRequest
 * @Description 查询位置的反参
 * @Date 2023/3/11 19:47
 * @Author ZoneFang
 */
@Data
public class QueryPositionsResponse extends BusPositionEntity {
    @ApiModelProperty("工号")
    private String driverId;

    @ApiModelProperty("公交车号码")
    private String busNo;

    @ApiModelProperty("公交班次")
    private Integer busSchedule;

    @ApiModelProperty("日期")
    private long tourDate;

    @ApiModelProperty("驶向(1-上行,2-下行)")
    private String headingType;

    @ApiModelProperty("现在的站台")
    private String nowStop;

    @ApiModelProperty("下一个站台")
    private String nextStop;
}
