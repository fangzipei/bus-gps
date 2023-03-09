package com.zonefun.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zonefun.backend.entity.DictionaryItem;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 字典项表 Mapper 接口
 * </p>
 *
 * @author ZoneFun
 * @since 2022-11-21
 */
@Mapper
public interface DictionaryItemMapper extends BaseMapper<DictionaryItem> {

}
