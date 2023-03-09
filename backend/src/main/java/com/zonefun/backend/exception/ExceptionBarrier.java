package com.zonefun.backend.exception;

import com.zonefun.backend.common.CommonResult;
import com.zonefun.backend.enums.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author: ZoneFang
 * @Date: 2021/3/31 下午1:46
 */
@RestControllerAdvice
@Slf4j
public class ExceptionBarrier {
    @ExceptionHandler(value = CommonException.class)
    public CommonResult commonExceptionHandler(CommonException e) {
        log.error(e.toString(), e);
        return new CommonResult(e.getCode(), e.getMessage(), null);
    }

    /**
     * 异常捕获 @Validated注解抛出的异常为ConstraintViolationException
     *
     * @param e 报错
     * @return com.glsc.dscp.common.CommonResult
     * @date 2022/10/20 17:05
     * @author ZoneFang
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    public CommonResult validExceptionHandler(ConstraintViolationException e) {
        log.error("数据校验出现问题，异常信息:{}", e.getMessage());
        // 获取具体报错字段和信息
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        String message = violations.stream().map(violation ->
                ((PathImpl) violation.getPropertyPath()).getLeafNode().getName() + ":" + violation.getMessage()
        ).collect(Collectors.joining(";"));
        return new CommonResult(ResultCode.VALIDATE_FAILED.getCode(), "参数出错", e.getMessage());
    }

    /**
     * 异常捕获 @RequestBody上使用@Valid 实体上使用@NotNull等，验证失败后抛出的异常是MethodArgumentNotValidException异常
     *
     * @param e 报错
     * @return com.glsc.dscp.common.CommonResult
     * @date 2022/10/21 15:35
     * @author ZoneFang
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CommonResult MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        log.error("数据校验出现问题，异常信息:{}", e.getMessage());
        String message = e.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining());
        return new CommonResult(ResultCode.VALIDATE_FAILED.getCode(), "参数出错", message);
    }

    /**
     * 处理Get请求中 使用@Valid 验证路径中请求实体校验失败后抛出的异常
     *
     * @param e 报错
     * @return com.glsc.dscp.common.CommonResult
     * @date 2022/10/21 15:36
     * @author ZoneFang
     */
    @ExceptionHandler(BindException.class)
    public CommonResult BindExceptionHandler(BindException e) {
        String message = e.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining());
        return new CommonResult(ResultCode.VALIDATE_FAILED.getCode(), "参数出错", message);
    }

    /**
     * 参数格式异常
     *
     * @param e 报错
     * @return com.glsc.dscp.common.CommonResult
     * @date 2022/10/21 15:37
     * @author ZoneFang
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public CommonResult HttpMessageNotReadableExceptionHandler(HttpMessageNotReadableException e) {
        return new CommonResult(ResultCode.VALIDATE_FAILED.getCode(), "参数出错", e.getMessage());
    }
}
