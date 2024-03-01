package com.ivanfang.fangblog.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ivanfang.fangblog.mapper.ArticleMapper;
import com.ivanfang.fangblog.pojo.Article;
import com.ivanfang.fangblog.pojo.PageBean;
import com.ivanfang.fangblog.service.ArticleService;
import com.ivanfang.fangblog.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public void add(Article article) {
        // get login user id
        Map<String, Object> userInfo = ThreadLocalUtil.get();
        Integer userId = (Integer) userInfo.get("id");
        article.setCreateUser(userId);

        // add new article
        articleMapper.add(article);
    }

    @Override
    public PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, String state) {
        // get login user id
        Map<String, Object> userInfo = ThreadLocalUtil.get();
        Integer userId = (Integer) userInfo.get("id");

        // get articles by page
        PageHelper.startPage(pageNum, pageSize);                                    // open page helper
        List<Article> articleList = articleMapper.list(userId, categoryId, state);   // get data as usual
        Page<Article> articlePage = (Page<Article>) articleList;                     // translate into Page type

        // translate into PageBean type
        PageBean<Article> pageBean = new PageBean<>();
        pageBean.setTotal(articlePage.getTotal());
        pageBean.setItems(articlePage.getResult());

        return pageBean;
    }

    @Override
    public PageBean<Article> getAllReleasedArticle(Integer pageNum, Integer pageSize) {
        // get articles by page
        PageHelper.startPage(pageNum, pageSize);
        List<Article> articleList = articleMapper.getAllReleasedArticle();
        Page<Article> articlePage = (Page<Article>) articleList;

        // translate to PageBean
        PageBean<Article> pageBean = new PageBean<>();
        pageBean.setItems(articlePage.getResult());
        pageBean.setTotal(articlePage.getTotal());

        return pageBean;
    }

    @Override
    public Article getById(Integer articleId) {
        // check if login user can access the article
        Map<String, Object> userInfo = ThreadLocalUtil.get();
        Integer userId = (Integer) userInfo.get("id");

        return articleMapper.getById(userId, articleId);
    }

    @Override
    public void update(Article article) {
        // check if login user can access the article
        Map<String, Object> userInfo = ThreadLocalUtil.get();
        Integer userId = (Integer) userInfo.get("id");
        article.setCreateUser(userId);

        // update the article
        articleMapper.update(article);
    }

    @Override
    public void delete(Integer id) {
        // check if login user can access the article
        Map<String, Object> userInfo = ThreadLocalUtil.get();
        Integer userId = (Integer) userInfo.get("id");

        // delete the article
        articleMapper.delete(userId, id);
    }

}
