package com.zonefun.backend.vo;

import jdk.jfr.Description;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName ExcelCreateSqlRow
 * @Description excel生成sql的行对象
 * @Date 2022/8/23 14:38
 * @Author ZoneFang
 */
@Data
public class ExcelCreateSqlRow implements Serializable {
    private static final long serialVersionUID = 1L;

    @Description("表名")
    private String tableName;

    @Description("是否主键 是/否")
    private String primaryKey;

    @Description("字段名")
    private String columnName;

    @Description("字段描述")
    private String columnDescription;

    @Description("数据类型")
    private String dataType;

    @Description("长度")
    private String length;

    @Description("小数位数")
    private String scale;

    @Description("可空")
    private String isBlank;

    @Description("是否必填")
    private String isNeed;

    @Description("缺省值")
    private String defaultValue;

    @Description("唯一约束")
    private String uniqueKey;
}
