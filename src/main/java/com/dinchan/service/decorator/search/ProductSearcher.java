package com.dinchan.service.decorator.search;

import com.dinchan.model.Product;

import java.util.List;

/**
 * Component Interface - Định nghĩa interface cho chức năng tìm kiếm sản phẩm
 */
public interface ProductSearcher {
  /**
   * Tìm kiếm sản phẩm theo query
   * @param query từ khóa tìm kiếm
   * @return danh sách sản phẩm tìm được
   */
  List<Product> search(String query);
}
