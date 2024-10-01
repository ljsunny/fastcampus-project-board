package com.fastcampus.fastcampusprojectboard;

import com.fastcampus.fastcampusprojectboard.config.JpaConfig;
import com.fastcampus.fastcampusprojectboard.domain.Article;
import com.fastcampus.fastcampusprojectboard.repository.ArticleCommentRepository;
import com.fastcampus.fastcampusprojectboard.repository.ArticleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.List;
//assertThat 이거 추가해야 뜸.
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("JPA 연결 테스트")
@Import(JpaConfig.class)
@DataJpaTest // 트랜젝션이 걸려있음
class FastcampusProjectBoardApplicationTests {

	private ArticleRepository articleRepository;
	private ArticleCommentRepository articleCommentRepository;

	public FastcampusProjectBoardApplicationTests(
			@Autowired ArticleRepository articleRepository,
			@Autowired ArticleCommentRepository articleCommentRepository) {
		this.articleRepository = articleRepository;
		this.articleCommentRepository = articleCommentRepository;
	}

	@DisplayName("select 테스트")
	@Test
	void givenTestData_whenSelecting_thenWorksFine(){
		//Given
		List<Article> articles = articleRepository.findAll();

		//When

		//Then
	}

	@DisplayName("Insert 테스트")
	@Test
	void givenTestData_whenInserting_thenWorksFine(){
		//Given
		long previousCount= articleRepository.count();
		//When
		Article savedArticle = articleRepository.save(Article.of("new Article","new Content", "#spring"));
		//Then
		assertThat(articleRepository.count()).isEqualTo(previousCount+1);
	}

	@DisplayName("Update 테스트")
	@Test
	void givenTestData_whenUpdate_thenWorksFine(){
		//Given
		Article article = articleRepository.findById(1L).orElseThrow();
		String updatedHashTag = "#springboot";
		article.setHashtag(updatedHashTag);
		//When
		Article savedArticle = articleRepository.saveAndFlush(article);
		//Then
		assertThat(savedArticle).hasFieldOrPropertyWithValue("hashtag",updatedHashTag);
	}

	@DisplayName("Delete 테스트")
	@Test
	void givenTestData_whenDelete_thenWorksFine(){
		//Given
		Article article = articleRepository.findById(1L).orElseThrow();
		long previousArticleCount = articleRepository.count();
		long previousArticleCommentCount = articleCommentRepository.count();
		long deleteCommentSize = article.getArticleComments().size();
		//When
		articleRepository.delete(article);
		//Then
		assertThat(articleRepository.count()).isEqualTo(previousArticleCount-1);
		assertThat(articleCommentRepository.count()).isEqualTo(previousArticleCommentCount-deleteCommentSize);
	}
}
