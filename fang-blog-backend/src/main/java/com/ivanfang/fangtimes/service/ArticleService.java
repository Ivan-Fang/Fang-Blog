package com.ivanfang.fangtimes.service;

import com.ivanfang.fangtimes.pojo.Article;
import com.ivanfang.fangtimes.pojo.PageBean;

public interface ArticleService {
    void add(Article article);

    PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, String state);

    Article getById(Integer id);

    void update(Article article);

    void delete(Integer id);

    PageBean<Article> getAllReleasedArticle(Integer pageNum, Integer pageSize);
}
