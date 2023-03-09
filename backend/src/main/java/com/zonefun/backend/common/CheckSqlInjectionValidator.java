package com.zonefun.backend.common;

import org.springframework.util.ObjectUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @ClassName CheckSqlInjectionValidator
 * @Description SQL注入校验
 * @Date 2022/10/21 10:08
 * @Author ZoneFang
 */
public class CheckSqlInjectionValidator implements ConstraintValidator<CheckSqlInjection, Object> {

    // SQL注入字符判断
    private static final String SQL_REG_EXP = ".*(\\b(select|insert|into|update|delete|from|where|and|or" +
            "|drop|execute|like|grant|use|union|order|by)\\b).*";

    /**
     * 初始化
     *
     * @param constraintAnnotation 注解
     * @return void
     * @date 2022/10/21 15:21
     * @author ZoneFang
     */
    @Override
    public void initialize(CheckSqlInjection constraintAnnotation) {
    }

    /**
     * 校验方法
     *
     * @param value   被@CheckSqlInjection注释的字段
     * @param context 校验器上下文
     * @return boolean
     * @date 2022/10/21 10:12
     * @author ZoneFang
     */
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (ObjectUtils.isEmpty(value) || !String.class.equals(value.getClass())) {
            // 如果值为空或不为String类型的字段则不进行校验直接通过
            return true;
        }
        return !((String) value).toLowerCase().matches(SQL_REG_EXP);
    }
}
