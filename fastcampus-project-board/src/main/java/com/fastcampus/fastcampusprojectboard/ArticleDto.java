package com.fastcampus.fastcampusprojectboard;

import java.io.Serializable;
import java.time.LocalDateTime;
// 객체 클릭 > jpa design > All new Action > DTO > 필드 다 선택 , Java record
/**
 * DTO for {@link com.fastcampus.fastcampusprojectboard.domain.Article}
 */
//props에 마우스 올리고 option+ enter
// put record component one line > 점 세개 > assign shortcut
//shift +command+k intention(정렬) shortcut설정
public record ArticleDto(
        String title,
        String content,
        String hashtag,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime updatedAt,
        String updatedBy
) implements Serializable {
}