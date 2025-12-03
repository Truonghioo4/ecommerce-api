package com.dinchan.repository;

import com.dinchan.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByCategoryId(String id);
    List<Category> findAllByParentCategory(Category parent);
    List<Category> findAllByLevel(int level);
}
