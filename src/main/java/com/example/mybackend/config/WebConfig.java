package com.example.mybackend.config;

import com.example.mybackend.common.AuthInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final AuthInterceptor authInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/api/**")           // 拦截所有 /api 开头的
                .excludePathPatterns(                  // 但这些放行
                        "/api/auth/login",             // 登录接口
                        "/api/test/**",                // 测试接口
                        "/swagger-ui/**",              // Swagger 页面
                        "/swagger-ui.html",
                        "/v3/api-docs/**"              // Swagger 接口数据
                );
    }
}
