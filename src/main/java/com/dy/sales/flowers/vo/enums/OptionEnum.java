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
    FLOWER_PICKER("flower_picker", "鲜花采摘人"),
    /**
     * 鲜花品种
     */
    FLOWER_CATEGORY("flower_category", "鲜花品种"),
    /**
     * 鲜花规格
     */
    FLOWER_SPECIFICATION("flower_specification", "鲜花规格"),
    /**
     * 鲜花损坏原因
     */
    FLOWER_DAMAGE_REASON("flower_damage_reason", "鲜花损坏原因"),
    ;

    /**
     * 编码
     */
    private final String code;
    /**
     * 描述
     */
    private final String desc;

    /**
     * 枚举缓存
     */
    private static final Map<String, OptionEnum> CACHE;

    static {
        CACHE = Arrays.stream(values()).collect(Collectors.toMap(OptionEnum::getCode, Function.identity()));
    }

    public static OptionEnum get(String code) {
        return CACHE.get(code);
    }
}
