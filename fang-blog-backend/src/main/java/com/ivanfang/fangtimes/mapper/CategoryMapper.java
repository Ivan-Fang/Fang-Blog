package com.ivanfang.fangtimes.mapper;

import com.ivanfang.fangtimes.pojo.Category;
import com.ivanfang.fangtimes.pojo.Result;
import org.apache.ibatis.annotations.*;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;

@Mapper
public interface CategoryMapper {
    @Insert("insert into category (category_name, category_alias, create_user, create_time, update_time) " +
            "values (#{category.categoryName}, #{category.categoryAlias}, #{category.createUser}, now(), now());")
    void add(@Param("category") Category category);

    @Select("select * from category where create_user = #{userId}")
    List<Category> list(Integer userId);

    @Select("select * from category where id = #{id}")
    Category getById(Integer id);

    @Update("update category " +
            "set category_name = #{categoryName}, category_alias = #{categoryAlias}, update_time = now()" +
            "where id = #{id}")
    void update(Category category);

    @Delete("delete from category where id = #{id}")
    void delete(Integer id);

    @Select("select * from category")
    List<Category> getAll();
}
