package com.orange.article.controller.v1;

import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orange.article.service.ApArticleService;
import com.orange.model.article.dto.ArticleHomeDTO;
import com.orange.model.article.pojo.ApArticle;
import com.orange.model.common.constant.ArticleConstant;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/article/")
public class ArticleHomeController {

    private final ApArticleService articleService;

    @PostMapping("/load")
    public List<ApArticle> load(@RequestBody @Validated ArticleHomeDTO dto) {
        return articleService.listArticleByType(dto, ArticleConstant.LOADTYPE_LOAD_MORE);
    }

    @PostMapping("/loadmore")
    public List<ApArticle> loadMore(@RequestBody @Validated ArticleHomeDTO dto) {
        return articleService.listArticleByType(dto, ArticleConstant.LOADTYPE_LOAD_MORE);
    }

    @PostMapping("/loadnew")
    public List<ApArticle> loadNew(@RequestBody @Validated ArticleHomeDTO dto) {
        return articleService.listArticleByType(dto, ArticleConstant.LOADTYPE_LOAD_NEW);
    }

}
