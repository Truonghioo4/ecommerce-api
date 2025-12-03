package com.dinchan.service.impl;

import com.dinchan.model.Category;
import com.dinchan.repository.CategoryRepository;
import com.dinchan.request.CreateCategoryRequest;
import com.dinchan.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private CategoryRepository categoryRepository;
    @Override
    public List<Category> getAllCategories(Integer level, String categoryParentId){
        if(level!=null) return categoryRepository.findAllByLevel(level);
        if(categoryParentId != null){
            Category categoryParent = categoryRepository.findByCategoryId(categoryParentId);
            return categoryRepository.findAllByParentCategory(categoryParent);
        }
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryByCategoryId(String id){
        return categoryRepository.findByCategoryId(id);
    }

}
