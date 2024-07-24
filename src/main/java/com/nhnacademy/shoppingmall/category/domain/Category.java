package com.nhnacademy.shoppingmall.category.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Setter
@Getter
public class Category {

    private int categoryId;
    private String categoryName;
    private int parentCategoryId;

    public Category(int categoryId, String categoryName, int parentCategoryId) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.parentCategoryId = parentCategoryId;
    }

    public Category(String categoryName, int parentCategoryId) {
        this.categoryId = -1;
        this.categoryName = categoryName;
        this.parentCategoryId = parentCategoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return categoryId == category.categoryId &&
                parentCategoryId == category.parentCategoryId &&
                Objects.equals(categoryName, category.categoryName);
    }

    @Override
    public int hashCode() { return Objects.hash(categoryId, parentCategoryId, categoryName); }

    @Override
    public String toString() {
        return "Category {" +
                "categoryId= '" + categoryId + '\'' +
                ", categoryName= '" + categoryName + '\'' +
                ", parentCategoryId= '" + parentCategoryId + '}';
    }
}
