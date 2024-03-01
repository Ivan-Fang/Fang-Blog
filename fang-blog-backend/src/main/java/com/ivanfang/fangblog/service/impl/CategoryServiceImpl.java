package com.ivanfang.fangblog.service.impl;

import com.ivanfang.fangblog.mapper.CategoryMapper;
import com.ivanfang.fangblog.pojo.Category;
import com.ivanfang.fangblog.service.CategoryService;
import com.ivanfang.fangblog.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public void add(Category category) {
        Map<String, Object> userInfo = ThreadLocalUtil.get();
        Integer userId = (Integer) userInfo.get("id");
        category.setCreateUser(userId);
        categoryMapper.add(category);
    }

    // get category list created by current user
    @Override
    public List<Category> list() {
        // get current user id
        Map<String, Object> userInfo = ThreadLocalUtil.get();
        Integer userId = (Integer) userInfo.get("id");
        return categoryMapper.list(userId);
    }

    @Override
    public Category getById(Integer id) {
        return categoryMapper.getById(id);
    }

    @Override
    public void update(Category category) {
        categoryMapper.update(category);
    }

    @Override
    public void delete(Integer id) {
        categoryMapper.delete(id);
    }

    // get all category no matter who the creating user is
    @Override
    public List<Category> getAll() {
        return categoryMapper.getAll();
    }

    @Override
    public Map<Integer, String> getIdCategoryNameMap() {
        List<Category> categoryList = getAll();
        Map<Integer, String> idCategoryNameMap = new HashMap<>();
        for (Category category : categoryList) {
            idCategoryNameMap.put(category.getId(), category.getCategoryName());
        }
        return idCategoryNameMap;
    }

}
