package com.fastcampus.fastcampusprojectboard;

import com.fastcampus.fastcampusprojectboard.config.JpaConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;

import java.util.List;
//assertThat 이거 추가해야 뜸.
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("JPA 연결 테스트")
@Import(JpaConfig.class)
class FastCampusProjectBoardApplicationTests {

	@Test
	void contextLoads() {
	}

}