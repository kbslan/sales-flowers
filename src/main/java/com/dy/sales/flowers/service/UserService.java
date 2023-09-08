package com.dy.sales.flowers.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dy.sales.flowers.entity.User;
import com.dy.sales.flowers.exception.LoginException;
import com.dy.sales.flowers.vo.request.UserQuery;
import com.dy.sales.flowers.vo.response.UserModel;

import java.util.List;

/**
 * <p>
 * 账号 服务类
 * </p>
 *
 * @author chao.lan
 * @since 2023-05-20
 */
public interface UserService extends IService<User> {

    /**
     * 手机号查询用户信息
     *
     * @param mobile 手机号
     * @return 用户信息
     */
    User getUserByMobile(String mobile);

    /**
     * 登陆
     *
     * @param request 登陆参数
     * @return 登陆成功返回用户对象，否则为null
     * @throws LoginException 登陆异常
     */
    UserModel login(UserQuery request) throws LoginException;

    /**
     * 登出
     *
     * @param user 用户
     */
    void logout(User user);

    /**
     * 注册
     *
     * @param request 注册参数
     * @return 注册成功返回用户对象，否则为null
     */
    UserModel register(UserQuery request);

    /**
     * 重置密码
     *
     * @param request 参数
     * @param user    操作人信息
     * @return 成功、失败
     */
    boolean resetPassword(UserQuery request, User user);

    /**
     * 分页查询
     *
     * @param request 参数
     * @return 结果
     */
    Page<User> pageQuery(UserQuery request);

    /**
     * 逻辑删除
     *
     * @param ids  用户ID列表
     * @param user 操作人信息
     * @return 结果
     */
    boolean deletes(List<Long> ids, User user);
}
