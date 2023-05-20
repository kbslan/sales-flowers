package com.dy.sales.flowers.vo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 状态枚举
 * @author chao.lan
 */
@Getter
@AllArgsConstructor
public enum YNEnum {
    /**
     * 启用、有效
     */
    YES(1, "启用"),
    /**
     * 禁用、无效
     */
    NO(0, "禁用");

    /**
     * 编码
     */
    private final Integer code;
    /**
     * 描述
     */
    private final String desc;
}
