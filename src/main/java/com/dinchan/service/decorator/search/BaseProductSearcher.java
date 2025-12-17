package com.dinchan.service.decorator.search;

import com.dinchan.model.Product;
import com.dinchan.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Concrete Component - Triển khai tìm kiếm sản phẩm cơ bản
 */
@Component
@RequiredArgsConstructor
public class BaseProductSearcher implements ProductSearcher {
  private final ProductRepository productRepository;

  @Override
  public List<Product> search(String query) {
    // Tìm kiếm cơ bản theo title và category name
    return productRepository.searchProduct(query);
  }
}
