package com.dinchan.controller;

import com.dinchan.exceptions.ProductExeption;
import com.dinchan.model.Product;
import com.dinchan.service.ProductService;
import com.dinchan.service.decorator.search.ProductSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final ProductSearchService productSearchService;

    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable Long productId) throws ProductExeption {
        Product product = productService.findProductById(productId);
        return new ResponseEntity<>(product, HttpStatus.OK );
    }

    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProduct(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String color,
            @RequestParam(required = false) Integer minPrice,
            @RequestParam(required = false) Integer maxPrice,
            @RequestParam(required = false) Integer minDiscount,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) Boolean sortByPopularity
    ) {
        // Sử dụng ProductSearchService với Decorator Pattern
        List<Product> products = productSearchService.searchWithFilters(
                query, category, color, minPrice, maxPrice,
                minDiscount, sortBy, sortByPopularity
        );
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/search/simple")
    public ResponseEntity<List<Product>> simpleSearch(
            @RequestParam(required = false) String query
    ) {
        List<Product> products = productSearchService.simpleSearch(query);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/search/promotion")
    public ResponseEntity<List<Product>> searchPromotionProducts(
            @RequestParam(required = false) String query,
            @RequestParam(defaultValue = "20") int minDiscount
    ) {
        List<Product> products = productSearchService.searchPromotionProducts(query, minDiscount);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/search/popular")
    public ResponseEntity<List<Product>> searchPopularProducts(
            @RequestParam(required = false) String query
    ) {
        List<Product> products = productSearchService.searchPopularProducts(query);
        return new ResponseEntity<>(products, HttpStatus.OK);

    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return new ResponseEntity<>(
                productService.getAllProducts(), HttpStatus.OK);
    }
}
