package com.dy.sales.flowers.service;

import com.alibaba.fastjson.JSON;
import com.dy.sales.flowers.vo.response.UserModel;
import com.dy.sales.flowers.utils.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
public class AuthService {


    /**
     * 生成token
     */
    public String generatorToken(UserModel userModel) {
        if (Objects.isNull(userModel)) {
            return null;
        }
        //加入UUID, 保证每次生成的Token都不一样
        userModel.setUuid(UUID.randomUUID().toString());
        return JwtTokenUtil.encode(JSON.toJSONString(userModel));
    }

    /**
     * 解析token
     */
    public UserModel authToken(String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        return JwtTokenUtil.decode(token, UserModel.class);
    }

}
