package com.zonefun.backend.filter;


import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.json.JSONObject;
import com.zonefun.backend.vo.JwtValue;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/*
 * 构造及解析jwt的工具类
 */
public class JwtHelper {

    private static final String SECRET = "zone_algorithm!23";

    public static String generateToken(String account, String channel) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("account", account);
        map.put("channel", channel);
        String jwt = Jwts.builder()
                .setClaims(map)
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))// 24小时后过期
                .signWith(SignatureAlgorithm.HS256, SECRET + SecureUtil.md5(account))
                .compact();
        return jwt;
    }

    public static JwtValue parseToken(String token) {
        if (StrUtil.isBlank(token)) {
            return null;
        }
        String[] payloads = token.split("\\.");
        if (payloads.length == 0 || payloads.length < 3) {
            return null;
        }
        String payload = new String(Base64.getDecoder().decode(payloads[1]));
        JSONObject jsonObject = new JSONObject(payload);
        String account = jsonObject.getStr("account");
        try {
            // parse the token.
            Map<String, Object> body = Jwts.parser()
                    .setSigningKey(SECRET + SecureUtil.md5(account))
                    .parseClaimsJws(token)
                    .getBody();
            JwtValue jwtValue = new JwtValue();
            jwtValue.setAccount((String) body.get("account"));
            jwtValue.setChannel((String) body.get("channel"));
            return jwtValue;
        } catch (Exception e) {
            return null;
        }
    }


}
