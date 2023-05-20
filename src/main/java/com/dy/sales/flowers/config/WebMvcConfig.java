package com.dy.sales.flowers.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.baomidou.mybatisplus.annotation.IEnum;
import com.dy.sales.flowers.config.interceptor.SsoLoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.Assert;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Resource
    private SsoLoginInterceptor ssoLoginInterceptor;
    @Resource
    private RequestLoggerInterceptor requestLoggerInterceptor;
    @Resource
    private UserArgumentResolver userArgumentResolver;


    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(userArgumentResolver);
    }

    @Override
    public void addInterceptors(InterceptorRegistry interceptorRegistry) {
        //登陆
        interceptorRegistry.addInterceptor(ssoLoginInterceptor)
                .order(20)
                .addPathPatterns("/**")
                .excludePathPatterns("/health")
                .excludePathPatterns("/ready")
                .excludePathPatterns("/user/login")
                .excludePathPatterns("/user/register")
        ;
        //请求参数记录日志拦截器
        interceptorRegistry.addInterceptor(requestLoggerInterceptor).addPathPatterns("/**");
    }

    @Bean
    public HttpMessageConverter<String> responseBodyConverter() {
        return new StringHttpMessageConverter(StandardCharsets.UTF_8);
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        //创建fastJson消息转换器
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        //创建配置类
        FastJsonConfig fastJsonConfig = new FastJsonConfig();

        fastJsonConfig.setSerializerFeatures(
                SerializerFeature.DisableCircularReferenceDetect,   //消除对同一对象循环引用的问题，默认为false（如果不配置有可能会进入死循环）
                SerializerFeature.WriteNullStringAsEmpty,           //字符类型字段如果为null,输出为"",而非null
                SerializerFeature.WriteMapNullValue                 //是否输出值为null的字段,默认为false。

        );
        fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");        //日期格式转换
        fastConverter.setFastJsonConfig(fastJsonConfig);
        //配置编码格式，处理中文乱码问题
        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        fastConverter.setSupportedMediaTypes(mediaTypes);
        //将fastjson添加到视图消息转换器列表内
        converters.add(fastConverter);
        converters.add(responseBodyConverter());
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverterFactory(new ConverterFactory<String, IEnum>() {
            @Override
            public <T extends IEnum> Converter<String, T> getConverter(Class<T> targetType) {
                return source -> {
                    Assert.isTrue(targetType.isEnum(), () -> "the class what implements IEnum must be the enum type");
                    return Arrays.stream(targetType.getEnumConstants())
                        .filter(t -> String.valueOf(t.getValue()).equals(source))
                        .findFirst().orElse(null);
                };
            }
        });
    }
}
