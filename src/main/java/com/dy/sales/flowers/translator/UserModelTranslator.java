package com.dy.sales.flowers.translator;

import com.dy.sales.flowers.entity.User;
import com.dy.sales.flowers.vo.constant.PermissionConstants;
import com.dy.sales.flowers.vo.response.UserModel;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
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
        UserModel model = new UserModel();
        model.setId(user.getId());
        model.setName(user.getName());
        model.setMobile(user.getMobile());
        model.setYn(user.getYn());
        model.setAdmin(Boolean.TRUE.equals(user.getAdmin()));
        model.setModifierName(user.getModifierName());
        model.setModified(user.getModified());

        model.setUuid(MDC.get("trace"));
        model.setLoginTime(System.currentTimeMillis());
        //权限
        if (Boolean.TRUE.equals(user.getAdmin())) {
            model.setPermissions(Arrays.asList(PermissionConstants.ADMIN, PermissionConstants.NORMAL));
        } else {
            model.setPermissions(Collections.singletonList(PermissionConstants.NORMAL));
        }
        return model;
    }
}
