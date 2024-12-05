package com.fastcampus.fastcampusprojectboard.dto;

import com.fastcampus.fastcampusprojectboard.domain.Article;
import com.fastcampus.fastcampusprojectboard.domain.ArticleComment;

import java.time.LocalDateTime;

/**
 * DTO for {@link com.fastcampus.fastcampusprojectboard.domain.ArticleComment}
 */
public record ArticleCommentDto(
        Long id,
        Long articleId,
        UserAccountDto userAccountDto,
        String content,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime updatedAt,
        String updatedBy) {
  public static ArticleCommentDto of(Long id,
                                     Long articleId,
                                     UserAccountDto userAccountDto,
                                     String content,
                                     LocalDateTime createdAt,
                                     String createdBy,
                                     LocalDateTime updatedAt,
                                     String updatedBy) {
    return new ArticleCommentDto(
            id,
            articleId,
            userAccountDto,
            content,
            createdAt,
            createdBy,
            updatedAt,
            updatedBy
    );
  }

  public static ArticleCommentDto from(ArticleComment entity) {
    return new ArticleCommentDto(
            entity.getId(),
            entity.getArticle().getId(),
            UserAccountDto.from(entity.getUserAccount()),
            entity.getContent(),
            entity.getCreatedAt(),
            entity.getCreatedBy(),
            entity.getUpdatedAt(),
            entity.getUpdatedBy()
    );
  }
  public ArticleComment toEntity(Article entity) {
    return ArticleComment.of(
            entity,
            userAccountDto.toEntity(),
            content
    );
  }

}