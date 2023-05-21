package com.dy.sales.flowers.vo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 状态枚举
 * @author chao.lan
 */
@Getter
@AllArgsConstructor
public enum YNEnum {
    /**
     * 有效
     */
    YES(1, "有效"),

    /**
     * 无效
     */
    NO(0, "无效"),

    ;

    /**
     * 编码
     */
    private final Integer code;
    /**
     * 描述
     */
    private final String desc;

    /**
     * 枚举缓存
     */
    private static final Map<Integer, YNEnum> CACHE;

    static {
        CACHE = Arrays.stream(values()).collect(Collectors.toMap(YNEnum::getCode, Function.identity()));
    }

    public static YNEnum get(Integer code) {
        return CACHE.get(code);
    }
}
