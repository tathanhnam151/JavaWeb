package com.namta.ecom.services.admin.category;

import com.namta.ecom.dto.CategoryDto;
import com.namta.ecom.entity.Category;

import java.util.List;

public interface CategoryService {
    Category createCategory(CategoryDto categoryDto);

    List<Category> getAllCategories();

    // TODO CHECK 
    
    Category getCategoryById(Long id);

    Category updateCategory(Long id, CategoryDto categoryDto);

    void deleteCategory(Long id);
}
