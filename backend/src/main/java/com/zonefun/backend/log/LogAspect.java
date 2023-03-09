package com.zonefun.backend.log;

import com.zonefun.backend.annotations.SkipSaveLog;
import com.zonefun.backend.entity.AllLog;
import com.zonefun.backend.filter.RequestWrapper;
import com.zonefun.backend.mapper.AllLogMapper;
import io.swagger.annotations.ApiOperation;
import jdk.jfr.Description;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @ClassName LogAspect
 * @Description 日志切面
 * @Date 2022/11/15 10:32
 * @Author ZoneFang
 */
@Aspect
@Slf4j
public class LogAspect {

    @Description("需要特殊注意的url")
    private static final List<String> attentionUrl = List.of();

    @Autowired
    private AllLogMapper allLogMapper;

    @Pointcut("execution(* com.zonefun.algorithm.controller.*.*(..))")
    public void log() {
    }

    /**
     * 日志打印
     *
     * @param point 切点
     * @return java.lang.Object
     * @date 2022/11/21 15:08
     * @author ZoneFang
     */
    @Around("log()")
    public Object printLog(ProceedingJoinPoint point) throws Throwable {
        // 记录开始时间
        LocalDateTime start = LocalDateTime.now();
        // 获取当前请求入参
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        RequestWrapper request = (RequestWrapper) attributes.getRequest();

        String requestId = RandomStringUtils.randomNumeric(10);
        log.info("request -> id: {}, ip: {}, url: {}, param: {}", requestId, request.getRemoteAddr(), request.getRequestURL(), request.getBody());
        Object ret = null;
        long cost = 0L;
        try {
            ret = point.proceed();
            Duration between = Duration.between(start, LocalDateTime.now());
            cost = between.toMillis();
        } finally {
            // 保存日志
            saveLog(request, point, cost, requestId, start, ret);
        }
        log.info("response -> id: {}, url:{}, cost: {}ms", requestId, request.getRequestURL(), cost);
        return ret;
    }


    private void saveLog(RequestWrapper request, ProceedingJoinPoint point, long cost, String requestId, LocalDateTime start, Object ret) {
        try {
            // 1.获取用户行为日志
            // 获取类的字节码对象，通过字节码对象获取方法信息
            Class<?> targetCls = point.getTarget().getClass();
            // 获取方法签名(通过此签名获取目标方法信息)
            MethodSignature ms = (MethodSignature) point.getSignature();
            // 获取目标方法上的注解指定的操作名称
            Method targetMethod =
                    targetCls.getDeclaredMethod(
                            ms.getName(),
                            ms.getParameterTypes());
            // 判断该日志是否需要入库 有SkipSaveLog注解的话就不需要入库
            SkipSaveLog skipSaveLog = targetMethod.getAnnotation(SkipSaveLog.class);
            if (!ObjectUtils.isEmpty(skipSaveLog)) {
                return;
            }
            // 获取方法上的注释作为描述存入log
            String description = "";
            ApiOperation apiOperation =
                    targetMethod.getAnnotation(ApiOperation.class);
            // 描述中加上requestId方便后续查询日志
            if (ObjectUtils.isEmpty(apiOperation)) {
                description = targetMethod.getName() + ":" + requestId;
            } else {
                description = apiOperation.value() + ":" + requestId;
            }
            log.info("targetMethod:{}", targetCls.getName() + targetMethod.getName());
            // 获取请求参数
            String params = request.getBody();
            // 2.封装用户行为日志
            AllLog allLog = new AllLog();
            allLog.setIp(request.getRemoteIP());
            allLog.setDescription(description);
            allLog.setCreateTime(LocalDateTime.now());
            allLog.setMethod(request.getMethod());
            allLog.setCreateAccount("");
            allLog.setParameter(params);
            allLog.setStartTime(start);
            // 判断请求url是否是需要关注的 如果是需要关注的则不存result因为过长
            if (!ObjectUtils.isEmpty(ret)) {
                if (!isAttentionUrl(request.getServletPath())) {
                    allLog.setResult(ret.toString());
                } else {
                    allLog.setResult(String.format("requestId:%s，请求成功但反参过长，具体信息可以查看日志！", requestId));
                }
            } else {
                allLog.setResult(String.format("requestId:%s，请求失败，具体信息请查看日志！", requestId));
            }
            allLog.setSpendTime(cost);
            allLog.setUrl(request.getRequestURL().toString());
            // 3.保存log
            allLogMapper.insert(allLog);

//            // 4.判断当前方法需不需要管理员日志
//            RecordAdminLog methodRecordAdminLog = targetMethod.getAnnotation(RecordAdminLog.class);
//            RecordAdminLog classRecordAdminLog = targetMethod.getDeclaringClass().getAnnotation(RecordAdminLog.class);
//            // 5. 需要管理员日志则新增
//            if (!ObjectUtils.isEmpty(methodRecordAdminLog) || !ObjectUtils.isEmpty(classRecordAdminLog)) {
//                AdminLog adminLog = new AdminLog();
//                BeanUtils.copyProperties(allLog, adminLog);
//                adminLogMapper.insert(adminLog);
//            }
        } catch (Exception e) {
            log.info("请求无法成功记录日志。requestId：{}", requestId);
            e.printStackTrace();
        }
    }

    /**
     * 判断url是否是需要关注的url
     *
     * @param url url
     * @return boolean
     * @date 2022/11/1 15:20
     * @author ZoneFang
     */
    private boolean isAttentionUrl(String url) {
        return attentionUrl.contains(url);
    }
}
