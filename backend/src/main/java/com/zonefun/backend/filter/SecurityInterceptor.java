package com.zonefun.backend.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class SecurityInterceptor implements HandlerInterceptor {

//    @Autowired
//    private AuthInfoDAO authInfoDAO;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String method = request.getMethod();
        String url = request.getServletPath();
        if ("".equals(url)) {
            url = request.getRequestURI();
        }
        if ((null != method && "OPTIONS".equals(method)) || isWhiteListUrl(url)) {
            return true;
        }
// 后端权限管理
//        if (this.hasPermission(handler, jwtValue, url)) {
//            return true;
//        }
        // 如果没有权限 则抛403异常 springboot会处理，跳转到 /error/403 页面
        response.sendError(HttpStatus.FORBIDDEN.value(), "无权限");
        return false;
    }

//    /**
//     * 是否有权限
//     *
//     * @param handler
//     * @return
//     */
//    private boolean hasPermission(Object handler, JwtValue jwtValue, String url) {
//        String account = jwtValue.getAccount();
//        String channel = jwtValue.getChannel();
//        if (handler instanceof HandlerMethod) {
//            HandlerMethod handlerMethod = (HandlerMethod) handler;
//            // 获取方法上的注解
//            RequiredPermission requiredPermission = handlerMethod.getMethod().getAnnotation(RequiredPermission.class);
//            // 如果方法上的注解为空 则获取类的注解
//            if (requiredPermission == null) {
//                requiredPermission = handlerMethod.getMethod().getDeclaringClass().getAnnotation(RequiredPermission.class);
//            }
//            // 如果标记了注解，则判断权限
//            if (requiredPermission != null) {
//                // 该注解目前只在管理后台使用
//                if (!channel.equals(ChannelConstant.CHANNEL_MNGR)) {
//                    //客户端token无权限访问管理后台，目前暂先校验这一条
//                    return false;
//                }
//
//                // 数据库 中获取该用户的权限信息 并判断是否有权限
//                // 获取注解的value
//                String authUrl = requiredPermission.value();
//                if (!ObjectUtils.isEmpty(authUrl)) {
//                    url = authUrl;
//                }
//                // 通过账号和权限路径判断其是否有该权限
//                Integer num = authInfoDAO.countAccountAuth(account, url);
//                if (ObjectUtils.isEmpty(num) || num < 1) {
//                    log.error("{}用户没有权限！", account);
//                    return false;
//                } else {
//                    return true;
//                }
//            }
//        }
//        return true;
//    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // TODO
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // TODO
    }

    private boolean isWhiteListUrl(String url) {
        if (url.equals("/user/login") || url.equals("/user/logout") || url.equals("/favicon.ico") || url.equals("/doc.html") || url.startsWith("/webjars") || url.contains("/service-worker.js")
                || url.contains("/swagger") || url.equals("/v3/api-docs") || url.equals("/captcha/get") || url.equals("/captcha/valid") || url.equals("/crypto/puk")
                || url.equals("/cust/send") || url.equals("/cust/validContInfo") || url.equals("/cust/validSmsCode") || url.equals("/cust/validOrganInfo")
                || url.equals("/cust/custLogin") || url.equals("/cust/custSmsLogin") || url.equals("/cust/resendLoginSms") || url.contains("test")
        ) {
            return true;
        } else {
            return false;
        }
    }

}
