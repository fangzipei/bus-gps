package com.zonefun.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zonefun.backend.entity.BusInfoEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

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

    @Select("select distinct bi.* " +
            "from bus_info bi " +
            "where bi.bus_no like concat('%', ${busNo}, '%') " +
            "   or bi.bus_no in (select distinct bus_no from bus_stop bs where stop_name like concat('%', ${stopName}, '%'))")
    List<BusInfoEntity> queryByBusNoOrStopName(@Param("busNo") String busNo, @Param("stopName") String stopName);
}
