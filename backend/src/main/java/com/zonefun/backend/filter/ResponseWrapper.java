package com.zonefun.backend.filter;

import com.alibaba.fastjson.JSONObject;
import jdk.jfr.Description;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;

/**
 * @ClassName ResponseWrapper
 * @Description 为了获取返回请求中的数据 提供一个对HttpServletResponse的外层包装
 * @Date 2022/6/20 9:05
 * @Author ZoneFang
 */
@Slf4j
public class ResponseWrapper extends HttpServletResponseWrapper {
    @Description("将返回体改为JSONObject 方便获取")
    private JSONObject body;

    /**
     * 封装请求 因为http请求流正常读一次就会消失，对其实例化后可以多次读取
     *
     * @param response 原始请求
     * @return
     * @date 2022/10/18 14:25
     * @author ZoneFang
     */
    public ResponseWrapper(HttpServletResponse response) throws IOException {
        super(response);
        body = new JSONObject();
    }

    @Override
    public String getHeader(String name) {
        return super.getHeader(name);
    }

    @Override
    public void setResponse(ServletResponse response) {
        super.setResponse(response);
    }
}


