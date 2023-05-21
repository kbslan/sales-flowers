package com.dy.sales.flowers.vo.request;

import com.dy.sales.flowers.vo.enums.YNEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 登陆参数
 *
 * @author chao.lan
 */
@Getter
@Setter
@ToString
public class UserQuery extends PageQuery {

    /**
     * 用户昵称（注册时使用）
     */
    private String name;

    /**
     * 手机号
     */
    private String mobile;
    /**
     * 密码
     */
    private String password;
    /**
     * 旧密码（重置密码时使用）
     */
    private String oldPassword;

    /**
     * 状态
     */
    private Integer yn = YNEnum.YES.getCode();
}
