package com.dinchan.service;

import com.dinchan.model.Category;
import com.dinchan.request.CreateCategoryRequest;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories(Integer level, String categoryParentId);
    Category getCategoryByCategoryId(String id);
}
