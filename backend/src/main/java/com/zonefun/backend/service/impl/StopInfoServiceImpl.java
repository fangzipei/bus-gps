package com.zonefun.backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zonefun.backend.entity.StopInfoEntity;
import com.zonefun.backend.mapper.StopInfoMapper;
import com.zonefun.backend.service.IStopInfoService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 站台信息 服务实现类
 * </p>
 *
 * @author ZoneFun
 * @since 2023-03-10
 */
@Service
public class StopInfoServiceImpl extends ServiceImpl<StopInfoMapper, StopInfoEntity> implements IStopInfoService {

}
