package com.fastcampus.fastcampusprojectboard.config;

import com.fastcampus.fastcampusprojectboard.dto.UserAccountDto;
import com.fastcampus.fastcampusprojectboard.dto.security.BoardPrincipal;
import com.fastcampus.fastcampusprojectboard.repository.UserAccountRepository;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
//                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll()) // 모든 경로 허용
                .authorizeHttpRequests(auth -> auth
                                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll() //정적파일
                                .requestMatchers( // antMatcher == mvcMathers == requestMatcher(security6부터 대체됨)
                                    HttpMethod.GET,
                                    "/",
                                    "/articles",
                                    "/articles/search-hashtag"
                                ).permitAll().anyRequest().authenticated()
                )
                .formLogin(config -> config
                        .loginPage("/login")
                        .defaultSuccessUrl("/")
                        .permitAll()
                )
                .logout(config -> config
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                )
                .build();
//                .formLogin(Customizer.withDefaults()); // 새로운 방식으로 formLogin 구성

    }

    @Bean
    public UserDetailsService userDetailsService(UserAccountRepository userAccountRepository) {
        return username -> userAccountRepository
                .findById(username)
                .map(UserAccountDto::from)
                .map(BoardPrincipal::from)
                .orElseThrow(() -> new UsernameNotFoundException("유저를 찾을 수 없습니다 - username: " + username));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder(); // 패스워드를 인코딩
    }
}
