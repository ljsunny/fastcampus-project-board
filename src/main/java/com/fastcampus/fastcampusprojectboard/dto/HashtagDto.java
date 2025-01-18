package com.fastcampus.fastcampusprojectboard.dto;

import com.fastcampus.fastcampusprojectboard.domain.Hashtag;

import java.time.LocalDateTime;

public record HashtagDto(
        Long id,
        String hashtagName,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime updatedAt,
        String updatedBy
) {

    public static HashtagDto of(String hashtagName) {
        return new HashtagDto(null, hashtagName, null, null, null, null);
    }

    public static HashtagDto of(Long id, String hashtagName, LocalDateTime createdAt, String createdBy, LocalDateTime updatedAt, String updatedBy) {
        return new HashtagDto(id, hashtagName, createdAt, createdBy, updatedAt, updatedBy);
    }

    public static HashtagDto from(Hashtag entity) {
        return new HashtagDto(
                entity.getId(),
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