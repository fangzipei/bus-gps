package com.zonefun.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zonefun.backend.dto.LoginRequest;
import com.zonefun.backend.dto.LoginResponse;
import com.zonefun.backend.entity.*;
import com.zonefun.backend.exception.CommonException;
import com.zonefun.backend.mapper.*;
import com.zonefun.backend.service.IBusTourService;
import com.zonefun.backend.util.DateUtil;
import com.zonefun.backend.util.PositionUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.velocity.runtime.directive.Stop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 公交车运行情况 服务实现类
 * </p>
 *
 * @author ZoneFun
 * @since 2023-03-10
 */
@Service
@Slf4j
public class BusTourServiceImpl extends ServiceImpl<BusTourMapper, BusTourEntity> implements IBusTourService {

    @Autowired
    private BusInfoMapper busInfoMapper;

    @Autowired
    private BusTourMapper busTourMapper;

    @Autowired
    private StopInfoMapper stopInfoMapper;

    @Autowired
    private BusPositionMapper busPositionMapper;

    @Autowired
    private BusStopMapper busStopMapper;

    @Override
    @Transactional
    public LoginResponse login(String busNo, String driverId, String longitude, String latitude) {
        LoginResponse res = new LoginResponse();
        QueryWrapper<BusInfoEntity> busInfoQW = new QueryWrapper<>();
        busInfoQW.eq("bus_no", busNo);
        List<BusInfoEntity> busInfoEntities = busInfoMapper.selectList(busInfoQW);
        if (ObjectUtils.isEmpty(busInfoEntities)) {
            throw new CommonException("不存在该线路，请重新选择！");
        }
        BusInfoEntity busInfoEntity = busInfoEntities.get(0);
        QueryWrapper<BusTourEntity> busTourQW = new QueryWrapper<>();
        busTourQW.eq("driver_id", driverId).eq("is_end", "1");
        List<BusTourEntity> busTourEntities = busTourMapper.selectList(busTourQW);
        if (ObjectUtils.isEmpty(busTourEntities)) {
            // 空表示可以新开个tour
            String headingType = "";
            String nowStop = "";
            res.setStatus("1");
            // 判断经纬度
            String upStartStop = busInfoEntity.getUpStart();
            String downStartStop = busInfoEntity.getDownStart();
            QueryWrapper<StopInfoEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.in("stop_name", upStartStop, downStartStop);
            List<StopInfoEntity> stopInfoEntities = stopInfoMapper.selectList(queryWrapper);
            Map<String, String[]> stopMap = stopInfoEntities.stream()
                    .collect(Collectors.toMap(StopInfoEntity::getStopName, stop -> new String[]{stop.getLongitude(), stop.getLatitude()}));
            double upStopDis = PositionUtil.getDistance(longitude, latitude, stopMap.get(upStartStop)[0], stopMap.get(upStartStop)[1]);
            log.info("工号{}的用户，离{}公交的上行起始站距离{}", driverId, busNo, upStopDis);
            double downStopDis = PositionUtil.getDistance(longitude, latitude, stopMap.get(downStartStop)[0], stopMap.get(downStartStop)[1]);
            log.info("工号{}的用户，离{}公交的下行起始站距离{}", driverId, busNo, downStopDis);
            if (upStopDis < 1000 && upStopDis < downStopDis) {
                headingType = "1";
                nowStop = upStartStop;
            } else if (downStopDis < 1000 && downStopDis < upStopDis) {
                headingType = "2";
                nowStop = downStartStop;
            } else {
                throw new CommonException("未到指定地点，无法开始行程！");
            }

            BusStopEntity nextStop = busStopMapper.getNextStop(busNo, nowStop, headingType);

            long now = DateUtil.getCurrDate();
            busTourQW.clear();
            busTourQW.eq("bus_no", busNo)
                    .eq("is_end", "2").eq("tour_date", now)
                    .orderByDesc("bus_schedule");
            List<BusTourEntity> latestBusTour = busTourMapper.selectList(busTourQW);
            BusTourEntity busTourEntity = new BusTourEntity();
            busTourEntity.setBusNo(busNo);
            busTourEntity.setDriverId(driverId);
            busTourEntity.setHeadingType(headingType);
            busTourEntity.setIsEnd("1");
            busTourEntity.setTourDate(now);
            busTourEntity.setNowStop(nowStop);
            if (ObjectUtils.isEmpty(latestBusTour)) {
                busTourEntity.setBusSchedule(1);
            } else {
                busTourEntity.setBusSchedule(latestBusTour.get(0).getBusSchedule() + 1);
            }
            if (!ObjectUtils.isEmpty(nextStop)) {
                busTourEntity.setNextStop(nextStop.getStopName());
            } else {
                busTourEntity.setNextStop("");
            }
            this.save(busTourEntity);
            res.setTourId(busTourEntity.getId());
        } else {
            // 继续开 把之前的路径都发过去
            QueryWrapper<BusPositionEntity> busPosQW = new QueryWrapper<>();
            busPosQW.eq("tour_id", busTourEntities.get(0).getId()).orderByAsc("time");
            List<BusPositionEntity> busPositionEntities = busPositionMapper.selectList(busPosQW);
            res.setStatus("2");
            res.setLocationVOs(busPositionEntities);
            res.setTourId(busTourEntities.get(0).getId());
        }
        return res;
    }

    @Override
    @Transactional
    public void endTour(String busNo, String driverId) {
        LoginResponse res = new LoginResponse();
        UpdateWrapper<BusTourEntity> uw = new UpdateWrapper<>();
        uw.set("is_end", "2")
                .eq("bus_no", busNo)
                .eq("driver_id", driverId)
                .eq("is_end", "1");
        busTourMapper.update(null, uw);
    }
}
