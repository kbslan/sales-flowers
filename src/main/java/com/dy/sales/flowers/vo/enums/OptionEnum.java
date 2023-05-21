package com.dy.sales.flowers.vo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 选项配置
 * @author chao.lan
 */
@Getter
@AllArgsConstructor
public enum OptionEnum {

    /**
     * 鲜花采摘人
     */
    FLOWER_PICKER(1, "鲜花采摘人"),
    /**
     * 鲜花品种
     */
    FLOWER_CATEGORY(2, "鲜花品种"),
    /**
     * 鲜花规格
     */
    FLOWER_SPECIFICATION(3, "鲜花规格"),
    /**
     * 鲜花损坏原因
     */
    FLOWER_DAMAGE_REASON(4, "鲜花损坏原因"),
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
    private static final Map<Integer, OptionEnum> CACHE;

    static {
        CACHE = Arrays.stream(values()).collect(Collectors.toMap(OptionEnum::getCode, Function.identity()));
    }

    public static OptionEnum get(Integer code) {
        return CACHE.get(code);
    }
}
