package com.fastcampus.fastcampusprojectboard;

import com.fastcampus.fastcampusprojectboard.config.JpaConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
//assertThat 이거 추가해야 뜸.
import static org.assertj.core.api.Assertions.assertThat;
@ActiveProfiles("test")
@SpringBootTest
class FastCampusProjectBoardApplicationTests {

	@Test
	void contextLoads() {
	}

}