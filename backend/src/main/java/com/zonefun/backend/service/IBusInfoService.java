package com.zonefun.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zonefun.backend.entity.BusInfoEntity;

import java.util.List;

/**
 * <p>
 * 公交车信息 服务类
 * </p>
 *
 * @author ZoneFun
 * @since 2023-03-10
 */
public interface IBusInfoService extends IService<BusInfoEntity> {

    List<BusInfoEntity> getBusInfos();

    List<BusInfoEntity> clientQuery(String busNo, String stopName);
}
