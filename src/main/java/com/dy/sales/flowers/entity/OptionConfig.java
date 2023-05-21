package com.dy.sales.flowers.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.dy.sales.flowers.vo.enums.OptionEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 选项配置
 * </p>
 *
 * @author chao.lan
 * @since 2023-05-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("option_config")
public class OptionConfig implements Serializable {
    private static final long serialVersionUID = 5852062244239152034L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 类型， 1：采花人，2：品种，3：规格，4：报损原因
     * @see OptionEnum
     */
    @TableField("type")
    private Integer type;

    /**
     * 名称
     */
    @TableField("label")
    private String label;

    /**
     * 值
     */
    @TableField("value")
    private String value;

    /**
     * 状态
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


}
