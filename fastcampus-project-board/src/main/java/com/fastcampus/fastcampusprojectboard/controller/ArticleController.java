package com.fastcampus.fastcampusprojectboard.controller;

import com.fastcampus.fastcampusprojectboard.domain.type.SearchType;
import com.fastcampus.fastcampusprojectboard.dto.ArticleWithCommentsDto;
import com.fastcampus.fastcampusprojectboard.dto.response.ArticleWithCommentsResponse;
import com.fastcampus.fastcampusprojectboard.service.ArticleService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

    @RequestMapping("/articles")
    @Controller
    public class ArticleController {
        private final ArticleService articleService;

        public ArticleController(ArticleService articleService) {
            this.articleService = articleService;
        }

        @GetMapping
        public String articles(
                @RequestParam(required = false) SearchType searchType, //required =false : 필수아님
                @RequestParam(required = false) String searchValue,
                @PageableDefault(size=10, sort="createdAt", direction = Sort.Direction.DESC) Pageable pageable, //한페이지에 10개씩, createAt으로 정렬
                ModelMap map) {
            map.addAttribute("articles", articleService.searchArticles(searchType,searchValue,pageable));
            return "articles/index";
        }

        @GetMapping("/{articleId}")
        public String article(ModelMap map, @PathVariable("articleId") Long articleId) {
            ArticleWithCommentsResponse article = ArticleWithCommentsResponse.from(articleService.getArticle(articleId));
            map.addAttribute("article", article);
            map.addAttribute("articleComments", article.articleCommentsResponse());

            return "articles/detail";
        }
}
