package com.handongkeji.config.configuration;

import com.handongkeji.config.interceptor.EncodingInterceptor;
import com.handongkeji.config.interceptor.SecurityInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;


/**
　* @Description: web 配置 管理拦截器，将之前的拦截器注入其中
　* @author shalongteng
　* @date 2020/4/3 14:19
　*/
@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Value("${app.filePath}")
    String filePath;

    @Value("${app.pre}")
    String pre;

    @Bean
    public EncodingInterceptor rechargeInterceptor() {
        return new EncodingInterceptor();
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SecurityInterceptor()).addPathPatterns(Arrays.asList("/admin/**")).excludePathPatterns("/admin/login")
                .addPathPatterns(Arrays.asList("/shop/**")).excludePathPatterns("/shop/login")
                .excludePathPatterns("/shop/resetPassword")
                .excludePathPatterns("/shop/sendSms")
                .excludePathPatterns("/shop/loginCode/save")
                .excludePathPatterns("/shop/loginCode/verifySms")
                .excludePathPatterns("/shop/imageCode");
    }
    /**
     * @Author yws
     * @Description //TODO 重写虚拟路径
     * @Date 2019/7/8 0008 17:03
     * @Param
     * @return
     **/
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(pre+"/**").addResourceLocations("file:///"+filePath);
    }
}
