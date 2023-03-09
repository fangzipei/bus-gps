package com.zonefun.backend.annotations;

import java.lang.annotation.*;

/**
 * @ClassName SkipSaveLog
 * @Description 跳过日志保存 优先级比管理员日志高
 * @Date 2022/10/20 11:08
 * @Author ZoneFang
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SkipSaveLog {
}
