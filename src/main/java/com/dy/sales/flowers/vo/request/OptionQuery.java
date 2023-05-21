package com.dy.sales.flowers.vo.request;

import com.dy.sales.flowers.vo.enums.OptionEnum;
import com.dy.sales.flowers.vo.enums.YNEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * 配置选项参数
 * @author chao.lan
 */
@Getter
@Setter
public class OptionQuery extends PageQuery{

    /**
     * id
     */
    private Long id;
    /**
     * 类型， 1：采花人，2：品种，3：规格，4：报损原因
     * @see OptionEnum
     */
    private Integer type;

    /**
     * 名称
     */
    private String label;

    /**
     * 值
     */
    private String value;

    /**
     * 状态
     */
    private Integer yn = YNEnum.YES.getCode();


}
