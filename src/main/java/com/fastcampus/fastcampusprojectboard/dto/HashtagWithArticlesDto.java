package com.fastcampus.fastcampusprojectboard.dto;


import com.fastcampus.fastcampusprojectboard.domain.Hashtag;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

public record HashtagWithArticlesDto(
        Long id,
        Set<ArticleDto> articles,
        String hashtagName,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime updatedAt,
        String updatedBy
) {

    public static HashtagWithArticlesDto of(Set<ArticleDto> articles, String hashtagName) {
        return new HashtagWithArticlesDto(null, articles, hashtagName, null, null, null, null);
    }

    public static HashtagWithArticlesDto of(Long id, Set<ArticleDto> articles, String hashtagName, LocalDateTime createdAt, String createdBy, LocalDateTime updatedAt, String updatedBy) {
        return new HashtagWithArticlesDto(id, articles, hashtagName, createdAt, createdBy, updatedAt, updatedBy);
    }

    public static HashtagWithArticlesDto from(Hashtag entity) {
        return new HashtagWithArticlesDto(
                entity.getId(),
                entity.getArticles().stream()
                        .map(ArticleDto::from)
                        .collect(Collectors.toUnmodifiableSet())
                ,
                entity.getHashtagName(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getUpdatedAt(),
                entity.getUpdatedBy()
        );
    }

    public Hashtag toEntity() {
        return Hashtag.of(hashtagName);
    }

}