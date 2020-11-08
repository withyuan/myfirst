package com.edu.config;

import com.edu.interceptor.LoginInterceptor;
import com.edu.interceptor.UserInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class LoginConfig implements WebMvcConfigurer {
    @Autowired
    LoginInterceptor loginInterceptor;
    @Autowired

    UserInterceptor userInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/manage/**").excludePathPatterns("/manage/login/**","/manage/upload");


        registry.addInterceptor(userInterceptor)
                .addPathPatterns("/cart/**","/order/**","/shipping/**","/user/**","/collect/**")
                .excludePathPatterns("/user/register.do","/user/login/**","/user/history","/user/forget_get_question/**"
                ,"/user/forget_check_answer.do","/user/forget_reset_password.do","/user/sendMessage","/user/check/vsCode",
                        "/user/logout.do","/order/callback.do");
    }

}
