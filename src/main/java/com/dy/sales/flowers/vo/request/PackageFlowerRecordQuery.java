package com.dy.sales.flowers.vo.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 包花记录查询参数
 * @author chao.lan
 */
@Getter
@Setter
public class PackageFlowerRecordQuery extends PageQuery{

    /**
     * id
     */
    private Long id;

    /**
     * 包花人ID
     */
    private Long packageId;

    /**
     * 采花人ID
     */
    private Long pickerId;

    /**
     * 品种ID
     */
    private Long categoryId;

    /**
     * 规格ID
     */
    private Long specificationId;

    /**
     * 损坏原因ID
     */
    private Long damageReasonId;

    /**
     * 包花数量
     */
    private Long packageAmount;

    /**
     * 损坏数量
     */
    private Long damageAmount;

    /**
     * 状态 1: 审核通过, 0: 提报中, -1: 删除
     */
    private Integer yn;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime start;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime end;


}
