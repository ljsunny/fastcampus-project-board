package com.fastcampus.fastcampusprojectboard.domain;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

//lombok 에노테이션 사용: Lombok이란 어노테이션 기반으로 코드를 자동완성 해주는 라이브러리이다.
// Lombok을 이용하면 Getter, Setter, Equlas, ToString 등과 다양한 방면의 코드를 자동완성 시킬 수 있다.
//출처: https://mangkyu.tistory.com/78 [MangKyu's Diary:티스토리]

@Getter
@ToString(callSuper = true)
@Table(name = "ArticleComment", indexes = {
        @Index(columnList = "content"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})//jpa buddy기능

//도메인 이름은 단수, 테이블 이름 단수
@Entity // 엔티티 등록
public class ArticleComment extends AuditingFields{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //여러개 - 한개,  optional=false : 필수값, cascade : none=> default
    @Setter @ManyToOne(optional = false) private Article article; //게시글 (ID)

    @Setter
    @ManyToOne(optional = false)
    @JoinColumn(name = "userId")
    private UserAccount userAccount; // 유저 정보 (ID)

    @Setter
    @Column(updatable = false)
    private Long parentCommentId; // 부모 댓글 ID

    @ToString.Exclude
    @OrderBy("createdAt ASC")
    @OneToMany(mappedBy = "parentCommentId", cascade = CascadeType.ALL)
    private Set<ArticleComment> childComments = new LinkedHashSet<>();


    @Setter @Column(nullable = false,length = 500) private String content; //본문

    //constructor
    protected ArticleComment() {}

    //constructor for setting
    private ArticleComment(Article article,
                           UserAccount userAccount,
                           Long parentCommentId,
                           String content) {
        this.article = article;
        this.userAccount = userAccount;
        this.parentCommentId = parentCommentId;
        this.content = content;
    }
    // public 으로 ArticleComment객체에 article과 content를 넣어줌
    public static ArticleComment of(Article article,
                             UserAccount userAccount,
                             String content) {
        return new ArticleComment(article, userAccount, null, content);
    }

    public void addChildComment(ArticleComment child) {
        child.setParentCommentId(this.getId());
        this.getChildComments().add(child);
    }

    //cmd+N(Generate) > equals 선택 > id로 equal 여부 확인
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArticleComment that = (ArticleComment) o;
        return id != null && id.equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
