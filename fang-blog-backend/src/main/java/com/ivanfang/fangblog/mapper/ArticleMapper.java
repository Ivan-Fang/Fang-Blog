package com.ivanfang.fangblog.mapper;

import com.ivanfang.fangblog.pojo.Article;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ArticleMapper {

    @Insert("insert into article(title, content, cover_img, state, category_id, create_user, create_time, update_time)" +
            "values(#{title}, #{content}, #{coverImg}, #{state}, #{categoryId}, #{createUser}, now(), now())")
    void add(Article article);

    List<Article> list(Integer userId, Integer categoryId, String state);

    @Select("select * from article where create_user = #{userId} and id = #{articleId}")
    Article getById(Integer userId, Integer articleId);

    void update(@Param("article") Article article);

    @Delete("delete from article where create_user = #{userId} and id = #{articleId}")
    void delete(Integer userId, Integer articleId);

    @Select("select * from article where state = 'released' order by update_time desc")
    List<Article> getAllReleasedArticle();
}
