package com.zonefun.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zonefun.backend.dto.QueryPositionsResponse;
import com.zonefun.backend.entity.BusPositionEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 公交车位置 Mapper 接口
 * </p>
 *
 * @author ZoneFun
 * @since 2023-03-10
 */
@Mapper
public interface BusPositionMapper extends BaseMapper<BusPositionEntity> {

    @Select("<script>" +
            "select * " +
            "from bus_tour bt " +
            "    inner join bus_position bp on bt.id = bp.tour_id " +
            "where bt.is_end = '1'" +
            "<if test=\"busNo != null and busNo == ''\">" +
            "   and bt.bus_no = busNo " +
            "</if>" +
            "</script>")
    List<QueryPositionsResponse> queryPositions(@Param("busNo") String busNo);
}
