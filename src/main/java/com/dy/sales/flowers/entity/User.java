package com.dy.sales.flowers.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dy.sales.flowers.vo.enums.YNEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

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


    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 昵称
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
    private boolean admin;

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
     * 创建时间
     */
    @TableField("created")
    private LocalDateTime created;

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
     * 权限，逗号分隔
     */
    @TableField(exist = false)
    private String permission;


}
