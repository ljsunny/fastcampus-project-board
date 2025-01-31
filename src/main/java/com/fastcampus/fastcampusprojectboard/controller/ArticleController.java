package com.fastcampus.fastcampusprojectboard.controller;

import com.fastcampus.fastcampusprojectboard.domain.constant.FormStatus;
import com.fastcampus.fastcampusprojectboard.domain.type.SearchType;
import com.fastcampus.fastcampusprojectboard.dto.UserAccountDto;
import com.fastcampus.fastcampusprojectboard.dto.request.ArticleRequest;
import com.fastcampus.fastcampusprojectboard.dto.response.ArticleResponse;
import com.fastcampus.fastcampusprojectboard.dto.response.ArticleWithCommentsResponse;
import com.fastcampus.fastcampusprojectboard.dto.security.BoardPrincipal;
import com.fastcampus.fastcampusprojectboard.service.ArticleService;
import com.fastcampus.fastcampusprojectboard.service.PaginationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

    @RequiredArgsConstructor
    @RequestMapping("/articles")
    @Controller
    public class ArticleController {
        private final ArticleService articleService;
        private final PaginationService paginationService;

//        public ArticleController(ArticleService articleService, PaginationService paginationService) {
//            this.articleService = articleService;
//            this.paginationService = paginationService;
//        } @RequiredArgsConstructor 삽입시 auto import

        @GetMapping
        public String articles(
                @RequestParam(required = false) SearchType searchType, //required =false : 필수아님
                @RequestParam(required = false) String searchValue,
                @PageableDefault(size=10, sort="createdAt", direction = Sort.Direction.DESC) Pageable pageable, //한페이지에 10개씩, createAt으로 정렬
                ModelMap map) {
            Page<ArticleResponse> articles = articleService.searchArticles(searchType,searchValue,pageable).map(ArticleResponse::from);
            List<Integer> barNumbers = paginationService.getPaginationBarNumbers(pageable.getPageNumber(), articles.getTotalPages());

            map.addAttribute("articles", articles);
            map.addAttribute("paginationBarNumbers", barNumbers);
            map.addAttribute("searchTypes", SearchType.values());
            map.addAttribute("searchTypeHashtag", SearchType.HASHTAG);
            return "articles/index";
        }

        @GetMapping("/{articleId}")
        public String article(ModelMap map, @PathVariable("articleId") Long articleId) {
            ArticleWithCommentsResponse article = ArticleWithCommentsResponse.from(articleService.getArticleWithComments(articleId));
            map.addAttribute("article", article);
            map.addAttribute("articleComments", article.articleCommentsResponse());
            map.addAttribute("totalCount", articleService.getArticleCount());
            map.addAttribute("searchTypeHashtag", SearchType.HASHTAG);

            return "articles/detail";
        }

        @GetMapping("/search-hashtag")
        public String searchArticleHashtag(
                @RequestParam(required = false) String searchValue,
                @PageableDefault(size=10, sort="createdAt", direction = Sort.Direction.DESC) Pageable pageable, //한페이지에 10개씩, createAt으로 정렬
                ModelMap map) {

            Page<ArticleResponse> articles = articleService.searchArticlesViaHashtag(searchValue,pageable).map(ArticleResponse::from);
            List<Integer> barNumbers = paginationService.getPaginationBarNumbers(pageable.getPageNumber(), articles.getTotalPages());
            List<String> hashtags = articleService.getHashtags();
            map.addAttribute("articles", articles);
            map.addAttribute("hashtags",hashtags);
            map.addAttribute("paginationBarNumbers", barNumbers);
            map.addAttribute("searchType", SearchType.HASHTAG);

            return "articles/search-hashtag";
        }

        @GetMapping("/form")
        public String articleForm(ModelMap map) {
            map.addAttribute("formStatus", FormStatus.CREATE);

            return "articles/form";
        }

        @PostMapping("/form")
        public String postNewArticle(ArticleRequest articleRequest,
                                     @AuthenticationPrincipal BoardPrincipal boardPrincipal) {
            articleService.saveArticle(articleRequest.toDto(boardPrincipal.toDto()));

            return "redirect:/articles";
        }

        @GetMapping("/{articleId}/form")
        public String updateArticleForm(@PathVariable Long articleId, ModelMap map) {
            ArticleResponse article = ArticleResponse.from(articleService.getArticle(articleId));

            map.addAttribute("article", article);
            map.addAttribute("formStatus", FormStatus.UPDATE);

            return "articles/form";
        }

        @PostMapping ("/{articleId}/form")
        public String updateArticle(
                @PathVariable Long articleId,
                @AuthenticationPrincipal BoardPrincipal boardPrincipal,
                ArticleRequest articleRequest
        ) {

            articleService.updateArticle(articleId, articleRequest.toDto(boardPrincipal.toDto())); //"jsun", "asdf1234", "jsun@mail.com", "jsun", "memo"

            return "redirect:/articles/" + articleId;
        }

        @PostMapping ("/{articleId}/delete")
        public String deleteArticle(
                @PathVariable Long articleId,
                @AuthenticationPrincipal BoardPrincipal boardPrincipal
        ) {
            //SecurityContextHolder.getContext().setAuthentication(null); old version
            articleService.deleteArticle(articleId, boardPrincipal.getUsername());

            return "redirect:/articles";
        }

    }
