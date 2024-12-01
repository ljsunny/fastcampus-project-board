package com.fastcampus.fastcampusprojectboard.service;

import com.fastcampus.fastcampusprojectboard.domain.Article;
import com.fastcampus.fastcampusprojectboard.domain.type.SearchType;
import com.fastcampus.fastcampusprojectboard.dto.ArticleDto;
import com.fastcampus.fastcampusprojectboard.dto.ArticleUpdateDto;
import com.fastcampus.fastcampusprojectboard.repository.ArticleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

@DisplayName("비즈니스 로직 - 게시글")
@ExtendWith(MockitoExtension.class)
public class ArticleServiceTest {

    @InjectMocks
    ArticleService articleService;
    @Mock
    private ArticleRepository articleRepository;

//    각 게시글 페이지로 이동
//    페이지 네이션
//    홈버튼 -> 게시판 페이지로 리다이렉션
//    정렬
    @DisplayName("게시글을 검색하면, 게시글 리스트를 반환한다.")
    @Test
    void givenSearchParameters_whenSearchingArticles_thenReturnsArticleList(){
        //GIven

        //When
        Page<ArticleDto> articles = articleService.searchArticles(SearchType.TITLE, "search keyword"); // 제목, 본문, ID, 닉네임, 해시테그

        //Then
        assertThat(articles).isNotNull();
    }

    @DisplayName("게시글을 검색하면, 게시글을 반환한다.")
    @Test
    void givenArticleId_whenSearchingArticles_thenReturnsArticleList(){
        //GIven

        //When
        ArticleDto articles = articleService.searchArticle(1L);

        //Then
        assertThat(articles).isNotNull();
    }

    @DisplayName("게시글 정보를 입력하면, 게시글을 생성한다")
    @Test
    void givenArticleInfo_whenSavingArticle_thenSavesArticle(){
        //GIven
        given(articleRepository.save(any(Article.class))).willReturn(null);
        //When
        articleService.saveArticle(ArticleDto.of(LocalDateTime.now(), "Uno","title","content","hashtag"));
        //sociable 테스트 : 여러단계의 레이어를 거쳐서 테스트

        //Then
        then(articleRepository).should().save(any(Article.class));
    }

    @DisplayName("게시글 id와 수정 정보를 입력하면, 게시글을 수정한다")
    @Test
    void givenArticleIdandModifiedInfo_whenUpdatingArticle_thenUpdatesArticle(){
        //GIven
        given(articleRepository.save(any(Article.class))).willReturn(null);
        //When
        articleService.updateArticle(1L, ArticleUpdateDto.of("title","content","#java"));
        //sociable 테스트 : 여러단계의 레이어를 거쳐서 테스트

        //Then
        then(articleRepository).should().save(any(Article.class));
    }

    @DisplayName("게시글 id를 입력하면, 게시글을 삭제한다")
    @Test
    void givenArticleId_whenDeletingArticle_thenDeletesArticle(){
        //GIven
        willDoNothing().given(articleRepository).delete(any(Article.class));
        //When
        articleService.deleteArticle(1L);
        //sociable 테스트 : 여러단계의 레이어를 거쳐서 테스트

        //Then
        then(articleRepository).should().delete(any(Article.class));
    }
}
