package com.dinchan.service.decorator.search;

import com.dinchan.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service quản lý việc tạo và sử dụng Product Search Decorators
 * Áp dụng Decorator Pattern để mở rộng chức năng tìm kiếm một cách linh hoạt
 */
@Service
@RequiredArgsConstructor
public class ProductSearchService {
  private final BaseProductSearcher baseProductSearcher;

  /**
   * Tìm kiếm sản phẩm với các tùy chọn lọc và sắp xếp
   */
  public List<Product> searchWithFilters(
    String query,
    String categoryId,
    String color,
    Integer minPrice,
    Integer maxPrice,
    Integer minDiscount,
    String sortBy,
    Boolean sortByPopularity
  ) {
    // Bắt đầu với base searcher
    ProductSearcher searcher = baseProductSearcher;

    // Áp dụng các decorator theo thứ tự: Filter trước, Sort sau

    // 1. Category filter
    if (categoryId != null && !categoryId.isEmpty()) {
      searcher = new CategoryFilterDecorator(searcher, categoryId);
    }

    // 2. Color filter
    if (color != null && !color.isEmpty()) {
      searcher = new ColorFilterDecorator(searcher, color);
    }

    // 3. Price range filter
    if (minPrice != null || maxPrice != null) {
      searcher = new PriceRangeFilterDecorator(searcher, minPrice, maxPrice);
    }

    // 4. Discount filter
    if (minDiscount != null && minDiscount > 0) {
      searcher = new DiscountFilterSearchDecorator(searcher, minDiscount);
    }

    // 5. Sorting decorators (áp dụng cuối cùng)
    if (sortByPopularity != null && sortByPopularity) {
      searcher = new PopularitySearchDecorator(searcher);
    } else if (sortBy != null && !sortBy.isEmpty()) {
      // sortBy có thể là "price_asc" hoặc "price_desc"
      if (sortBy.startsWith("price")) {
        String order = sortBy.contains("desc") ? "desc" : "asc";
        searcher = new PriceSortSearchDecorator(searcher, order);
      }
    }

    return searcher.search(query);
  }

  /**
   * Tìm kiếm đơn giản chỉ với query
   */
  public List<Product> simpleSearch(String query) {
    return baseProductSearcher.search(query);
  }

  /**
   * Tìm kiếm với sắp xếp theo giá
   */
  public List<Product> searchWithPriceSort(String query, String sortOrder) {
    ProductSearcher searcher = new PriceSortSearchDecorator(baseProductSearcher, sortOrder);
    return searcher.search(query);
  }

  /**
   * Tìm kiếm sản phẩm khuyến mãi (có discount cao)
   */
  public List<Product> searchPromotionProducts(String query, int minDiscount) {
    ProductSearcher searcher = new DiscountFilterSearchDecorator(baseProductSearcher, minDiscount);
    searcher = new PriceSortSearchDecorator(searcher, "asc");
    return searcher.search(query);
  }

  /**
   * Tìm kiếm sản phẩm phổ biến
   */
  public List<Product> searchPopularProducts(String query) {
    ProductSearcher searcher = new PopularitySearchDecorator(baseProductSearcher);
    return searcher.search(query);
  }
}

