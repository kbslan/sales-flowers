package com.dy.sales.flowers.vo.constant;

import java.time.format.DateTimeFormatter;

/**
 * <p>
 * nothing to say
 * </p>
 *
 * @author chao.lan
 * @version 1.0.0
 * @since 2023/9/22 21:58
 */
public class CommonConstants {
    // 2023-09-22
    public static DateTimeFormatter YYYY_MM_DD = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    //  2023-09-22 上午
    public static DateTimeFormatter YYYY_MM_DD_A = DateTimeFormatter.ofPattern("yyyy-MM-dd a");
    // 2023-09-22 21:58
    public static DateTimeFormatter YYYY_MM_DD_HH_MM = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    // 2023-09-22 21:58:00
    public static DateTimeFormatter YYYY_MM_DD_HH_MM_SS = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
}
