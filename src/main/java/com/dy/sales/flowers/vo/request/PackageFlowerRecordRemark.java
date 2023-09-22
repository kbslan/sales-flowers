package com.dy.sales.flowers.vo.request;

import lombok.Getter;
import lombok.Setter;

/**
 * 包花记录评价
 * @author chao.lan
 */
@Getter
@Setter
public class PackageFlowerRecordRemark {

    /**
     * id
     */
    private Long id;

    /**
     * 评价内容
     */
    private String remark;

}
