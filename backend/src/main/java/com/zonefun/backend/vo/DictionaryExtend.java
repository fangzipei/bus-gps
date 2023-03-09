package com.zonefun.backend.vo;

import com.zonefun.backend.entity.DictionaryCatalog;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName DictionaryExtend
 * @Description 字典类扩展字段
 * @Date 2022/8/22 10:18
 * @Author ZoneFang
 */
@Data
public class DictionaryExtend extends DictionaryCatalog {

    @ApiModelProperty("字典项值")
    private String itemValue;

    @ApiModelProperty("字典项描述")
    private String itemDescription;

    @ApiModelProperty("字典项英文名")
    private String itemEnglishName;

}
