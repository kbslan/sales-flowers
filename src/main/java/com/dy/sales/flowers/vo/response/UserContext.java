package com.dy.sales.flowers.vo.response;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;


/**
 * 上下文
 *
 * @author chao.lan
 */
@Data
public class UserContext {

    private static final ThreadLocal<UserContext> HOLDER = ThreadLocal.withInitial(UserContext::new);

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户姓名：密文
     */
    private String userName;

    /**
     * 用户手机号
     */
    private String mobile;

    /**
     * 状态
     */
    private Integer yn;

    /**
     * 是否是管理员
     */
    private boolean admin;

    /**
     * 权限，逗号分隔
     */
    private String permission;

    /**
     * 扩展信息
     */
    private Map<String, String> extInfo;


    public static UserContext getInstance() {
        return HOLDER.get();
    }

    public static void setInstance(UserContext userContext) {
        HOLDER.set(userContext);
    }

    public static void remove() {
        HOLDER.remove();
    }

    public String fetchExt(String key) {
        return extInfo == null ? StringUtils.EMPTY : extInfo.get(key);
    }

    /**
     * 是否已登录
     */
    public boolean isLogged() {
        return userId != null;
    }

    /**
     * 是否是web浏览器登录
     */
    public boolean isWebLogin() {
        return true;
    }

}
