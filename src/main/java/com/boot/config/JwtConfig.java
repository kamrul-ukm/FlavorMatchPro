package com.boot.config;

import com.boot.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class JwtConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                //添加拦截路径
                .addPathPatterns("/**")
                //添加放行路径
                .excludePathPatterns("/login/login", "/login/register", "/file/*",
                        "/alipay/notify", "/alipay/pay", "/alipay/pay2");
    }
}

