package com.fastcampus.fastcampusprojectboard.dto;

import com.fastcampus.fastcampusprojectboard.domain.Article;
import com.fastcampus.fastcampusprojectboard.domain.UserAccount;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

import static com.fastcampus.fastcampusprojectboard.domain.QUserAccount.userAccount;

/**
 * DTO (Data Transfer Object) for {@link com.fastcampus.fastcampusprojectboard.domain.Article}.
 *
 * This class is used to transfer data between different layers (e.g., from the service layer to the controller layer)
 * without exposing the actual domain entity `Article`. It improves encapsulation and ensures a clean separation
 * between the domain model and the view model.
 */
public record ArticleDto(
        Long id,
        UserAccountDto userAccountDto,
        String title,
        String content,
        Set<HashtagDto> hashtagDtos,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime updatedAt,
        String updatedBy
) {

  public static ArticleDto of(UserAccountDto userAccountDto, String title, String content, Set<HashtagDto> hashtagDtos) {
    return new ArticleDto(null, userAccountDto, title, content, hashtagDtos, null, null, null, null);
  }

  public static ArticleDto of(Long id, UserAccountDto userAccountDto, String title, String content, Set<HashtagDto> hashtagDtos, LocalDateTime createdAt, String createdBy, LocalDateTime updatedAt, String updatedBy) {
    return new ArticleDto(id, userAccountDto, title, content, hashtagDtos, createdAt, createdBy, updatedAt, updatedBy);
  }

  /**
   * Converts an Article entity to an ArticleDto.
   *
   * This method is used to transform the domain entity `Article` into its corresponding DTO.
   * It ensures the DTO only contains the data necessary for the view or client.
   *
   * @param entity The Article entity to be converted
   * @return An ArticleDto representing the given entity
   */
  public static ArticleDto from(Article entity) {
    return new ArticleDto(
            entity.getId(),
            UserAccountDto.from(entity.getUserAccount()),
            entity.getTitle(),
            entity.getContent(),
            entity.getHashtags().stream()
                    .map(HashtagDto::from)
                    .collect(Collectors.toUnmodifiableSet())
            ,
            entity.getCreatedAt(),
            entity.getCreatedBy(),
            entity.getUpdatedAt(),
            entity.getUpdatedBy()
    );
  }

  /**
   * Converts this DTO back into an Article entity.
   *
   * This method is useful when you need to save or update the entity in the database
   * based on the data received from the client or another layer.
   *
   * @return An Article entity created from this DTO
   */
  public Article toEntity(UserAccount userAccount) {
    return Article.of(
            userAccount,
            title,
            content
    );
  }
}
