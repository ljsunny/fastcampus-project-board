package com.fastcampus.fastcampusprojectboard;

import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.fastcampus.fastcampusprojectboard.domain.ArticleComment}
 */
@Value
public class ArticleCommentDto implements Serializable {
    Long id;
    String content;
    LocalDateTime createdAt;
    String createdBy;
    LocalDateTime updatedAt;
    String updatedBy;
}