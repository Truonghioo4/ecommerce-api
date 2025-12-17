package com.dinchan.service.decorator.search;

import com.dinchan.model.Category;
import com.dinchan.model.Product;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Concrete Decorator - Thêm chức năng lọc theo category
 */
public class CategoryFilterDecorator extends ProductSearcherDecorator {
  private final String categoryId;

  public CategoryFilterDecorator(ProductSearcher wrappedSearcher, String categoryId) {
    super(wrappedSearcher);
    this.categoryId = categoryId;
  }

  @Override
  public List<Product> search(String query) {
    List<Product> products = super.search(query);

    if (categoryId == null || categoryId.isEmpty()) {
      return products;
    }

    return products.stream()
      .filter(product ->
        product.getCategory() != null &&
        isCategoryMatch(product.getCategory(), categoryId)
      ).toList();
  }

  public boolean isCategoryMatch(Category category, String targetCategoryId) {
    if (category == null) {
      return false;
    }

    if (targetCategoryId.equals(category.getCategoryId())) {
      return true;
    }

    return isCategoryMatch(category.getParentCategory(), targetCategoryId);
  }
}




