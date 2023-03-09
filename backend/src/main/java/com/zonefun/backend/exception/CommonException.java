package com.zonefun.backend.exception;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: wuhao
 * @Date: 2021/3/31 下午1:43
 */
@NoArgsConstructor
@Data
public class CommonException extends RuntimeException {
    private Integer code;

    private String message;

    private Throwable e;

    private Map<String, Object> errorProperties = new HashMap();

    public CommonException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public CommonException(String message) {
        this.code = 400;
        this.message = message;
    }

    public CommonException(String message, Throwable e) {
        this.code = 400;
        this.message = message;
        this.e = e;
    }

    public CommonException setParam(String key, Object value) {
        errorProperties.put(key, value);
        return this;
    }

    @Override
    public String getMessage() {
        StringBuilder sb = new StringBuilder();
        if (message != null) {
            sb.append(message);
        }
        if (!errorProperties.isEmpty()) {
            sb.append(" [");
            List<String> errorParams = errorProperties.entrySet().stream()
                    .map(entry -> entry.getKey() + "=" + entry.getValue())
                    .collect(Collectors.toList());
            sb.append(String.join(",", errorParams));
            sb.append("]");
        }
        return sb.toString();
    }
}
