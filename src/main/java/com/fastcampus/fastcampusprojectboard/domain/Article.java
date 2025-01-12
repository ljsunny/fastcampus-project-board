package com.fastcampus.fastcampusprojectboard.domain;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@ToString(callSuper = true)
@Table(indexes = {
        @Index(columnList = "title"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})

@Entity
public class Article extends AuditingFields{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Setter
    @ManyToOne(optional = false)
    @JoinColumn(name = "userId")
    private UserAccount userAccount; // 유저정보(ID)
    // 유저가 접근하지 못하도록 특정 항목만 setter줌
    @Setter @Column(nullable = false) private String title; //제목
    @Setter @Column(nullable = false, length = 1000) private String content; //내용
    @ToString.Exclude
    @JoinTable(
            name = "article_hashtag",
            joinColumns = @JoinColumn(name = "articleId"),
            inverseJoinColumns = @JoinColumn(name = "hashtagId")
    )// article - hashtag relationship table
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Hashtag> hashtags = new LinkedHashSet<>();

    //순환참조 끊어줌. Article <-> ArticelComment 둘다 참조하고 있어서. 보통 Article 쪽에서 끊어줌
    @ToString.Exclude
    //양방향 데이터, cascading: 댓글들 다 같이 삭제. 에디팅이 어려워서 삭제하는 경우도 있음
    //실무에서는 양방향 데이터 잘 안씀. 한쪽에서만 써주자
    @OrderBy("createdAt DESC")
    @OneToMany(mappedBy = "article", cascade =CascadeType.ALL ) private final Set<ArticleComment> articleComments = new LinkedHashSet<>();

    protected Article() {}

    private Article(
                    UserAccount userAccount,
                    String title,
                    String content) {
        this.userAccount = userAccount;
        this.title = title;
        this.content = content;
    }

    public static Article of(UserAccount userAccount, String title, String content) {
        return new Article(userAccount, title, content);
    }

    public void addHashtag(Hashtag hashtag) {
        this.getHashtags().add(hashtag);
    }

    public void addHashtags(Collection<Hashtag> hashtags) {
        this.getHashtags().addAll(hashtags);
    }

    public void clearHashtags() {
        this.getHashtags().clear();
    }

    //리스트 넣거나 , 중복요소 제거, 동일성 동등성 검사 EqualsHashcode: 모든걸 비교하는 방법<= 이거 안씀
    //pattern-matching
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Article that)) return false;
        return id != null && id.equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
