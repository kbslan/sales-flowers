package com.dy.sales.flowers.vo.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;


/**
 * 范围查询
 *
 * @author chao.lan
 */
@Getter
@Setter
@ToString
public class BetweenValue<T> implements Serializable {
    private static final long serialVersionUID = 3830000122546032924L;
    /**
     * 左边界（小值）
     */
    private T min;
    /**
     * 右边界（大值）
     */
    private T max;
}
