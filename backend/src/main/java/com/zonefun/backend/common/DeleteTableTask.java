package com.zonefun.backend.common;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zonefun.backend.entity.BusPositionEntity;
import com.zonefun.backend.entity.BusTourEntity;
import com.zonefun.backend.mapper.BusPositionMapper;
import com.zonefun.backend.mapper.BusTourMapper;
import com.zonefun.backend.service.IBusPositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @ClassName DeleteTableTask
 * @Description 删表定时任务
 * @Date 2023/3/12 17:10
 * @Author ZoneFang
 */
@Component
public class DeleteTableTask {

    @Autowired
    private BusPositionMapper busPositionMapper;

    @Autowired
    private BusTourMapper busTourMapper;

    // 0 30 9 1 * ?
    @Scheduled(cron = "0 30 9 1 * ?")
    public void deleteBusPosition(){
        QueryWrapper<BusPositionEntity> qw = new QueryWrapper<>();
        qw.eq("1",1);
        busPositionMapper.delete(qw);
        QueryWrapper<BusTourEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("1",1);
        busTourMapper.delete(queryWrapper);
    }
}
