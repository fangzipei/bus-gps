package com.zonefun.backend.annotations;

import jdk.jfr.Description;

import java.lang.annotation.*;

/**
 * @ClassName ExcelField
 * @Description 需要转换为Excel列的字段
 * @Date 2022/9/9 10:13
 * @Author ZoneFang
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExcelField {
    @Description("字段名")
    String value();

    @Description("转换用的enum类")
    Class enumClass() default Object.class;

    @Description("导出Excel列的顺序")
    int sort() default 0;

    @Description("自定义转义规则，字典值和对应解释中间用“-”分割，字典项之间用“;”分割，如 1-是;2-否。慎用！优先级低于enumClass！")
    String enumValue() default "";

    @Description("是否必输，默认非必输，只有在读取的时候才会使用")
    boolean required() default false;
}
