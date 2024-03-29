package com.dy.sales.flowers.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 包花记录
 * </p>
 *
 * @author chao.lan
 * @since 2023-05-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("package_flower_record")
public class PackageFlowerRecord implements Serializable {

    private static final long serialVersionUID = 1956217935346879491L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 包花人ID
     */
    @TableField(value = "package_id", updateStrategy = FieldStrategy.NEVER)
    private Long packageId;

    /**
     * 采花人ID
     */
    @TableField("picker_id")
    private Long pickerId;

    /**
     * 品种ID
     */
    @TableField("category_id")
    private Long categoryId;

    /**
     * 规格ID
     */
    @TableField("specification_id")
    private Long specificationId;

    /**
     * 损坏原因ID
     */
    @TableField("damage_reason_id")
    private Long damageReasonId;

    /**
     * 包花数量
     */
    @TableField("package_amount")
    private Long packageAmount;

    /**
     * 损坏数量
     */
    @TableField("damage_amount")
    private Long damageAmount;

    /**
     * 状态 1: 审核通过, 0: 提报中, -1: 删除
     * @see com.dy.sales.flowers.vo.enums.YNEnum
     */
    @TableField("yn")
    private Integer yn;

    /**
     * 创建人ID
     */
    @TableField(value = "creator_id", updateStrategy = FieldStrategy.NEVER)
    private Long creatorId;

    /**
     * 创建人名称
     */
    @TableField(value = "creator_name", updateStrategy = FieldStrategy.NEVER)
    private String creatorName;

    /**
     * 创建时间
     */
    @TableField(value = "created", updateStrategy = FieldStrategy.NEVER)
    private LocalDateTime created;

    /**
     * 更新人ID
     */
    @TableField("modifier_id")
    private Long modifierId;

    /**
     * 更新人名称
     */
    @TableField("modifier_name")
    private String modifierName;

    /**
     * 更新时间
     */
    @TableField("modified")
    private LocalDateTime modified;

    /**
     * 审核人ID
     */
    @TableField("audit_id")
    private Long auditId;

    /**
     * 审核时间
     */
    @TableField("audit_time")
    private LocalDateTime auditTime;

    /**
     * 评价人ID
     */
    @TableField("remark_id")
    private Long remarkId;

    /**
     * 评价内容
     */
    @TableField("remark")
    private String remark;

    /**
     * 评价时间
     */
    @TableField("remark_time")
    private LocalDateTime remarkTime;


}
