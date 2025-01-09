package com.fastcampus.fastcampusprojectboard.config;

import com.fastcampus.fastcampusprojectboard.domain.UserAccount;
import com.fastcampus.fastcampusprojectboard.repository.UserAccountRepository;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.event.annotation.BeforeTestMethod;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.*;

@Import(SecurityConfig.class)
public class TestSecurityConfig {
    @MockBean private UserAccountRepository userAccountRepository;

    @BeforeTestMethod //인증 테스트를 할때에만 호출, 각종 시큐리티가 필요한 곳에 넣어주면됨
    public void securitySetup() {
        given(userAccountRepository.findById(anyString())).willReturn(Optional.of(UserAccount.of(
                "jsunTest", // 여기에 있는 정보를 @WithUserDetails 에서 읽음
                "pw",
                "jsun-test@email.com",
                "jsun-test",
                "test memo"
        )));

    }
}
