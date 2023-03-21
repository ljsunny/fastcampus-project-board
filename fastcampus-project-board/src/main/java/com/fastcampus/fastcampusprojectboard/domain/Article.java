package com.fastcampus.fastcampusprojectboard.domain;

<<<<<<< HEAD
import java.time.LocalDateTime;

public class Article {
    private Long id;
    private String title;
    private String content;
    private String hashtag;

    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;
=======
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@ToString
@Table(indexes = {
        @Index(columnList = "title"),
        @Index(columnList = "hashtag"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
@Entity
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // 유저가 접근하지 못하도록 특정 항목만 setter줌
    @Setter @Column(nullable = false) private String title;
    @Setter @Column(nullable = false, length = 10000) private String content;
    @Setter private String hashtag;

    //순환참조 끊어줌.
    @ToString.Exclude
    //양방향 데이터, cascading: 댓글들 다 같이 삭제. 에디팅이 어려워서 삭제하는 경우도 있음
    @OrderBy("id")
    @OneToMany(mappedBy = "article", cascade =CascadeType.ALL ) private final Set<ArticleComment> articleComments = new LinkedHashSet<>();

    //jpa oditting
    @CreatedDate @Column(nullable = false) private LocalDateTime createdAt;
    @CreatedBy @Column(nullable = false, length = 100) private String createdBy;
    @LastModifiedDate @Column(nullable = false) private LocalDateTime updatedAt;
    @LastModifiedBy @Column(nullable = false,length = 100) private String updatedBy;

    protected Article() {}

    private Article(String title, String content, String hashtag) {
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
    }

    public Article of(String title, String content, String hashtag) {
        return new Article(title, content,hashtag);
    }

    //리스트 넣거나 , 중복요소 제거, 동일성 동등성 검사 EqualsHashcode: 모든걸 비교하는 방법<= 이거 안씀
    //pattern-matching
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return id!=null && id.equals(article.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
>>>>>>> #11-db
}
