package com.fastcampus.fastcampusprojectboard.repository;

import com.fastcampus.fastcampusprojectboard.domain.ArticleComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // 안붙여도 돌아감
public interface ArticleCommentRepository extends JpaRepository<ArticleComment,Long> {
}