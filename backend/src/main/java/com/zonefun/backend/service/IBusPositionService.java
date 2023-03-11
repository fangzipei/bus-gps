package com.zonefun.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zonefun.backend.dto.QueryPositionsResponse;
import com.zonefun.backend.entity.BusPositionEntity;

import java.util.List;

/**
 * <p>
 * 公交车位置 服务类
 * </p>
 *
 * @author ZoneFun
 * @since 2023-03-10
 */
public interface IBusPositionService extends IService<BusPositionEntity> {

    void savePosition(String longitude, String latitude, int tourId);

    List<QueryPositionsResponse> queryPositions(String busNo);
}
