package com.dinchan.controller;

import com.dinchan.exceptions.ProductExeption;
import com.dinchan.model.Category;
import com.dinchan.model.Product;
import com.dinchan.request.CreateCategoryRequest;
import com.dinchan.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/{categoryId}")
    public ResponseEntity<Category> getCategoryByCategoryId(@PathVariable String categoryId) {
        Category category = categoryService.getCategoryByCategoryId(categoryId);
        return new ResponseEntity<>(category, HttpStatus.OK );
    }

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories(@RequestParam(required = false) Integer level, @RequestParam(required = false) String categoryParentId) {
        List<Category> categories = categoryService.getAllCategories(level, categoryParentId);
        return new ResponseEntity<>(categories, HttpStatus.OK);

    }
}
