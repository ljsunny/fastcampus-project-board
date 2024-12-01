package com.fastcampus.fastcampusprojectboard.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.fastcampus.fastcampusprojectboard.domain.Article}
 */
public record ArticleDto(
        LocalDateTime createdAt,//shift+command+k element 정렬
        String createdBy,
        String title,
        String content,
        String hashtag
) {
  //command + n -> constructor 생성

  public static ArticleDto of(LocalDateTime createdAt, String createdBy, String title, String content, String hashtag) {
    return new ArticleDto(createdAt, createdBy, title, content, hashtag);
  }
}