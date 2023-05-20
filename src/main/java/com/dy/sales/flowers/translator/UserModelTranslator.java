package com.dy.sales.flowers.translator;

import com.dy.sales.flowers.entity.User;
import com.dy.sales.flowers.vo.constant.PermissionConstants;
import com.dy.sales.flowers.vo.response.UserModel;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.util.function.Function;

/**
 * 用户模型转换
 *
 * @author chao.lan
 */
@Component
public class UserModelTranslator implements Function<User, UserModel> {

    @Override
    public UserModel apply(User user) {
        UserModel userModel = new UserModel();
        userModel.setId(user.getId());
        userModel.setName(user.getName());
        userModel.setMobile(user.getMobile());
        userModel.setYn(user.getYn());
        userModel.setUuid(MDC.get("trace"));
        userModel.setLoginTime(System.currentTimeMillis());
        userModel.setAdmin(user.isAdmin());
        if (user.isAdmin()) {
            userModel.setPermission(PermissionConstants.ADMIN_USER_PERMISSION);
        }
        return userModel;
    }
}
