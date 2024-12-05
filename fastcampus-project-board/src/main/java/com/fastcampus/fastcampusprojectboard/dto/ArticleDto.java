package com.fastcampus.fastcampusprojectboard.dto;

import com.fastcampus.fastcampusprojectboard.domain.Article;

import java.time.LocalDateTime;

/**
 * DTO (Data Transfer Object) for {@link com.fastcampus.fastcampusprojectboard.domain.Article}.
 *
 * This class is used to transfer data between different layers (e.g., from the service layer to the controller layer)
 * without exposing the actual domain entity `Article`. It improves encapsulation and ensures a clean separation
 * between the domain model and the view model.
 */
public record ArticleDto(
        Long id,                           // The unique identifier for the article
        UserAccountDto userAccountDto,     // User account details associated with the article
        String title,                      // Title of the article
        String content,                    // Content/body of the article
        String hashtag,                    // Hashtag(s) associated with the article
        LocalDateTime createdAt,           // Timestamp of when the article was created
        String createdBy,                  // Username or ID of the user who created the article
        LocalDateTime updatedAt,           // Timestamp of the last update to the article
        String updatedBy                   // Username or ID of the user who last updated the article
) {
  /**
   * Factory method to create an instance of ArticleDto with all fields.
   *
   * @param id             The unique identifier for the article
   * @param userAccountDto The DTO for the associated user account
   * @param title          The title of the article
   * @param content        The content of the article
   * @param hashtag        Hashtag(s) for the article
   * @param createdAt      The timestamp of article creation
   * @param createdBy      The username of the creator
   * @param updatedAt      The timestamp of the last update
   * @param updatedBy      The username of the last updater
   * @return A new instance of ArticleDto
   */
  public static ArticleDto of(Long id,
                              UserAccountDto userAccountDto,
                              String title,
                              String content,
                              String hashtag,
                              LocalDateTime createdAt,
                              String createdBy,
                              LocalDateTime updatedAt,
                              String updatedBy) {
    return new ArticleDto(
            id,
            userAccountDto,
            title,
            content,
            hashtag,
            createdAt,
            createdBy,
            updatedAt,
            updatedBy
    );
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
            UserAccountDto.from(entity.getUserAccount()), // Converts the associated UserAccount entity to its DTO
            entity.getTitle(),
            entity.getContent(),
            entity.getHashtag(),
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
  public Article toEntity() {
    return Article.of(
            userAccountDto.toEntity(), // Converts the associated UserAccountDto back into its entity form
            title,
            content,
            hashtag
    );
  }
}
