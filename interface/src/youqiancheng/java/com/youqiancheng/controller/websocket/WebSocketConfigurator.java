package com.youqiancheng.controller.websocket;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName WebSocketConfigurator
 * @Description TODO
 * @Author zzb
 * @Date 2022/2/19 10:17
 * @Version 1.0
 **/
@ConditionalOnWebApplication
@Configuration
public class WebSocketConfigurator {
    @Bean
    public CustomSpringConfigurator customSpringConfigurator() {
        return new CustomSpringConfigurator(); // This is just to get context
    }
}
