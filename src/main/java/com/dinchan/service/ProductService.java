package com.dinchan.service;

import com.dinchan.model.Product;
import com.dinchan.model.Seller;
import com.dinchan.request.CreateProductRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    public Product createProduct(CreateProductRequest req, Seller seller);
    public void deleteProduct(Long id);
    public Product updateProduct(Long id, Product product);
    public Product findProductById(Long id);
    public List<Product> searchProducts(String query);
    public List<Product> getAllProducts();
    public List<Product> getProductBySellerId(Long id);

}
