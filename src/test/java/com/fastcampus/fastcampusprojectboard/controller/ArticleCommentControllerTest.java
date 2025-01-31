package com.fastcampus.fastcampusprojectboard.controller;

import com.fastcampus.fastcampusprojectboard.config.TestSecurityConfig;
import com.fastcampus.fastcampusprojectboard.dto.ArticleCommentDto;
import com.fastcampus.fastcampusprojectboard.dto.request.ArticleCommentRequest;
import com.fastcampus.fastcampusprojectboard.service.ArticleCommentService;
import com.fastcampus.fastcampusprojectboard.util.FormDataEncoder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// shift + cmd + T : 테스트 생성
@DisplayName("View 컨트롤러 - 댓글")
@Import({TestSecurityConfig.class, FormDataEncoder.class})
@WebMvcTest(ArticleCommentController.class) // 해당 컨트롤러만 읽어들임
class ArticleCommentControllerTest {

    private final MockMvc mvc;
    private final FormDataEncoder formDataEncoder;

    @MockBean private ArticleCommentService articleCommentService;

    public ArticleCommentControllerTest(@Autowired MockMvc mvc,
                                        @Autowired FormDataEncoder formDataEncoder) {
        this.mvc = mvc;
        this.formDataEncoder = formDataEncoder;
    }

    @WithUserDetails(value = "jsunTest", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @DisplayName("[view][POST] 댓글 등록 - 정상 호출")
    @Test
    void givenNewArticleCommentInfo_whenRequesting_thenSavesNewArticleComment() throws Exception {
        // Given
        long articleId = 1L;
        ArticleCommentRequest articleRequest = ArticleCommentRequest.of(articleId,"test comment");
        willDoNothing().given(articleCommentService).saveArticleComment(any(ArticleCommentDto.class));

        // When & Then
        mvc.perform(
                        post("/comments/new")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .content(formDataEncoder.encode(articleRequest))
                                .with(csrf())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/articles/"+articleId))
                .andExpect(redirectedUrl("/articles/"+articleId));
        then(articleCommentService).should().saveArticleComment(any(ArticleCommentDto.class));
    }

    @WithUserDetails(value = "jsunTest", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @DisplayName("[view][POST] 게시글 삭제 - 정상 호출")
    @Test
    void givenArticleCommentIdToDelete_whenRequesting_thenDeletesArticleComment() throws Exception {
        // Given
        long articleId = 1L;
        long articleCommentId = 1L;
        String userId = "jsunTest";
        willDoNothing().given(articleCommentService).deleteArticleComment(articleCommentId, userId);

        // When & Then
        mvc.perform(
                        post("/comments/" + articleCommentId + "/delete")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .content(formDataEncoder.encode(Map.of("articleId", articleId))) // articleId 포함
                                .with(csrf())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/articles/" + articleId)) // 리디렉션 URL 확인
                .andExpect(redirectedUrl("/articles/" + articleId));

        then(articleCommentService).should().deleteArticleComment(articleCommentId, userId);
    }

    @WithUserDetails(value = "jsunTest", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @DisplayName("[view][POST] 대댓글 등록 - 정상 호출")
    @Test
    void givenArticleCommentInfoWithParentCommentId_whenRequesting_thenSavesNewChildComment() throws Exception {
        // Given
        long articleId = 1L;
        ArticleCommentRequest request = ArticleCommentRequest.of(articleId, 1L, "test comment");
        willDoNothing().given(articleCommentService).saveArticleComment(any(ArticleCommentDto.class));
        // When & Then
        mvc.perform(
                        post("/comments/new")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .content(formDataEncoder.encode(request))
                                .with(csrf())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/articles/" + articleId))
                .andExpect(redirectedUrl("/articles/" + articleId));
        then(articleCommentService).should().saveArticleComment(any(ArticleCommentDto.class));
    }
}