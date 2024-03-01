package com.ivanfang.fangblog.service;

import com.ivanfang.fangblog.pojo.Article;
import com.ivanfang.fangblog.pojo.PageBean;

public interface ArticleService {
    void add(Article article);

    PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, String state);

    Article getById(Integer id);

    void update(Article article);

    void delete(Integer id);

    PageBean<Article> getAllReleasedArticle(Integer pageNum, Integer pageSize);
}
