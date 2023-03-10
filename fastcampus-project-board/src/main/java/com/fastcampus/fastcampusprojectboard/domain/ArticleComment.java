package com.fastcampus.fastcampusprojectboard.domain;

import java.time.LocalDateTime;

public class ArticleComment {
    private Long id;
    private Long articleId;
    private String content;

    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;
}
