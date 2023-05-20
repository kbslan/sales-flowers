package com.dy.sales.flowers.web;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dy.sales.flowers.annotation.CurrentUser;
import com.dy.sales.flowers.entity.User;
import com.dy.sales.flowers.service.AuthService;
import com.dy.sales.flowers.service.UserService;
import com.dy.sales.flowers.translator.UserModelTranslator;
import com.dy.sales.flowers.utils.CookieUtils;
import com.dy.sales.flowers.vo.constant.PermissionConstants;
import com.dy.sales.flowers.vo.constant.SsoConstants;
import com.dy.sales.flowers.vo.enums.ResultCode;
import com.dy.sales.flowers.vo.request.UserQuery;
import com.dy.sales.flowers.vo.response.HttpResult;
import com.dy.sales.flowers.vo.response.UserModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 账号 前端控制器
 * </p>
 *
 * @author chao.lan
 * @since 2023-05-20
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;
    @Resource
    private AuthService authService;
    @Resource
    private UserModelTranslator userModelTranslator;

    /**
     * 用户登陆接口
     *
     * @param request  登陆参数
     * @param response 响应，设置cookie
     * @return 用户模型
     */
    @PostMapping("/login")
    public HttpResult<UserModel> login(@RequestBody UserQuery request, HttpServletResponse response) {
        log.info("Login开始登陆验证 request={}", request);
        UserModel userModel = userService.login(request);
        if (Objects.nonNull(userModel)) {
            String token = authService.generatorToken(userModel);
            CookieUtils.set(response, SsoConstants.COOKIE_NAME, token, SsoConstants.COOKIE_DOMAIN, SsoConstants.COOKIE_PATH, SsoConstants.MAX_AGE, Boolean.TRUE, SsoConstants.SECURE);
        }

        return HttpResult.success(userModel);

    }

    /**
     * 退出登录
     *
     * @param user 当前用户信息
     * @return 结果
     */
    @GetMapping("/logout")
    public HttpResult<Void> logout(@CurrentUser User user, HttpServletResponse response) {
        userService.logout(user);
        log.info("用户退出登录：{}", user);
        CookieUtils.set(response, SsoConstants.COOKIE_NAME, "", SsoConstants.COOKIE_DOMAIN, SsoConstants.COOKIE_PATH, 1, Boolean.TRUE, SsoConstants.SECURE);
        return HttpResult.success();
    }


    /**
     * 用户注册接口
     */
    @PostMapping("/register")
    public HttpResult<Boolean> register(@RequestBody UserQuery request) {
        return HttpResult.success(userService.register(request));
    }


    /**
     * 用户修改密码接口
     */
    @PostMapping("/password/reset")
    public HttpResult<Boolean> resetPassword(@RequestBody UserQuery request, @CurrentUser User user) {
        //是否是本人修改密码
        if (!Objects.equals(request.getMobile(), user.getMobile())) {
            log.error("重置密码失败，非本人操作 request.mobile={} user.mobile={}", request.getMobile(), user.getMobile());
            return HttpResult.failed(ResultCode.RESET_PASSWORD_FAILED);
        }

        boolean success = userService.resetPassword(request);
        return success ? HttpResult.success() : HttpResult.failed(ResultCode.SYS_EXCEPTION);
    }

    /**
     * 用户列表查询
     */
    @PostMapping("/page")
    public HttpResult<Page<User>> page(@RequestBody UserQuery request, @CurrentUser(permission = PermissionConstants.ADMIN_USER_PERMISSION) User user) {
        return HttpResult.success(userService.pageQuery(request));
    }

    /**
     * 用户删除接口
     */
    @GetMapping("/delete")
    public HttpResult<Boolean> delete(@RequestParam("ids") List<Long> ids, @CurrentUser(permission = PermissionConstants.ADMIN_USER_PERMISSION) User user) {
        if (CollectionUtils.isEmpty(ids)) {
            return HttpResult.failed(ResultCode.PARAM_EXCEPTION);
        }
        boolean success = userService.deletes(ids, user);
        return success ? HttpResult.success() : HttpResult.failed(ResultCode.SYS_EXCEPTION);
    }

    /**
     * 用户信息查询接口
     */
    @GetMapping("/info")
    public HttpResult<UserModel> info(@RequestParam(name = "id", required = false) Long id, @CurrentUser User user) {
        if (Objects.isNull(id)) {
            id = user.getId();
        }
        User byId = userService.getById(id);
        if (Objects.isNull(byId)) {
            return HttpResult.failed(ResultCode.USER_NOT_FOUND);
        }

        return HttpResult.success(userModelTranslator.apply(byId));
    }

}
