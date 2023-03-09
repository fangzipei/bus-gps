package com.zonefun.backend.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Charsets;
import jdk.jfr.Description;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;

/**
 * @ClassName RequestWrapper
 * @Description 为了解决拦截器读取请求流数据后导致controller无法再次读取数据的问题重写的请求处理类
 * @Date 2022/6/20 9:05
 * @Author ZoneFang
 */
@Slf4j
public class RequestWrapper extends HttpServletRequestWrapper {
    @Description("将请求Body转为字符串")
    private String body;
    @Description("将请求Body转为JSONObject")
    private JSONObject requestParameter;

    /**
     * 封装请求 因为http请求流正常读一次就会消失，对其实例化后可以多次读取
     *
     * @param request 原始请求
     * @return
     * @date 2022/10/18 14:25
     * @author ZoneFang
     */
    public RequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        // 若是GET请求或者application/json请求则对其封装
        if (ObjectUtils.isEmpty(request.getContentType()) || request.getContentType().startsWith("application/json")) {
            try {
                body = getBodyString(request);
                requestParameter = JSON.parseObject(body);
            } catch (Exception e) {
                e.printStackTrace();
                log.error("url:{}，请求体转换出错！", request.getRequestURI());
            }
        }
    }

    /**
     * 重写getInputStream 从body中获取请求参数
     *
     * @return javax.servlet.ServletInputStream
     * @date 2022/6/20 9:16
     * @author ZoneFang
     */
    @Override
    public ServletInputStream getInputStream() throws IOException {
        if(!ObjectUtils.isEmpty(body)) {
            final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body.getBytes(Charsets.UTF_8));
            return new ServletInputStream() {
                @Override
                public boolean isFinished() {
                    return false;
                }

                @Override
                public boolean isReady() {
                    return false;
                }

                @Override
                public void setReadListener(ReadListener readListener) {

                }

                @Override
                public int read() throws IOException {
                    return byteArrayInputStream.read();
                }
            };
        } else {
            return super.getInputStream();
        }
    }

    /**
     * 重写获取字符流的方式
     *
     * @return java.io.BufferedReader
     * @date 2022/6/20 9:32
     * @author ZoneFang
     */
    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(this.getInputStream(), Charsets.UTF_8));
    }

    @Override
    public String getHeader(String name) {
        return super.getHeader(name);
    }

    @Override
    public Enumeration<String> getHeaderNames() {
        return super.getHeaderNames();
    }

    @Override
    public Enumeration<String> getHeaders(String name) {
        return super.getHeaders(name);
    }

    /**
     * 获取body字符串
     *
     * @return java.lang.String
     * @date 2022/10/18 14:43
     * @author ZoneFang
     */
    public String getBody() {
        return this.body;
    }

    /**
     * 获取参数的JSONObject
     *
     * @return com.alibaba.fastjson.JSONObject
     * @date 2022/10/18 14:44
     * @author ZoneFang
     */
    public JSONObject getRequestParameter() {
        return this.requestParameter;
    }

    /**
     * 获取请求Body
     *
     * @param request 请求
     * @return java.lang.String
     * @date 2022/10/18 14:22
     * @author ZoneFang
     */
    private static String getBodyString(ServletRequest request) {
        StringBuilder sb = new StringBuilder();
        InputStream inputStream = null;
        BufferedReader reader = null;
        try {
            inputStream = request.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            String line = "";
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

    /**
     * 获取当前请求者的ip
     *
     * @return java.lang.String
     * @date 2022/10/20 16:04
     * @author ZoneFang
     */
    public String getRemoteIP() {
        String ip = this.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = this.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = this.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = this.getRemoteAddr();
        }
        return ip;
    }
}


