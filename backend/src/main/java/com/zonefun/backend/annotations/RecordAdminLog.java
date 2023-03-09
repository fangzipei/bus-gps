package com.zonefun.backend.annotations;

import java.lang.annotation.*;

/**
 * @ClassName RecordAdminLog
 * @Description 记录管理员日志
 * @Date 2022/10/20 11:08
 * @Author ZoneFang
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RecordAdminLog {
}
