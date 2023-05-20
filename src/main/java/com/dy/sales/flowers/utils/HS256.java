package com.dy.sales.flowers.utils;

import org.apache.commons.codec.binary.Hex;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

/**
 * Created by lan_c on 2019/1/3.
 */
public class HS256 {

    /**
     * 数字签名
     *
     * @param secretKey 秘钥
     * @param data      数据
     * @return 签名
     */
    public static String sign(String secretKey, String data) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            byte[] secretByte = secretKey.getBytes(StandardCharsets.UTF_8);
            byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
            SecretKey secret = new SecretKeySpec(secretByte, "HMACSHA256");
            mac.init(secret);
            byte[] doFinal = mac.doFinal(dataBytes);
            byte[] hexB = (new Hex()).encode(doFinal);
            return new String(hexB);
        } catch (Exception var8) {
            return null;
        }
    }
}
