package com.ivanfang.fangtimes.service;

import com.ivanfang.fangtimes.pojo.Category;
import com.ivanfang.fangtimes.pojo.Result;

import java.util.List;
import java.util.Map;

public interface CategoryService {
    void add(Category category);

    List<Category> list();

    Category getById(Integer id);

    void update(Category category);

    void delete(Integer id);

    List<Category> getAll();

    Map<Integer, String> getIdCategoryNameMap();
}
