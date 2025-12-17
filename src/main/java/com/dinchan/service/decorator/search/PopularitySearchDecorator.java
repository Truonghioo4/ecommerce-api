package com.dinchan.service.decorator.search;

import com.dinchan.model.Product;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Concrete Decorator - Thêm chức năng sắp xếp theo độ phổ biến (số lượng đánh giá)
 */
public class PopularitySearchDecorator extends ProductSearcherDecorator {

  public PopularitySearchDecorator(ProductSearcher wrappedSearcher) {
    super(wrappedSearcher);
  }

  @Override
  public List<Product> search(String query) {
    List<Product> products = super.search(query);

    // Sắp xếp theo số lượng đánh giá giảm dần (sản phẩm phổ biến nhất lên đầu)
    return products.stream()
      .sorted(Comparator.comparing(Product::getNumRatings).reversed())
      .collect(Collectors.toList());
  }
}
