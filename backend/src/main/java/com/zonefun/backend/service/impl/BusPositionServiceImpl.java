package com.zonefun.backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zonefun.backend.entity.BusPositionEntity;
import com.zonefun.backend.mapper.BusPositionMapper;
import com.zonefun.backend.service.IBusPositionService;
import org.springframework.stereotype.Service;

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

}
