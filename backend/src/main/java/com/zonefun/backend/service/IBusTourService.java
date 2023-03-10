package com.zonefun.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zonefun.backend.dto.LoginResponse;
import com.zonefun.backend.entity.BusTourEntity;

/**
 * <p>
 * 公交车运行情况 服务类
 * </p>
 *
 * @author ZoneFun
 * @since 2023-03-10
 */
public interface IBusTourService extends IService<BusTourEntity> {

    LoginResponse login(String busNo, String driverId, String longitude, String latitude);

    void endTour(String busNo, String driverId);
}
