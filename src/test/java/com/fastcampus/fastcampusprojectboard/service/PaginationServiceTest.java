package com.fastcampus.fastcampusprojectboard.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("비즈니스 로직 - 페이지네이션")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = PaginationService.class) // 가볍게 테스트할 떄 유용
public class PaginationServiceTest {
    private final PaginationService paginationService;

    public PaginationServiceTest(@Autowired PaginationService paginationService) {
        this.paginationService = paginationService;
    }

    @DisplayName("현재 페이지 번호와 총 페이지 수를 주면, 페이징 바 리스트를 만들어준다.")
    @MethodSource //파라미터 테스트를 메소드소스 방법으로 진행
    @ParameterizedTest(name="[{index}] 현재페이지: {0}, 총페이지: {1} => {2}") //파라미터 테스트
    void givenCurrentPageNumberAndTotalPages_whenCalculating_thenReturnsPaginationBarNumbers(int currentPageNumber,
                                                                                             int totalPages,
                                                                                             List<Integer> expected) {//검증 받고 싶은값
        //Given

        //When
        List<Integer> actual = paginationService.getPaginationBarNumbers(currentPageNumber, totalPages);
        //Then
        assertThat(actual).isEqualTo(expected);
    }
    static Stream<Arguments> givenCurrentPageNumberAndTotalPages_whenCalculating_thenReturnsPaginationBarNumbers() {//검증 받고 싶은값
        return Stream.of(
                Arguments.arguments(0,13, List.of(0,1,2,3,4)),
                Arguments.arguments(1,13, List.of(0,1,2,3,4)),
                Arguments.arguments(2,13, List.of(0,1,2,3,4)),
                Arguments.arguments(3,13, List.of(1,2,3,4,5)),
                Arguments.arguments(4,13, List.of(2,3,4,5,6)),
                Arguments.arguments(5,13, List.of(3,4,5,6,7)),
                Arguments.arguments(6,13, List.of(4,5,6,7,8)),
                Arguments.arguments(10,13, List.of(8,9,10,11,12)),
                Arguments.arguments(11,13, List.of(9,10,11,12)),
                Arguments.arguments(12,13, List.of(10,11,12))

        );
    }

    @DisplayName("현재 설정되어 있는 페이지네이션 바의 길이를 알려준다")
    @Test
    void givenNothing_whenCalling_thenReturnsCurrentBarLength() {
        //Given

        //When
        int barLength = paginationService.currentBarLength();

        //Then
        assertThat(barLength).isEqualTo(5);
    }
}
