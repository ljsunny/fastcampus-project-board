package com.fastcampus.fastcampusprojectboard.domain;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.Objects;

//lombok 에노테이션 사용: Lombok이란 어노테이션 기반으로 코드를 자동완성 해주는 라이브러리이다.
// Lombok을 이용하면 Getter, Setter, Equlas, ToString 등과 다양한 방면의 코드를 자동완성 시킬 수 있다.
//출처: https://mangkyu.tistory.com/78 [MangKyu's Diary:티스토리]

@Getter
@ToString
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
    @Setter @Column(nullable = false,length = 500) private String content; //본문

    //constructor
    protected ArticleComment() {}

    //constructor for setting
    private ArticleComment(Article article, String content) {
        this.article = article;
        this.content = content;
    }
    // public 으로 ArticleComment객체에 article과 content를 넣어줌
    public ArticleComment of(Article article, String content) {
        return new ArticleComment(article, content);
    }

    //cmd+N(Generate) > equals 선택 > id로 equal 여부 확인
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArticleComment that = (ArticleComment) o;
        return id!=null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
