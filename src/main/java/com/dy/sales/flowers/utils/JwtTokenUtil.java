package com.dy.sales.flowers.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author lan_c
 * @date 2019/1/3
 */
@Slf4j
public class JwtTokenUtil {

    /**
     * 加密
     */
    public static String encode(String secretKey, String str) {
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("typ", "JWT");
        headerMap.put("alg", "HS256");
        String headerStr = JSON.toJSONString(headerMap);
        String headerBase64 = Base64.encode(headerStr);
        if (headerBase64 == null) {
            log.error("JwtTokenUtil encode failed:{}", headerStr);
            return null;
        } else {
            String payloadBase64 = Base64.encode(str);
            if (payloadBase64 == null) {
                log.error("JwtTokenUtil encode failed:{}", str);
                return null;
            } else {
                String signatureOrigin = headerBase64 + "." + payloadBase64;
                String signature = HS256.sign(secretKey, signatureOrigin);
                if (signature == null) {
                    log.error("JwtTokenUtil signature failed:{}", signatureOrigin);
                    return null;
                } else {
                    return headerBase64 + "." + payloadBase64 + "." + signature;
                }
            }
        }
    }

    /**
     * 解密
     */
    public static JSONObject decode(String token) {
        return decode(token, JSONObject.class);
    }

    /**
     * 解密
     */
    public static <T> T decode(String token, Class<T> clazz) {
        if (StringUtils.isNotBlank(token)) {
            String[] components = token.split("\\.");
            if (components.length == 3) {
                String payloadBase64 = components[1];
                String payloadJsonStr = Base64.decode(payloadBase64);
                return StringUtils.isBlank(payloadJsonStr) ? null : JSON.parseObject(payloadJsonStr, clazz);
            }
        }

        return null;
    }

    /**
     * 校验是否是自己生成的token
     */
    public static boolean isMyToken(String secretKey, String token) {
        if (StringUtils.isNotBlank(token)) {
            String[] components = token.split("\\.");
            if (components.length == 3) {
                String headerBase64 = components[0];
                String payloadBase64 = components[1];
                String signature = components[2];
                String signatureOrigin = headerBase64 + "." + payloadBase64;
                String signatureVerified = HS256.sign(secretKey, signatureOrigin);
                if (StringUtils.isNotBlank(signatureVerified)) {
                    //签名是否一致
                    return signatureVerified.equals(signature);
                }
            }
        }
        return false;
    }
}

