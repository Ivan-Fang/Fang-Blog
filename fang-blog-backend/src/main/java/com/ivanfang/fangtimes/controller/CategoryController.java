package com.ivanfang.fangtimes.controller;

import com.ivanfang.fangtimes.pojo.Category;
import com.ivanfang.fangtimes.pojo.Result;
import com.ivanfang.fangtimes.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public Result add(@RequestBody @Validated(Category.Add.class) Category category) {
        categoryService.add(category);
        return Result.success();
    }

    // get category list created by current user
    @GetMapping
    public Result<List<Category>> list() {
        List<Category> categoryList = categoryService.list();
        return Result.success(categoryList);
    }

    // get all category no matter who the creating user is
    @GetMapping("/all")
    public Result<List<Category>> getAll() {
        List<Category> categoryList = categoryService.getAll();
        return Result.success(categoryList);
    }

    // get id-categoryName map
    @GetMapping("/id/categoryName/map")
    public Result<Map<Integer, String>> getIdCategoryNameMap() {
        Map<Integer, String> idCategoryNameMap = categoryService.getIdCategoryNameMap();
        return Result.success(idCategoryNameMap);
    }

    // get the detail of specified category
    @GetMapping("/detail")
    public Result<Category> detail(Integer id) {
        System.out.println("id = " + id);
        Category category = categoryService.getById(id);
        return Result.success(category);
    }

    // update specified category
    @PutMapping
    public Result update(@RequestBody @Validated(Category.Update.class) Category category) {
        categoryService.update(category);
        return Result.success();
    }

    // delete specified category
    @DeleteMapping
    public Result delete(@RequestParam Integer id) {
        categoryService.delete(id);
        return Result.success();
    }

}
