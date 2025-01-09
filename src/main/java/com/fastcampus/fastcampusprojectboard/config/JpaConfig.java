package com.fastcampus.fastcampusprojectboard.config;

import com.fastcampus.fastcampusprojectboard.dto.security.BoardPrincipal;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@EnableJpaAuditing // JPA Auditing 활성화. Entity에 생성자 및 수정자 자동 기록 가능
@Configuration // Spring Configuration 클래스임을 나타냄
public class JpaConfig {

    /**
     * AuditorAware 빈 등록
     * - JPA Auditing에서 자동으로 작성자 정보를 저장하기 위해 사용됨.
     * - Article 등 엔티티에서 생성자나 수정자 필드를 자동으로 채움.
     */
    @Bean
    public AuditorAware<String> auditorAware() { // Article의 등록자 또는 수정자를 자동으로 기록
        return () -> Optional.ofNullable(SecurityContextHolder.getContext()) // 현재 SecurityContext 가져오기
                .map(SecurityContext::getAuthentication) // Authentication 객체 가져오기
                .filter(Authentication::isAuthenticated) // 인증된 사용자만 처리
                .map(Authentication::getPrincipal) // 인증 정보의 주체(principal) 가져오기
                .map(BoardPrincipal.class::cast) // Principal을 BoardPrincipal 타입으로 캐스팅
                .map(BoardPrincipal::getUsername); // 사용자 이름(username)을 추출
    }
}
