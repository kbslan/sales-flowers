package com.dy.sales.flowers.utils;

import org.apache.commons.lang3.StringUtils;

import java.nio.charset.StandardCharsets;

/**
 * @author lan_c
 * @date 2019/1/3
 */
public class Base64 {

    public static String encode(String str) {

        byte[] bytes;
        try {
            bytes = str.getBytes(StandardCharsets.UTF_8);
        } catch (Exception var3) {
            return null;
        }

        return com.sun.org.apache.xerces.internal.impl.dv.util.Base64.encode(bytes);
    }

    public static String decode(String base64Str) {
        if (StringUtils.isNotBlank(base64Str)) {
            try {
                return new String(com.sun.org.apache.xerces.internal.impl.dv.util.Base64.decode(base64Str));
            } catch (Exception var2) {
                return null;
            }
        } else {
            return null;
        }
    }
}
