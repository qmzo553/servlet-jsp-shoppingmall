package com.nhnacademy.shoppingmall.category.sevice.impl;

import com.nhnacademy.shoppingmall.category.domain.Category;
import com.nhnacademy.shoppingmall.category.exception.CategoryAlreadyExistsException;
import com.nhnacademy.shoppingmall.category.exception.CategoryNotFoundException;
import com.nhnacademy.shoppingmall.category.repository.CategoryRepository;
import com.nhnacademy.shoppingmall.category.sevice.CategoryService;

import java.util.List;

public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category getCategory(int categoryId) {
        if(categoryId < 0) {
            throw new IllegalArgumentException("Invalid category id");
        }

        return categoryRepository.findByCategoryId(categoryId).get();
    }

    @Override
    public Category getCategory(String categoryName) {
        if(categoryName == null) {
            throw new IllegalArgumentException("Invalid category name");
        }

        return categoryRepository.findByCategoryName(categoryName).get();
    }

    @Override
    public List<Category> getChildCategories(int parentCategoryId) {
        if(parentCategoryId < 0) {
            throw new IllegalArgumentException("Invalid parent category id");
        }

        return categoryRepository.findChildrenByCategoryId(parentCategoryId);
    }

    @Override
    public List<Category> getRootCategories() {
        return categoryRepository.findRootCategories();
    }

    @Override
    public void saveCategory(Category category) {
        if(categoryRepository.countByCategoryId(category.getCategoryId()) > 0) {
            throw new CategoryAlreadyExistsException(category.getCategoryId());
        }

        categoryRepository.save(category);
    }

    @Override
    public void updateCategory(Category category) {
        if(categoryRepository.countByCategoryId(category.getCategoryId()) <= 0) {
            throw new CategoryNotFoundException(category.getCategoryId());
        }

        categoryRepository.update(category);
    }

    @Override
    public void deleteCategory(int categoryId) {
        if(categoryRepository.countByCategoryId(categoryId) <= 0) {
            throw new CategoryNotFoundException(categoryId);
        }

        categoryRepository.deleteByCategoryId(categoryId);
    }
}
