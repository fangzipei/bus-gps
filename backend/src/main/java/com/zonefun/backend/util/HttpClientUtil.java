package com.zonefun.backend.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zonefun.backend.exception.CommonException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

/**
 * @Author: ZoneFang
 * @Date: 2021/4/17 下午1:34
 */
public class HttpClientUtil {
    private static final Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

    private final static ObjectMapper objectMapper = new ObjectMapper();

    private static final HttpClient client = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    /**
     * 同步请求+post+表单
     *
     * @param uri
     * @param params
     * @return
     */
    public static Object postForm(String uri, String params) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(params))
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response == null || response.statusCode() != 200) {
                throw new CommonException("调用后台接口异常").setParam("uri", uri).setParam("param", params);
            }
            return objectMapper.readValue(response.body(), Object.class);
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
            throw new CommonException("调用后台接口异常").setParam("uri", uri);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new CommonException("调用后台接口异常").setParam("uri", uri);
        }
    }

    /**
     * 通过请求获取请求者IP
     *
     * @param request 网络请求
     * @return java.lang.String
     * @date 2022/6/20 16:53
     * @author ZoneFang
     */
    public static String getRemoteIP(HttpServletRequest request) {
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
        // 本地地址
        if ("0:0:0:0:0:0:0:1".equals(ip)) {
            ip = "127.0.0.1";
        }
        return ip;
    }

}
