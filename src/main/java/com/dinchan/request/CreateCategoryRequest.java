package com.dinchan.request;

import com.dinchan.model.Category;
import lombok.Data;

@Data
public class CreateCategoryRequest {
    private Category category;
    private Category category2;
    private Category category3;
}
