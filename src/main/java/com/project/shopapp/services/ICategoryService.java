package com.project.shopapp.services;

import com.project.shopapp.dtos.CategoryDTO;
import com.project.shopapp.models.Category;


import java.util.List;

public interface ICategoryService {
    Category createCategory(CategoryDTO categoryDTO);
    Category getCategory(Long id);

    Category getCategoryById(Long id);

    List<Category> getAllCategory();
    void updateCategory(long id , CategoryDTO categoryDTO);
    void deleteCategory(Long id);
}
