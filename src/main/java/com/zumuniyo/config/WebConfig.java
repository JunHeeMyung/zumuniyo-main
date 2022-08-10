package com.zumuniyo.config;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOrigins("http://localhost:3000")
            .allowedMethods("OPTIONS", "GET", "POST", "PUT", "DELETE");
    }
    
    @PostConstruct
    public void start() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
    }
}