package com.zonefun.backend.service.impl;

import cn.hutool.system.SystemUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zonefun.backend.dto.QueryPositionsResponse;
import com.zonefun.backend.entity.BusPositionEntity;
import com.zonefun.backend.entity.BusTourEntity;
import com.zonefun.backend.mapper.BusPositionMapper;
import com.zonefun.backend.mapper.BusTourMapper;
import com.zonefun.backend.service.IBusPositionService;
import com.zonefun.backend.util.PositionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>
 * 公交车位置 服务实现类
 * </p>
 *
 * @author ZoneFun
 * @since 2023-03-10
 */
@Service
public class BusPositionServiceImpl extends ServiceImpl<BusPositionMapper, BusPositionEntity> implements IBusPositionService {

    @Autowired
    private BusTourMapper busTourMapper;

    @Autowired
    private BusPositionMapper busPositionMapper;

    private ConcurrentHashMap<Integer, Long> BUS_MAP = new ConcurrentHashMap<>();

    @Override
    public void savePosition(String longitude, String latitude, int tourId) {
        if (BUS_MAP.containsKey(tourId)) {
            Long latestTime = BUS_MAP.putIfAbsent(tourId, System.currentTimeMillis());
            if (System.currentTimeMillis() - latestTime > 60000) {
                // TODO 更新周期
            }
        }
        BusPositionEntity busPositionEntity = new BusPositionEntity();
        busPositionEntity.setLongitude(longitude);
        busPositionEntity.setLatitude(latitude);
        busPositionEntity.setTime(System.currentTimeMillis());
        this.save(busPositionEntity);
    }

    @Override
    public List<QueryPositionsResponse> queryPositions(String busNo) {
        return busPositionMapper.queryPositions(busNo);
    }
}
