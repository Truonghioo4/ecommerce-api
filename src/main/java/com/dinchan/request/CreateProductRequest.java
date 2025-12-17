package com.dinchan.request;

import com.dinchan.model.Category;
import lombok.Data;

import java.util.List;
@Data
public class CreateProductRequest {
    private String title;
    private String description;
    private int mrpPrice;
    private int sellPrice;
    private String color;
    private List<String> images;
    private Category category;
    private Category category2;
    private Category category3;
    private String sizes;
}
