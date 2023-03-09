package com.zonefun.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zonefun.backend.entity.DictionaryCatalog;
import com.zonefun.backend.vo.DictionaryExtend;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 字典目录表 Mapper 接口
 * </p>
 *
 * @author ZoneFun
 * @since 2022-11-21
 */
@Mapper
public interface DictionaryCatalogMapper extends BaseMapper<DictionaryCatalog> {

    /**
     * 通过字典英文名获取字典项列表
     *
     * @param dictEnglishName 字典英文名
     * @return java.util.List<com.zonefun.algorithm.vo.DictionaryExtend>
     * @date 2022/11/24 11:25
     * @author ZoneFang
     */
    @Select("select " +
            "   * " +
            "from " +
            "   dictionary_catalog dc " +
            "inner join dictionary_item di on " +
            "   dc.dict_no = di.dict_no " +
            "where " +
            "   dc.dict_english_name = #{dictEnglishName}")
    List<DictionaryExtend> listItemsByDictEnglishName(@Param("dictEnglishName") String dictEnglishName);
}
