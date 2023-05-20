package com.dy.sales.flowers.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusProperties;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.core.toolkit.GlobalConfigUtils;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.LocalDateTimeTypeHandler;
import org.apache.ibatis.type.LocalDateTypeHandler;
import org.apache.ibatis.type.LocalTimeTypeHandler;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Slf4j
@Configuration
@EnableTransactionManagement
@MapperScan("com.dy.sales.flowers.mapper")
public class MybatisPlusConfig {

//
//    public MybatisPlusConfig(MybatisPlusProperties mybatisPlusProperties) {
//        log.info(" mybatisPlusProperties : {}", mybatisPlusProperties);
//        MybatisConfiguration configuration = mybatisPlusProperties.getConfiguration();
//        GlobalConfig globalConfig = mybatisPlusProperties.getGlobalConfig();
//        GlobalConfigUtils.setGlobalConfig(configuration, globalConfig);
//        configuration.getTypeHandlerRegistry().register(LocalDateTime.class, new LocalDateTimeTypeHandler());
//        configuration.getTypeHandlerRegistry().register(LocalDate.class, new LocalDateTypeHandler());
//        configuration.getTypeHandlerRegistry().register(LocalTime.class, new LocalTimeTypeHandler());
//        GlobalConfigUtils.setGlobalConfig(configuration, globalConfig);
//    }

    /**
     * 分页插件
     */
    @Bean
    public MybatisPlusInterceptor paginationInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }
    
}
