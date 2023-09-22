package com.dy.sales.flowers.service;

import com.alibaba.fastjson.JSON;
import com.dy.sales.flowers.exception.LoginException;
import com.dy.sales.flowers.utils.JwtTokenUtil;
import com.dy.sales.flowers.vo.enums.ResultCode;
import com.dy.sales.flowers.vo.response.UserModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

/**
 * 授权相关
 *
 * @author chao.lan
 */
@Slf4j
@Service
public class AuthService {
    @Value("${jwt.secretKey:i_am_a_developer}")
    private String secretKey;


    /**
     * 生成token
     */
    public String generatorToken(UserModel userModel) {
        if (Objects.isNull(userModel)) {
            return null;
        }
        //加入UUID, 保证每次生成的Token都不一样
        userModel.setUuid(UUID.randomUUID().toString());
        return JwtTokenUtil.encode(secretKey, JSON.toJSONString(userModel));
    }

    /**
     * 解析token
     */
    public UserModel authToken(String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        if (!isMyToken(token)) {
            log.error("token校验失败，非系统签发的令牌 token={}", token);
            throw new LoginException(ResultCode.COOKIE_CHECK_FAILED);
        }
        return JwtTokenUtil.decode(token, UserModel.class);
    }

    /**
     * 验证是否是自己签发的令牌，数据是否被篡改
     *
     * @param token 令牌
     * @return 结果
     */
    public boolean isMyToken(String token) {
        return JwtTokenUtil.isMyToken(secretKey, token);
    }

}
