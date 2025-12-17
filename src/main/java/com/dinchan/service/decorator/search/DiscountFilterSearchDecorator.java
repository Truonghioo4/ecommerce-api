package com.dinchan.service.decorator.search;

import com.dinchan.model.Product;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Concrete Decorator - Thêm chức năng lọc theo mức discount tối thiểu
 */
public class DiscountFilterSearchDecorator extends ProductSearcherDecorator {
  private final int minDiscount;

  public DiscountFilterSearchDecorator(ProductSearcher wrappedSearcher, int minDiscount) {
    super(wrappedSearcher);
    this.minDiscount = minDiscount;
  }

  @Override
  public List<Product> search(String query) {
    List<Product> products = super.search(query);

    // Lọc sản phẩm có discount >= minDiscount
    return products.stream()
      .filter(product -> product.getDiscountPercent() >= minDiscount)
      .collect(Collectors.toList());
  }
}
