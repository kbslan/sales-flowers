package com.dy.sales.flowers.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dy.sales.flowers.entity.User;
import com.dy.sales.flowers.exception.BusinessException;
import com.dy.sales.flowers.exception.LoginException;
import com.dy.sales.flowers.mapper.UserMapper;
import com.dy.sales.flowers.service.UserService;
import com.dy.sales.flowers.translator.UserModelTranslator;
import com.dy.sales.flowers.utils.PageUtils;
import com.dy.sales.flowers.vo.enums.ResultCode;
import com.dy.sales.flowers.vo.enums.YNEnum;
import com.dy.sales.flowers.vo.request.ChangeStatusParams;
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

    private static final String ADMIN = "admin";
    @Resource
    private UserModelTranslator userModelTranslator;

    @Override
    public User getUserByMobile(String mobile) {
        Assert.hasLength(mobile, "手机号码为空");
        if (!Objects.equals(mobile, ADMIN) && !MOBILE_PATTERN.matcher(mobile).matches()) {
            throw new BusinessException(ResultCode.MOBILE_ILLEGAL);
        }
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
    public Boolean register(UserQuery request) {
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

        return this.save(user);
    }

    @Override
    public Boolean resetPassword(UserQuery request) {
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
        dbUser.setModifierId(dbUser.getId());
        dbUser.setModifierName(dbUser.getName());

        return this.updateById(dbUser);
    }

    @Override
    public Page<UserModel> pageQuery(UserQuery request) {
        Page<User> page = this.page(new Page<>(request.getPage(), request.getSize()), buildQueryWrapper(request));
        return PageUtils.convertToPage(page, userModelTranslator);
    }

    private static LambdaQueryWrapper<User> buildQueryWrapper(UserQuery request) {
        return Wrappers.<User>lambdaQuery()
                .like(StringUtils.isNotBlank(request.getName()), User::getName, request.getName())
                .like(StringUtils.isNotBlank(request.getMobile()), User::getMobile, request.getMobile())
                .eq(Objects.nonNull(request.getYn()), User::getYn, request.getYn())
                .eq(Objects.nonNull(request.getAdmin()), User::getAdmin, request.getAdmin())
                .orderByDesc(User::getId);
    }

    @Override
    public List<UserModel> list(UserQuery request) {
        return this.list(buildQueryWrapper(request)).stream()
                .map(userModelTranslator).collect(Collectors.toList());
    }

    @Override
    public boolean changeYn(ChangeStatusParams request, User user) {
        if (CollectionUtils.isNotEmpty(request.getIds())) {
            return this.update(
                    new User()
                            .setYn(request.getStatus())
                            .setModified(LocalDateTime.now())
                            .setModifierId(user.getId())
                            .setModifierName(user.getName()),
                    Wrappers.<User>lambdaQuery()
                            .in(User::getId, request.getIds())
            );

        }
        return false;
    }

    @Override
    public boolean changeAdmin(ChangeStatusParams request, User user) {
        if (CollectionUtils.isNotEmpty(request.getIds())) {
            return this.update(
                    new User()
                            .setAdmin(Objects.equals(YNEnum.YES.getCode(), request.getStatus()))
                            .setModified(LocalDateTime.now())
                            .setModifierId(user.getId())
                            .setModifierName(user.getName()),
                    Wrappers.<User>lambdaQuery()
                            .in(User::getId, request.getIds())
            );

        }
        return false;
    }
}
