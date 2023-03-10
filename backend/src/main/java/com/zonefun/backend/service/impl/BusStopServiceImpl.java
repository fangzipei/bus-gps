package com.zonefun.backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zonefun.backend.entity.BusStopEntity;
import com.zonefun.backend.mapper.BusStopMapper;
import com.zonefun.backend.service.IBusStopService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 公交站台信息 服务实现类
 * </p>
 *
 * @author ZoneFun
 * @since 2023-03-10
 */
@Service
public class BusStopServiceImpl extends ServiceImpl<BusStopMapper, BusStopEntity> implements IBusStopService {

}
