package com.dy.sales.flowers.vo.request;

import lombok.Getter;
import lombok.Setter;

/**
 * 分页查询基础对象
 *
 * @author chao.lan
 */
@Getter
@Setter
public class PageQuery {

    private Integer page = 1;

    private Integer size = 20;

    private Boolean searchCount = true;
}
