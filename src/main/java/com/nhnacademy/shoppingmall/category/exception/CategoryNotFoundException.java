package com.nhnacademy.shoppingmall.category.exception;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(int categoryId) { super( "Category with id " + categoryId + " not found"); }
}
