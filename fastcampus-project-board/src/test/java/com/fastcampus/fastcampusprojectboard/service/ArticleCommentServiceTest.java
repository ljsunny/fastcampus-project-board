package com.fastcampus.fastcampusprojectboard.service;
import com.fastcampus.fastcampusprojectboard.domain.Article;
import com.fastcampus.fastcampusprojectboard.domain.ArticleComment;
import com.fastcampus.fastcampusprojectboard.dto.ArticleCommentDto;
import com.fastcampus.fastcampusprojectboard.repository.ArticleCommentRepository;
import com.fastcampus.fastcampusprojectboard.repository.ArticleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

@DisplayName("비즈니스 로직 - 게시글 댓글")
@ExtendWith(MockitoExtension.class)
public class ArticleCommentServiceTest {
    @InjectMocks
    ArticleCommentService articleCommentService;
    @Mock
    private ArticleCommentRepository articleCommentRepository;
    private ArticleRepository articleRepository;
    //댓글 리스트 조회
    //댓글 입력
    //댓글 수정
    //댓글 삭제

    @DisplayName("게시글 ID를 입력하면, 댓글 리스트 반환")
    @Test
    void givenArticleId_whenSearchingArticleComments_thenReturnArticleComments(){
        //Given
        long articleId =1L;
        given(articleRepository.findById(articleId)).willReturn(Optional.of(
                Article.of("title","content","#java")
        ));
        //When
        List<ArticleCommentDto> articleComments = articleCommentService.getComments(articleId);

        //Then
        assertThat(articleComments).isNotNull();
        then(articleRepository).should().findById(articleId);
    }

    //to jisun: 여기아래 saving이랑 updating test case 이어서 작성해야되!!!
    @DisplayName("게시글 ID를 입력하면, 댓글 입력한다")
    @Test
    void givenArticleId_whenSavingArticleComments_thenSavesArticleComment(){
        //Given
        given(articleCommentRepository.save(any(ArticleComment.class))).willReturn(null);
        //When
        articleCommentService.saveArticleComment(1L, ArticleCommentDto.of(LocalDateTime.now(), "Jisun","content"));

        //Then
        then(articleCommentRepository).should().save(any(ArticleComment.class));
    }

    @DisplayName("댓글 ID와 수정 정보를 입력하면, 게시글 댓글을 수정한다.")
    @Test
    void givenArticleCommentIdandInfo_whenUpdatingArticleComments_thenUpdatesArticleComment(){
        //Given
        given(articleCommentRepository.save(any(ArticleComment.class))).willReturn(null);
        //When
        articleCommentService.updateArticle(1L,"newContent");

        //Then
        then(articleCommentRepository).should().save(any(ArticleComment.class));
    }

    @DisplayName("댓글 ID를 입력하면, 게시글 댓글을 삭제한다.")
    @Test
    void givenArticleCommentId_whenDeletingArticleComments_thenDeletesArticleComment(){
        //Given
        willDoNothing().given(articleCommentRepository).delete(any(ArticleComment.class));
        //When
        articleCommentService.deleteArticle(1L);

        //Then
        then(articleCommentRepository).should().delete(any(ArticleComment.class));
    }
}
