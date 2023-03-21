package com.fastcampus.fastcampusprojectboard.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@EnableJpaAuditing
@Configuration
public class JpaConfig {
    @Bean
    public AuditorAware<String> auditorAware(){ // Artcle에 등록자 자동 등록
        return () -> Optional.of("jsun"); // 스프링 시큐리티로 인증 기능 붙이게 될 때, 수정하자
    }
}
