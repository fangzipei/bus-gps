package com.zonefun.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zonefun.backend.entity.BusInfoEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 公交车信息 Mapper 接口
 * </p>
 *
 * @author ZoneFun
 * @since 2023-03-10
 */
@Mapper
public interface BusInfoMapper extends BaseMapper<BusInfoEntity> {

}
