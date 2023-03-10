package com.zonefun.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zonefun.backend.entity.BusTourEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 公交车运行情况 Mapper 接口
 * </p>
 *
 * @author ZoneFun
 * @since 2023-03-10
 */
@Mapper
public interface BusTourMapper extends BaseMapper<BusTourEntity> {

}
