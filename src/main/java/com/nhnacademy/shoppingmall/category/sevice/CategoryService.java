package com.nhnacademy.shoppingmall.category.sevice;

import com.nhnacademy.shoppingmall.category.domain.Category;

import java.util.List;

public interface CategoryService {

    Category getCategory(int categoryId);
    Category getCategory(String categoryName);
    List<Category> getChildCategories(int parentCategoryId);

    List<Category> getRootCategories();

    void saveCategory(Category category);
    void updateCategory(Category category);
    void deleteCategory(int categoryId);
}
