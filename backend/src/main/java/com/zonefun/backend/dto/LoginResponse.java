package com.zonefun.backend.dto;

import com.zonefun.backend.entity.BusPositionEntity;
import com.zonefun.backend.vo.LocationVO;
import jdk.jfr.Description;
import lombok.Data;

import java.io.Serializable;
import java.rmi.server.UID;
import java.util.List;

/**
 * @ClassName LoginResponse
 * @Description 登陆返回
 * @Date 2023/3/11 10:05
 * @Author ZoneFang
 */
@Data
public class LoginResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    @Description("位置信息")
    private List<BusPositionEntity> locationVOs;

    @Description("当前状态 1-开始开 2-继续开w")
    private String status;

    @Description("旅程编号")
    private Integer tourId;
}
