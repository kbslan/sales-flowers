package com.dy.sales.flowers.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dy.sales.flowers.entity.User;
import com.dy.sales.flowers.exception.BusinessException;
import com.dy.sales.flowers.exception.LoginException;
import com.dy.sales.flowers.mapper.UserMapper;
import com.dy.sales.flowers.service.UserService;
import com.dy.sales.flowers.translator.UserModelTranslator;
import com.dy.sales.flowers.vo.enums.ResultCode;
import com.dy.sales.flowers.vo.enums.YNEnum;
import com.dy.sales.flowers.vo.request.UserQuery;
import com.dy.sales.flowers.vo.response.UserModel;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


/**
 * <p>
 * 账号 服务实现类
 * </p>
 *
 * @author chao.lan
 * @since 2023-05-20
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    //11位手机号码校验
    private static final Pattern MOBILE_PATTERN = Pattern.compile("^1[3-9]\\d{9}$");
    @Resource
    private UserModelTranslator userModelTranslator;

    @Override
    public User getUserByMobile(String mobile) {
        Assert.hasLength(mobile, "手机号码为空");
        Assert.isTrue(MOBILE_PATTERN.matcher(mobile).matches(), "手机号码格式错误");
        return this.getOne(Wrappers.<User>lambdaQuery().eq(User::getMobile, mobile));
    }

    @Override
    public UserModel login(UserQuery request) throws LoginException {

        User user = this.getUserByMobile(request.getMobile());
        //用户不存在
        if (Objects.isNull(user)) {
            throw new LoginException(ResultCode.USER_NOT_FOUND);
        }
        //密码错误
        if (!Objects.equals(request.getPassword(), user.getPassword())) {
            throw new LoginException(ResultCode.USER_PASSWORD_ERROR);
        }
        //账号禁用
        if (!Objects.equals(user.getYn(), YNEnum.YES.getCode())) {
            throw new LoginException(ResultCode.USER_DISABLED);
        }
        //校验通过，转换对象，减少暴露信息
        return userModelTranslator.apply(user);
    }

    @Override
    public void logout(User user) {
        //ignore
    }

    @Override
    public UserModel register(UserQuery request) {
        String mobile = StringUtils.isBlank(request.getMobile()) ? null : request.getMobile().trim();
        if (StringUtils.isBlank(mobile)) {
            throw new BusinessException(ResultCode.MOBILE_EMPTY);
        }
        if (StringUtils.isBlank(request.getName())) {
            request.setName(mobile);
        }
        if (StringUtils.isBlank(request.getPassword())) {
            throw new BusinessException(ResultCode.PASSWORD_MISSING);
        }
        User user = this.getUserByMobile(mobile);
        //号码已被注册
        if (Objects.nonNull(user)) {
            throw new BusinessException(ResultCode.MOBILE_REGISTERED);
        }
        if (StringUtils.isNotBlank(request.getName())) {
            user = this.getOne(Wrappers.<User>lambdaQuery().eq(User::getName, request.getName()));
            if (Objects.nonNull(user)) {
                throw new BusinessException(ResultCode.USER_NAME_REGISTERED);
            }
        }
        user = new User();
        user.setName(StringUtils.isBlank(request.getName()) ? UUID.randomUUID().toString().replace("-", "") : request.getName());
        user.setMobile(mobile);
        user.setAdmin(false);
        user.setPassword(request.getPassword());
        user.setYn(YNEnum.YES.getCode());
        user.setCreated(LocalDateTime.now());
        user.setCreatorId(-1L);
        user.setCreatorName("register");
        user.setModifierId(-1L);
        user.setModifierName("register");
        user.setModified(LocalDateTime.now());
        user.setSalt("");

        boolean success = this.save(user);
        if (success) {
            return userModelTranslator.apply(user);
        }
        return null;
    }

    @Override
    public boolean resetPassword(UserQuery request, User user) {
        User dbUser = this.getUserByMobile(request.getMobile());
        //用户不存在
        if (Objects.isNull(dbUser)) {
            throw new LoginException(ResultCode.USER_NOT_FOUND);
        }
        //旧密码不一致
        if (!Objects.equals(request.getOldPassword(), dbUser.getPassword())) {
            throw new LoginException(ResultCode.RESET_PASSWORD_NOT_EQUAL);
        }
        dbUser.setPassword(request.getPassword());
        dbUser.setModified(LocalDateTime.now());
        dbUser.setModifierId(user.getId());
        dbUser.setModifierName(user.getName());
        return this.updateById(dbUser);
    }

    @Override
    public Page<User> pageQuery(UserQuery request) {
        return this.page(new Page<>(request.getPage(), request.getSize()),
                Wrappers.<User>lambdaQuery()
                        .like(StringUtils.isNotBlank(request.getName()), User::getName, request.getName())
                        .like(StringUtils.isNotBlank(request.getMobile()), User::getMobile, request.getMobile())
                        .eq(User::getYn, Objects.isNull(request.getYn()) ? YNEnum.YES.getCode() : request.getYn())
                        .orderByDesc(User::getModified)

        );
    }

    @Override
    public boolean deletes(List<Long> ids, User user) {
        if (CollectionUtils.isNotEmpty(ids)) {
            return this.updateBatchById(ids.stream().map(id -> {
                User update = new User();
                update.setId(id);
                update.setYn(YNEnum.NO.getCode());
                update.setModified(LocalDateTime.now());
                update.setModifierId(user.getId());
                update.setModifierName(user.getName());
                return update;
            }).collect(Collectors.toList()), 200);
        }
        return false;
    }
}
