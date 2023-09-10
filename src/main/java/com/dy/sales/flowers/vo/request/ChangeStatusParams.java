package com.dy.sales.flowers.vo.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 *    状态变更参数
 * </p>
 *
 * @author chao.lan
 * @version 1.0.0
 * @since 2023/9/10 1:45
 */
@Getter
@Setter
public class ChangeStatusParams implements Serializable {
    private static final long serialVersionUID = -2006174509071119537L;

    /**
     * ID列表
     */
    private List<Long> ids;

    /**
     * 状态
     */
    private Integer status;
}
