package com.dinchan.service.decorator.search;

import com.dinchan.model.Product;

import java.util.List;

/**
 * Base Decorator - Abstract Decorator cho tìm kiếm sản phẩm
 */
public abstract class ProductSearcherDecorator implements ProductSearcher {
  protected ProductSearcher wrappedSearcher;

  public ProductSearcherDecorator(ProductSearcher wrappedSearcher) {
    this.wrappedSearcher = wrappedSearcher;
  }

  @Override
  public List<Product> search(String query) {
    return wrappedSearcher.search(query);
  }
}
