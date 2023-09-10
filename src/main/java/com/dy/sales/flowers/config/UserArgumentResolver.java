package com.dy.sales.flowers.config;

import com.dy.sales.flowers.annotation.CurrentUser;
import com.dy.sales.flowers.entity.User;
import com.dy.sales.flowers.exception.BusinessException;
import com.dy.sales.flowers.vo.constant.PermissionConstants;
import com.dy.sales.flowers.vo.enums.ResultCode;
import com.dy.sales.flowers.vo.response.UserContext;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.*;

/**
 * 用户信息参数处理类
 * {@link CurrentUser}
 *
 * @author chao.lan
 */
@Component
public class UserArgumentResolver implements HandlerMethodArgumentResolver {


    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(User.class) && parameter.hasParameterAnnotation(CurrentUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {
        UserContext context = UserContext.getInstance();
        if (Objects.isNull(context)) {
            throw new BusinessException(ResultCode.SYS_EXCEPTION);
        }

        User user = new User();
        user.setId(context.getUserId());
        user.setName(context.getUserName());
        user.setMobile(context.getMobile());
        user.setYn(context.getYn());
        user.setAdmin(context.isAdmin());
        user.setPermissions(context.getPermissions());
        //接口访问权限校验
        CurrentUser currentUser = parameter.getParameterAnnotation(CurrentUser.class);
        String permission = currentUser.permission();
        //需要权限
        if (StringUtils.isNotBlank(permission)) {
            if (!hasPermission(user, permission)) {
                throw new BusinessException(ResultCode.REQUEST_ILLEGAL);
            }
        }

        return user;
    }

    /**
     * 权限判断
     */
    private boolean hasPermission(User currentUser, String needPermission) {
        if (currentUser == null) {
            return false;
        }
        if (StringUtils.isBlank(needPermission)) {
            return true;
        }
        List<String> userPermission = currentUser.getPermissions();
        if (CollectionUtils.isEmpty(userPermission)) {
            return false;
        } else {
            //超级管理员
            if (userPermission.contains(PermissionConstants.ADMIN)) {
                return true;
            }
            for (String item : needPermission.split(",")) {
                if (userPermission.contains(item)) {
                    return true;
                }
            }
        }
        return false;
    }

}
