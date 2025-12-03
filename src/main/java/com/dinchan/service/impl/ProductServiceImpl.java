package com.dinchan.service.impl;

import com.dinchan.exceptions.ProductExeption;
import com.dinchan.model.Category;
import com.dinchan.model.Product;
import com.dinchan.model.Seller;
import com.dinchan.repository.CategoryRepository;
import com.dinchan.repository.ProductRepository;
import com.dinchan.request.CreateProductRequest;
import com.dinchan.service.ProductService;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;


    @Override
    public Product createProduct(CreateProductRequest req, Seller seller) {
        Category directCategory = null;
        Category category1 = null;
        Category category2 = null;
        Category category3 = null;
        if(req.getCategory() != null) {
            category1 = categoryRepository.findByCategoryId(req.getCategory().getCategoryId());
            if(category1 == null) {
                Category category = new Category();
                category.setCategoryId(req.getCategory().getCategoryId());
                category.setName(req.getCategory().getName());
                category.setLevel(1);
                category1 = categoryRepository.save(category);
            }
            directCategory = category1;
        }

        if(req.getCategory2() != null && req.getCategory() !=null){
            category2 = categoryRepository.findByCategoryId(req.getCategory2().getCategoryId());
            if(category2 == null) {
                Category category = new Category();
                category.setCategoryId(req.getCategory2().getCategoryId());
                category.setName(req.getCategory2().getName());
                category.setLevel(2);
                category.setParentCategory(category1);
                category2 = categoryRepository.save(category);
            }
            directCategory = category2;
        }
        if(req.getCategory3() != null && req.getCategory2() != null) {
            category3 =  categoryRepository.findByCategoryId(req.getCategory3().getCategoryId());
            if(category3 == null ) {
                Category category = new Category();
                category.setCategoryId(req.getCategory3().getCategoryId());
                category.setName(req.getCategory3().getName());
                category.setLevel(3);
                category.setParentCategory(category2);
                category3 = categoryRepository.save(category);
            }
            directCategory = category3;
        }

        int discountPercentage = calculateDiscountPercentage(req.getMrpPrice(), req.getSellingPrice());
        Product product = new Product();
        product.setSeller(seller);
        product.setCategory(directCategory);
        product.setDescription(req.getDescription());
        product.setCreatedAt(LocalDateTime.now());
        product.setTitle(req.getTitle());
        product.setColor(req.getColor());
        product.setSellPrice(req.getSellingPrice());
        product.setImages(req.getImages());
        product.setMrpPrice(req.getMrpPrice());
        product.setSizes(req.getSizes());
        product.setDiscountPercent(discountPercentage);
        return productRepository.save(product);
    }

    private int calculateDiscountPercentage(int mrpPrice, int sellingPrice) {
        if (mrpPrice <= 0) {
            throw new IllegalArgumentException("Actual price must be greater than 0");
        }
        double discount = mrpPrice - sellingPrice;
        double discountPercentage = (discount / mrpPrice) * 100;
        return (int) discountPercentage;
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = findProductById(id);
        productRepository.delete(product);
    }

    @Override
    public Product updateProduct(Long id, Product product) {
        findProductById(id);
        product.setId(id);
        return productRepository.save(product);
    }

    @Override
    public Product findProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() ->
                new ProductExeption("product not found with id" + id));
    }

    @Override
    public List<Product> searchProducts(String query) {
        return productRepository.searchProduct(query);
    }

    @Override
    public Page<Product> getAllProducts(String category, String brand, String colors, String sizes, Integer minPrice, Integer maxPrice, Integer minDiscount, String sort, String stock, Integer pageNumber) {
        Specification<Product> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(category != null) {
                Join<Product, Category> categoryJoin = root.join("category");
                predicates.add(criteriaBuilder.equal(categoryJoin.get("categoryId"), category));
            }
            if(colors != null && !colors.isEmpty()) {
                System.out.println("colors: " + colors);
                predicates.add(criteriaBuilder.equal(root.get("color"),colors));
            }

            if(sizes != null && !sizes.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("size"), sizes));
            }
            if(minPrice != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("sellingPrice"), minPrice));
            }
            if(maxPrice != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("sellingPrice"), maxPrice));
            }
            if(minDiscount != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("discountPercent"), minDiscount));
            }
            if(stock != null) {
                predicates.add(criteriaBuilder.equal(root.get("stock"), stock));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
        Pageable pageable;
        if(sort != null && !sort.isEmpty()) {
            pageable = switch (sort) {
                case "price_low" -> PageRequest.of(pageNumber != null ? pageNumber : 0, 10,
                        Sort.by("sellingPrice").ascending());
                case "price_high" -> PageRequest.of(pageNumber != null ? pageNumber : 0, 10,
                        Sort.by("sellingPrice").descending());
                default -> PageRequest.of(pageNumber != null ? pageNumber : 0, 10,
                        Sort.unsorted());
            };
        } else {
            pageable = PageRequest.of(pageNumber != null ? pageNumber : 0, 10, Sort.unsorted());
        }
        return productRepository.findAll(spec,pageable);
    }

    @Override
    public List<Product> getProductBySellerId(Long id) {
        return productRepository.findBySellerId(id);
    }
}
