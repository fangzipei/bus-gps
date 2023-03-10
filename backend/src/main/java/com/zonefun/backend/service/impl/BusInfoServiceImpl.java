package com.zonefun.backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zonefun.backend.entity.BusInfoEntity;
import com.zonefun.backend.mapper.BusInfoMapper;
import com.zonefun.backend.service.IBusInfoService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 公交车信息 服务实现类
 * </p>
 *
 * @author ZoneFun
 * @since 2023-03-10
 */
@Service
public class BusInfoServiceImpl extends ServiceImpl<BusInfoMapper, BusInfoEntity> implements IBusInfoService {

}
