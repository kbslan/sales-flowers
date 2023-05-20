package com.dy.sales.flowers.utils;

/**
 * Created by lan_c on 2019/1/3.
 */
import org.springframework.util.StringUtils;

public class Base64 {
    public Base64() {
    }

    public static String encode(String str) {
        Object var1 = null;

        byte[] bytes;
        try {
            bytes = str.getBytes("UTF-8");
        } catch (Exception var3) {
            return null;
        }

        return new String(com.sun.org.apache.xerces.internal.impl.dv.util.Base64.encode(bytes));
    }

    public static String decode(String base64Str) {
        if (base64Str != null && !StringUtils.isEmpty(base64Str)) {
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
