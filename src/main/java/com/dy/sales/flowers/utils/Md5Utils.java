package com.dy.sales.flowers.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;


/**
 * @author chao.lan
 */
@Slf4j
public class Md5Utils {

    public static String toMD5(String plainText) {
        if(StringUtils.isEmpty(plainText)) {
            return null;
        }
        return md5String(plainText.getBytes(StandardCharsets.UTF_8));
    }

    private static String md5String(byte[] data) {
        StringBuilder sb = new StringBuilder();
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] buf = md5.digest(data);
            for (byte b : buf) {
                sb.append(byte2Hex(b));
            }
        } catch (Exception e) {
            log.error("MD5Util.md5String() throw exception", e);
        }
        return sb.toString();
    }

    private static String byte2Hex(byte b) {
        String hex = Integer.toHexString(b);
        if (hex.length() > 2) {
            hex = hex.substring(hex.length() - 2);
        }
        while (hex.length() < 2) {
            hex = "0" + hex;
        }
        return hex;
    }
}

