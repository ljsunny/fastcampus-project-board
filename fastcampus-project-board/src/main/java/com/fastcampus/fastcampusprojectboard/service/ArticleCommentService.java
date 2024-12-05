package com.fastcampus.fastcampusprojectboard.service;

import com.fastcampus.fastcampusprojectboard.dto.ArticleCommentDto;
import com.fastcampus.fastcampusprojectboard.repository.ArticleCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class ArticleCommentService {

    private final ArticleCommentRepository articleCommentRepository;

    public List<ArticleCommentDto> searchArticleComments(Long articleId) {
        return List.of();
    }

    @Transactional(readOnly = true)
    public List<ArticleCommentDto> getComments(long articleId) {
        return List.of();
    }

    public void saveArticleComment(ArticleCommentDto dto) {
    }

    public void updateArticleComment(ArticleCommentDto dto) {
    }

    public void deleteArticleComment(Long articleCommentId) {
    }
}
