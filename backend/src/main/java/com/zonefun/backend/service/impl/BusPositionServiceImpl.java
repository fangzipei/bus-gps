package com.zonefun.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zonefun.backend.dto.QueryPositionsResponse;
import com.zonefun.backend.dto.SavePositionResponse;
import com.zonefun.backend.entity.BusPositionEntity;
import com.zonefun.backend.entity.BusStopEntity;
import com.zonefun.backend.entity.BusTourEntity;
import com.zonefun.backend.entity.StopInfoEntity;
import com.zonefun.backend.mapper.BusPositionMapper;
import com.zonefun.backend.mapper.BusStopMapper;
import com.zonefun.backend.mapper.BusTourMapper;
import com.zonefun.backend.mapper.StopInfoMapper;
import com.zonefun.backend.service.IBusPositionService;
import com.zonefun.backend.util.PositionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
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

    @Autowired
    private BusStopMapper busStopMapper;

    @Autowired
    private StopInfoMapper stopInfoMapper;

    private ConcurrentHashMap<Integer, Long> BUS_MAP = new ConcurrentHashMap<>();

    @Override
    public SavePositionResponse savePosition(String longitude, String latitude, int tourId, double velocity) {
        String isEnd = "1";
        long now = System.currentTimeMillis();
        if (!BUS_MAP.containsKey(tourId)) {
            BUS_MAP.put(tourId, now);
        }

        Long latestTime = BUS_MAP.get(tourId);
        if (now - latestTime > 60000) {
            // 更新周期 如果和上次记录的时间大于一分钟了则看是否需要更新站点信息等
            BusTourEntity busTourEntity = busTourMapper.selectById(tourId);
            if ("2".equals(busTourEntity.getIsEnd())) {
                isEnd = "2";
                return new SavePositionResponse(isEnd, velocity);
            }
            BUS_MAP.put(tourId, now);
            QueryWrapper<StopInfoEntity> stopInfoQW = new QueryWrapper<>();
            stopInfoQW.eq("stop_name", busTourEntity.getNextStop());
            List<StopInfoEntity> stopInfoEntities = stopInfoMapper.selectList(stopInfoQW);
            // 更新下一站信息 并判断是否结束
            if (!ObjectUtils.isEmpty(stopInfoEntities)) {
                StopInfoEntity stopInfoEntity = stopInfoEntities.get(0);
                double distance = PositionUtil.getDistance(stopInfoEntity.getLongitude(), stopInfoEntity.getLatitude(),
                        longitude, latitude);
                if (distance < 100) {
                    busTourEntity.setNowStop(busTourEntity.getNextStop());
                    BusStopEntity nextStop = busStopMapper.getNextStop(busTourEntity.getBusNo(), busTourEntity.getNowStop(), busTourEntity.getHeadingType());
                    if (!ObjectUtils.isEmpty(nextStop)) {
                        busTourEntity.setNextStop(nextStop.getStopName());
                    } else {
                        isEnd = "2";
                        busTourEntity.setIsEnd(isEnd);
                        BUS_MAP.remove(tourId);
                    }
                }
            }
            BusPositionEntity latestPosition = busPositionMapper.getLatestPosition(tourId);
            if (!ObjectUtils.isEmpty(latestPosition)) {
                double dis = PositionUtil.getDistance(longitude, latitude, latestPosition.getLongitude(), latestPosition.getLatitude());
                long time = now - latestPosition.getTime();
                velocity = (dis / time) * 60 * 60;
            }
        }

        BusPositionEntity busPositionEntity = new BusPositionEntity();
        busPositionEntity.setLongitude(longitude);
        busPositionEntity.setLatitude(latitude);
        busPositionEntity.setTime(System.currentTimeMillis());
        busPositionEntity.setVelocity(velocity);
        this.save(busPositionEntity);
        return new SavePositionResponse(isEnd, velocity);
    }

    @Override
    public List<QueryPositionsResponse> queryPositions(String busNo) {
        return busPositionMapper.queryPositions(busNo);
    }
}
