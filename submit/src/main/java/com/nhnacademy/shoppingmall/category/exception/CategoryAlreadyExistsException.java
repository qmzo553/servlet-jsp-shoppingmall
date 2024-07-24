package com.nhnacademy.shoppingmall.category.exception;

public class CategoryAlreadyExistsException extends RuntimeException {
    public CategoryAlreadyExistsException(int categoryId) { super("Category with id " + categoryId + " already exists"); }
}
