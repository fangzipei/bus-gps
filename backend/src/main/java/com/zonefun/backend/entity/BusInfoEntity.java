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
 * 公交车信息
 * </p>
 *
 * @author ZoneFun
 * @since 2023-03-10
 */
@Getter
@Setter
@TableName("bus_info")
@ApiModel(value = "BusInfoEntity对象", description = "公交车信息")
public class BusInfoEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("公交车号码")
    private String busNo;

    @ApiModelProperty("上行开始站台")
    private String upStart;

    @ApiModelProperty("上行结束站台")
    private String upEnd;

    @ApiModelProperty("下行开始站台")
    private String downStart;

    @ApiModelProperty("下行结束站台")
    private String downEnd;

    @ApiModelProperty("运行时间")
    private String runTime;


}
