package com.dy.sales.flowers.vo.response;

import com.dy.sales.flowers.vo.enums.YNEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户模型
 *
 * @author chao.lan
 */
@Getter
@Setter
@ToString
public class UserModel implements Serializable {

    private static final long serialVersionUID = -2810067788742742354L;
    /**
     * 主键
     */
    private Long id;

    /**
     * 昵称
     */
    private String name;

    /**
     * 电话号码
     */
    private String mobile;

    /**
     * 状态 1: 启用, 0: 禁用
     *
     * @see YNEnum
     */
    private Integer yn;

    /**
     * 是否是管理员
     */
    private boolean admin;

    /**
     * 更新人名称
     */
    private String modifierName;

    /**
     * 更新时间
     */
    private LocalDateTime modified;
    /**
     * 随机值，保证每次生成的token不相同
     */
    private String uuid;

    /**
     * 登陆时间
     */
    private Long loginTime;

    /**
     * 权限列表
     */
    private List<String> permissions;

    /**
     * token
     */
    private String token;

    /**
     * 是否超过有效期
     *
     * @return true: 过期， false: 未过期
     */
    public boolean isExpired() {
//        if (Objects.isNull(loginTime)) {
//            return true;
//        }
//
//        return (System.currentTimeMillis() - loginTime) > SsoConstants.MAX_AGE * 60;
        return false;
    }
}
