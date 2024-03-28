package org.sideproject.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {
    // Todo. 나중에 도메인 생기면 그때는 출처 제한하도록

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")//cors를 적용할 URL패턴 정의
                .allowedOriginPatterns("*")//자원 공유 허락할 Origin 허락
                .allowedMethods("*")//허락할 HTTP method 지정
                .allowCredentials(true)
                .allowedHeaders("*");
    }
}