package com.ivanfang.fangblog.controller;

import com.ivanfang.fangblog.pojo.Article;
import com.ivanfang.fangblog.pojo.PageBean;
import com.ivanfang.fangblog.pojo.Result;
import com.ivanfang.fangblog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    // add new article
    @PostMapping
    public Result add(@RequestBody @Validated(Article.Add.class) Article article) {
        articleService.add(article);
        return Result.success();
    }

    // list articles by page
    @GetMapping
    public Result<PageBean<Article>> list(Integer pageNum,
                                          Integer pageSize,
                                          @RequestParam(required = false) Integer categoryId,
                                          @RequestParam(required = false) String state) {
        PageBean<Article> articlePageBean = articleService.list(pageNum, pageSize, categoryId, state);
        return Result.success(articlePageBean);
    }

    // get all released articles (no matter it is published by current user)
    @GetMapping("/allReleased")
    public Result<PageBean<Article>> getAllReleasedArticle(Integer pageNum, Integer pageSize) {
        PageBean<Article> articlePageBean = articleService.getAllReleasedArticle(pageNum, pageSize);
        return Result.success(articlePageBean);
    }

    // get the detail of specified article id
    @GetMapping("/{id}")
    public Result<Article> getById(@PathVariable("id") Integer id) {
        Article article = articleService.getById(id);
        return Result.success(article);
    }

    // update the article info
    @PutMapping
    public Result update(@RequestBody @Validated(Article.Update.class) Article article) {
        articleService.update(article);
        return Result.success();
    }

    // delete the article by id
    @DeleteMapping
    public Result delete(Integer id) {
        articleService.delete(id);
        return Result.success();
    }

}
