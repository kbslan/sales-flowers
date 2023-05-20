package com.dy.sales.flowers.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class LoginConfig {

    /**
     * 登陆IP检查
     */
    @Value("${spring.sso.ipCheck:false}")
    private boolean ipCheck;

    /**
     * agent检查
     */
    @Value("${spring.sso.agentCheck:false}")
    private boolean agentCheck;

    /**
     * cookie续期
     */
    @Value("${spring.sso.renewal:true}")
    private boolean renewal;
}
