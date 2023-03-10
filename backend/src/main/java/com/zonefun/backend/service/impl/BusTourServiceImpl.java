package com.zonefun.backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zonefun.backend.entity.BusTourEntity;
import com.zonefun.backend.mapper.BusTourMapper;
import com.zonefun.backend.service.IBusTourService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 公交车运行情况 服务实现类
 * </p>
 *
 * @author ZoneFun
 * @since 2023-03-10
 */
@Service
public class BusTourServiceImpl extends ServiceImpl<BusTourMapper, BusTourEntity> implements IBusTourService {

}
