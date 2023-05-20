package com.dy.sales.flowers.vo.response;

import com.dy.sales.flowers.vo.enums.YNEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 用户模型
 *
 * @author chao.lan
 */
@Getter
@Setter
@ToString
public class UserModel {

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
     * 随机值，保证每次生成的token不相同
     */
    private String uuid;

    /**
     * 登陆时间
     */
    private Long loginTime;

    /**
     * 是否是管理员
     */
    private boolean admin;

    /**
     * 权限，逗号分隔
     */
    private String permission;

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
