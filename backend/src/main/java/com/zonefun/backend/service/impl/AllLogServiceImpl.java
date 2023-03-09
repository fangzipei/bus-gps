package com.zonefun.backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zonefun.backend.entity.AllLog;
import com.zonefun.backend.mapper.AllLogMapper;
import com.zonefun.backend.service.IAllLogService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 全日志表 服务实现类
 * </p>
 *
 * @author ZoneFun
 * @since 2022-11-24
 */
@Service
public class AllLogServiceImpl extends ServiceImpl<AllLogMapper, AllLog> implements IAllLogService {

}
