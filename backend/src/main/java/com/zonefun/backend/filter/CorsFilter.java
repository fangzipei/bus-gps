package com.zonefun.backend.filter;


//import com.zonefun.backend.util.RedisUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
public class CorsFilter implements Filter {

//    @Resource
//    private RedisUtil redisUtil;

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Value("${base.white-list-url}")
    private String whiteListUrl;

    @Autowired
    private Environment env;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String method = request.getMethod();

        ServletRequest requestWrapper = new RequestWrapper(request);

        if (null != method && "OPTIONS".equals(method)) {
            response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
            response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
            response.setHeader("Access-Control-Max-Age", "3600");
            response.setHeader("Access-Control-Allow-Headers", "token,Content-Type,X-Requested-With");
            response.setHeader("Access-Control-Allow-Credentials", "true"); //是否支持cookie跨域
            filterChain.doFilter(requestWrapper, servletResponse);
            return;
        }

        String url = request.getServletPath();
        String ip = getRemoteIP(request);
        if (isWhiteListUrl(url)) {
            response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
            response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
            response.setHeader("Access-Control-Max-Age", "3600");
            response.setHeader("Access-Control-Allow-Headers", "token,Content-Type,X-Requested-With");
            response.setHeader("Access-Control-Allow-Credentials", "true"); //是否支持cookie跨域
            filterChain.doFilter(requestWrapper, servletResponse);
            return;
        }
// 单点登录
//        String token = request.getHeader("token");
//        if (StrUtil.isBlank(token)) {
//            response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
//            response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
//            response.setHeader("Access-Control-Max-Age", "3600");
//            response.setHeader("Access-Control-Allow-Headers", "token,Content-Type,X-Requested-With");
//            response.setHeader("Access-Control-Allow-Credentials", "true"); //是否支持cookie跨域
//            response.setHeader("Access-Control-Expose-Headers", "sessiontimeout");
//            response.setHeader("sessiontimeout", "true");
//            response.sendError(HttpStatus.UNAUTHORIZED.value(), "登录超时或者未授权");
//            return;
//        }
//        String account = "";
//        JwtValue jwtValue = JwtHelper.parseToken(token);
//        if (null == jwtValue) {
//            response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
//            response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
//            response.setHeader("Access-Control-Max-Age", "3600");
//            response.setHeader("Access-Control-Allow-Headers", "token,Content-Type,X-Requested-With");
//            response.setHeader("Access-Control-Allow-Credentials", "true"); //是否支持cookie跨域
//            response.setHeader("Access-Control-Expose-Headers", "sessiontimeout");
//            response.setHeader("sessiontimeout", "true");
//            response.sendError(HttpStatus.UNAUTHORIZED.value(), "登录超时或者未授权");
//            return;
//        }
//        account = jwtValue.getAccount();
//        if (StrUtil.isBlank(account)) {
//            response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
//            response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
//            response.setHeader("Access-Control-Max-Age", "3600");
//            response.setHeader("Access-Control-Allow-Headers", "token,Content-Type,X-Requested-With");
//            response.setHeader("Access-Control-Allow-Credentials", "true"); //是否支持cookie跨域
//            response.setHeader("Access-Control-Expose-Headers", "sessiontimeout");
//            response.setHeader("sessiontimeout", "true");
//            response.sendError(HttpStatus.UNAUTHORIZED.value(), "登录超时或者未授权");
//            return;
//        }
//        // 为了实现客户平台异地限制同账号登录做的改变 后台管理支持多地同账号登录
//        // 后台管理redis中存的是prefix+token 客户平台存的是prefix+account,token
//        if (redisUtil.hasKey(CommonConstant.MNGR_LOGIN_TOKEN_PREFIX + token)) {
//            // 如果是管理后台的则续期
//            redisUtil.expire(CommonConstant.MNGR_LOGIN_TOKEN_PREFIX + token, CommonConstant.DS_TOKEN_REDIS_EXPIRE_SECOND);
//        } else if (redisUtil.hasKey(CommonConstant.CUST_LOGIN_TOKEN_PREFIX + account)) {
//            // 如果是管理平台的则需要判断下存的token和传的是否一样
//            Object redisToken = redisUtil.get(CommonConstant.CUST_LOGIN_TOKEN_PREFIX + account);
//            // 如果登录的token和redis存的token不同则报错
//            if (!token.equals(redisToken)) {
//                response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
//                response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
//                response.setHeader("Access-Control-Max-Age", "3600");
//                response.setHeader("Access-Control-Allow-Headers", "token,Content-Type,X-Requested-With");
//                response.setHeader("Access-Control-Allow-Credentials", "true"); //是否支持cookie跨域
//                response.setHeader("Access-Control-Expose-Headers", "sessiontimeout");
//                response.setHeader("sessiontimeout", "true");
//                response.sendError(HttpStatus.UNAUTHORIZED.value(), "登录超时或者未授权");
//                return;
//            } else {
//                // 相同的话则续期
//                redisUtil.expire(CommonConstant.CUST_LOGIN_TOKEN_PREFIX + account, CommonConstant.DS_TOKEN_REDIS_EXPIRE_SECOND);
//            }
//        } else {
//            // 如果都不满足则报错
//            response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
//            response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
//            response.setHeader("Access-Control-Max-Age", "3600");
//            response.setHeader("Access-Control-Allow-Headers", "token,Content-Type,X-Requested-With");
//            response.setHeader("Access-Control-Allow-Credentials", "true"); //是否支持cookie跨域
//            response.setHeader("Access-Control-Expose-Headers", "sessiontimeout");
//            response.setHeader("sessiontimeout", "true");
//            response.sendError(HttpStatus.UNAUTHORIZED.value(), "登录超时或者未授权");
//            return;
//        }
//
//
//        if (StrUtil.isNotBlank(account)) {
//            //记录日志
//            response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
//            response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
//            response.setHeader("Access-Control-Max-Age", "3600");
//            response.setHeader("Access-Control-Allow-Headers", ",Content-Type,X-Requested-With");
//            response.setHeader("Access-Control-Allow-Credentials", "true"); //是否支持cookie跨域
//            filterChain.doFilter(requestWrapper, servletResponse);
//        } else {
//            response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
//            response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
//            response.setHeader("Access-Control-Max-Age", "3600");
//            response.setHeader("Access-Control-Allow-Headers", ",Content-Type,X-Requested-With");
//            response.setHeader("Access-Control-Allow-Credentials", "true"); //是否支持cookie跨域
//            response.setHeader("Access-Control-Expose-Headers", "sessiontimeout");
//            response.setHeader("sessiontimeout", "true");
//            response.sendError(HttpStatus.UNAUTHORIZED.value(), "登录超时或者未授权");
//        }

        filterChain.doFilter(requestWrapper, servletResponse);

    }

    /**
     * 判断url是否在白名单中 若在白名单中则不需要token
     * @param url url
     * @return boolean
     * @date 2022/11/28 9:44
     * @author ZoneFang
     */
    private boolean isWhiteListUrl(String url) {
        String whiteList = env.getProperty("base.whiteListUrl");
        if (!ObjectUtils.isEmpty(whiteList)) {
            String[] arrWhiteList = whiteList.split(",");
            boolean res = false;
            for (String white : arrWhiteList) {
                if (white.equals(url)) {
                    res = true;
                    break;
                }
            }
            return res;
        }
        return true;
    }


    @Override
    public void destroy() {

    }

    public String getRemoteIP(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }


}
