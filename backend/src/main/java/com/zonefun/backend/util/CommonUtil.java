package com.zonefun.backend.util;

import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName CommonUtil
 * @Description 常用工具
 * @Date 2022/10/19 18:14
 * @Author ZoneFang
 */
public class CommonUtil {

//    /**
//     * 获取当前线程中Http请求的token对应的账号
//     *
//     * @return java.lang.String
//     * @date 2022/10/19 18:17
//     * @author ZoneFang
//     */
//    public static String getAccount() {
//        String account = "";
//        // 获取当前线程中Http请求的token，后解析成账号
//        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        if (!ObjectUtils.isEmpty(attributes)) {
//            HttpServletRequest request = attributes.getRequest();
//            if (!ObjectUtils.isEmpty(request)) {
//                String token = request.getHeader("token");
//                JwtValue jwtValue = JwtHelper.parseToken(token);
//                if (!ObjectUtils.isEmpty(jwtValue)) {
//                    account = jwtValue.getAccount();
//                }
//            }
//        }
//        return account;
//    }

    /**
     * 获取当前线程中Http请求的返回参数
     *
     * @return java.lang.String
     * @date 2022/10/19 18:17
     * @author ZoneFang
     */
    public static HttpServletResponse getResponse() {
        // 获取当前线程中Http请求的返回参数
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (!ObjectUtils.isEmpty(attributes)) {
            return attributes.getResponse();
        }
        return null;
    }

    /**
     * 获取当前线程的http请求 若没有则返回null
     *
     * @return javax.servlet.http.HttpServletRequest
     * @date 2022/6/27 9:04
     * @author ZoneFang
     */
    public static HttpServletRequest getHttpRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (ObjectUtils.isEmpty(attributes)) {
            return null;
        }
        return attributes.getRequest();
    }
}
