package com.fastcampus.fastcampusprojectboard.controller;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Disabled("except Spring Data Rest Test")
@DisplayName("Data REST TEST - API TEST")
@Transactional //rollback - DB 부하를 줄임
@AutoConfigureMockMvc
@SpringBootTest
public class DataRestTest {

    private final MockMvc mvc;

    public DataRestTest(@Autowired MockMvc mvc) {
        this.mvc = mvc;
    }

    @DisplayName("[api] Show Board List")
    @Test
    void givenNoting_whenRequestingArticles_thenReturnsArticlesJsonResponse() throws Exception {

        //given

        //when & then
        mvc.perform(get("/api/articles")) // ctrl + enter 두번 => MockMvcRequestBuilders.get() 선택 => option + enter => statically import
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("application/hal+json"))); //json 형식이랑 다름
//                .andDo(print());
    }

    @DisplayName("[api] Show Single Board List")
    @Test
    void givenNoting_whenRequestingSingleArticles_thenReturnsArticlesJsonResponse() throws Exception {
        //given

        //when & then
        mvc.perform(get("/api/articles/1")) // ctrl + enter 두번 => MockMvcRequestBuilders.get() 선택 => option + enter => statically import
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("application/hal+json"))); //json 형식이랑 다름
//                .andDo(print());
    }
    @DisplayName("[api] Show Board Comments List")
    @Test
    void givenNoting_whenRequestingArticleComments_thenReturnsArticleCommentsJsonResponse() throws Exception {
        //given

        //when & then
        mvc.perform(get("/api/articleComments")) // ctrl + enter 두번 => MockMvcRequestBuilders.get() 선택 => option + enter => statically import
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("application/hal+json"))); //json 형식이랑 다름
//                .andDo(print());
    }

    @DisplayName("[api] Show Single Board Comments")
    @Test
    void givenNoting_whenRequestingArticleComment_thenReturnsArticleCommentJsonResponse() throws Exception {
        //given

        //when & then
        mvc.perform(get("/api/articleComments/1")) // ctrl + enter 두번 => MockMvcRequestBuilders.get() 선택 => option + enter => statically import
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("application/hal+json"))); //json 형식이랑 다름
//                .andDo(print());
    }

    @DisplayName("[api] 회원 관련 API 는 일체 제공하지 않는다.")
    @Test
    void givenNothing_whenRequestingUserAccounts_thenThrowsException() throws Exception {
        // Given
        // When & Then
        mvc.perform(get("/api/userAccounts")).andExpect(status().isNotFound());
        mvc.perform(post("/api/userAccounts")).andExpect(status().isNotFound());
        mvc.perform(put("/api/userAccounts")).andExpect(status().isNotFound());
        mvc.perform(patch("/api/userAccounts")).andExpect(status().isNotFound());
        mvc.perform(delete("/api/userAccounts")).andExpect(status().isNotFound());
        mvc.perform(head("/api/userAccounts")).andExpect(status().isNotFound());
    }

}
