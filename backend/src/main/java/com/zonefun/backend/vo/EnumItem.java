package com.zonefun.backend.vo;

import jdk.jfr.Description;
import lombok.Data;

/**
 * @ClassName EnumItem
 * @Description 枚举类的组成部分
 * @Date 2022/8/22 17:58
 * @Author ZoneFang
 */
@Data
public class EnumItem {
    @Description("枚举名称")
    private String enumItemName;

    @Description("枚举值")
    private String enumItemValue;

    @Description("枚举描述")
    private String enumItemDesc;

    @Description("枚举类型 0-字符 1-数字 2-字符串")
    private String dataType;
}
