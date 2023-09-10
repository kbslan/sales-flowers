package com.dy.sales.flowers.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.dy.sales.flowers.vo.enums.YNEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 账号
 * </p>
 *
 * @author chao.lan
 * @since 2023-05-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("user")
public class User implements Serializable {
    private static final long serialVersionUID = -5820758145443805098L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户名称
     */
    @TableField("name")
    private String name;

    /**
     * 电话号码
     */
    @TableField("mobile")
    private String mobile;

    /**
     * 是否是管理员
     */
    @TableField("admin")
    private Boolean admin;

    /**
     * 密码, 暂时先明文，后续有需要再改造
     */
    @TableField("password")
    private String password;

    /**
     * 状态 1: 启用, 0: 禁用
     * @see YNEnum
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
     * 密码salt
     */
    @TableField("salt")
    private String salt;

    /**
     * 权限列表
     */
    @TableField(exist = false)
    private List<String> permissions;


}
