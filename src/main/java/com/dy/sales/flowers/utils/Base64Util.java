package com.dy.sales.flowers.utils;


import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author lan_c
 * @date 2019/1/3
 */
public class Base64Util {

//    public static String encode(String str) {
//
//        byte[] bytes;
//        try {
//            bytes = str.getBytes(StandardCharsets.UTF_8);
//        } catch (Exception var3) {
//            return null;
//        }
//
//        return com.sun.org.apache.xerces.internal.impl.dv.util.Base64.encode(bytes);
//    }
//
//    public static String decode(String base64Str) {
//        if (StringUtils.isNotBlank(base64Str)) {
//            try {
//                return new String(com.sun.org.apache.xerces.internal.impl.dv.util.Base64.decode(base64Str));
//            } catch (Exception var2) {
//                return null;
//            }
//        } else {
//            return null;
//        }
//    }

    public static String encode(String str) {
        byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
        byte[] encodedBytes = Base64.getEncoder().encode(bytes);
        return new String(encodedBytes, StandardCharsets.UTF_8);
    }

    public static String decode(String base64Str) {
        if (base64Str != null) {
            byte[] decodedBytes = Base64.getDecoder().decode(base64Str);
            return new String(decodedBytes, StandardCharsets.UTF_8);
        } else {
            return null;
        }
    }
}
