package com.dy.sales.flowers.config.filter.impl;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * nothing to say
 * </p>
 *
 * @author chao.lan
 * @version 1.0.0
 * @since 2023/9/8 10:46
 */
@Component
public class LoginIgnoreRequestUri {

    private static final List<String> IGNORE_URI = Arrays.asList("/user/register", "/user/login", "/user/password/reset");

    public boolean isIgnore(String uri) {
        return IGNORE_URI.contains(uri);
    }
}
