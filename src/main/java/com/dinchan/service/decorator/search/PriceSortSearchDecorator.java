package com.dinchan.service.decorator.search;

import com.dinchan.model.Product;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Concrete Decorator - Thêm chức năng sắp xếp theo giá
 */
public class PriceSortSearchDecorator extends ProductSearcherDecorator {
  private final String sortOrder; // "asc" hoặc "desc"

  public PriceSortSearchDecorator(ProductSearcher wrappedSearcher, String sortOrder) {
    super(wrappedSearcher);
    this.sortOrder = sortOrder != null ? sortOrder.toLowerCase() : "asc";
  }

  @Override
  public List<Product> search(String query) {
    List<Product> products = super.search(query);

    // Sắp xếp theo giá bán
    if ("desc".equals(sortOrder)) {
      return products.stream()
        .sorted(Comparator.comparing(Product::getSellPrice).reversed())
        .collect(Collectors.toList());
    } else {
      return products.stream()
        .sorted(Comparator.comparing(Product::getSellPrice))
        .collect(Collectors.toList());
    }
  }
}
