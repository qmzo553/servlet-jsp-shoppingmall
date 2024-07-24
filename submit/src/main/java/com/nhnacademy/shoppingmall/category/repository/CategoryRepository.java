package com.nhnacademy.shoppingmall.category.repository;

import com.nhnacademy.shoppingmall.category.domain.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository {

    Optional<Category> findByCategoryId(int categoryId);
    Optional<Category> findByCategoryName(String categoryName);

    List<Category> findRootCategories();

    List<Category> findChildrenByCategoryId(int parentCategoryId);
    int save(Category category);
    int deleteByCategoryId(int categoryId);
    int update(Category category);
    int countByCategoryId(int categoryId);
    int getLastCategoryId();
}
