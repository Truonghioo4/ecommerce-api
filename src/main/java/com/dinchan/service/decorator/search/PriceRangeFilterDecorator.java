package com.dinchan.service.decorator.search;

import com.dinchan.model.Product;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Concrete Decorator - Thêm chức năng lọc theo khoảng giá
 */
public class PriceRangeFilterDecorator extends ProductSearcherDecorator {
  private final Integer minPrice;
  private final Integer maxPrice;

  public PriceRangeFilterDecorator(ProductSearcher wrappedSearcher, Integer minPrice, Integer maxPrice) {
    super(wrappedSearcher);
    this.minPrice = minPrice;
    this.maxPrice = maxPrice;
  }

  @Override
  public List<Product> search(String query) {
    List<Product> products = super.search(query);

    return products.stream()
      .filter(product -> {
        int price = product.getSellPrice();
        boolean matchMin = minPrice == null || price >= minPrice;
        boolean matchMax = maxPrice == null || price <= maxPrice;
        return matchMin && matchMax;
      })
      .collect(Collectors.toList());
  }
}

