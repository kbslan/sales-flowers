package com.dy.sales.flowers.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 基于SSO获取当前用户信息
 *
 * @author chao.lan
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface CurrentUser {
    /**
     * 多个权限用逗号分隔，权限之间是“或者”的关系（满足其一即可）
     */
    String permission() default "";
}
