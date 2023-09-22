package com.dy.sales.flowers.vo.request;

import lombok.Getter;
import lombok.Setter;

/**
 * 包花记录审核
 * @author chao.lan
 */
@Getter
@Setter
public class PackageFlowerRecordAudit {

    /**
     * id
     */
    private Long id;
    /**
     * 状态 1: 审核通过, 0: 提报中, -1: 删除
     */
    private Integer yn;

}
