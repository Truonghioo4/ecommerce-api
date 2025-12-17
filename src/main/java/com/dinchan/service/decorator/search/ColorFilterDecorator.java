package com.dinchan.service.decorator.search;

import com.dinchan.model.Product;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Concrete Decorator - Thêm chức năng lọc theo màu sắc
 */
public class ColorFilterDecorator extends ProductSearcherDecorator {
  private final String color;

  public ColorFilterDecorator(ProductSearcher wrappedSearcher, String color) {
    super(wrappedSearcher);
    this.color = color;
  }

  @Override
  public List<Product> search(String query) {
    List<Product> products = super.search(query);

    if (color == null || color.isEmpty()) {
      return products;
    }

    return products.stream()
      .filter(product -> color.equalsIgnoreCase(product.getColor()))
      .collect(Collectors.toList());
  }
}

