package com.zonefun.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zonefun.backend.entity.BusStopEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 公交站台信息 Mapper 接口
 * </p>
 *
 * @author ZoneFun
 * @since 2023-03-10
 */
@Mapper
public interface BusStopMapper extends BaseMapper<BusStopEntity> {

    @Select("select * from bus_stop where bus_no = #{busNo} and heading_type = #{headingType} " +
            "and sequence = (" +
            "select sequence from bus_stop where bus_no = #{busNo} and heading_type = #{headingType} " +
            "and stop_name = #{nowStop})")
    BusStopEntity getNextStop(@Param("busNo") String busNo, @Param("nowStop") String nowStop,
                              @Param("headingType") String headingType);
}
