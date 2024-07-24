package com.nhnacademy.shoppingmall.product.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class Product {

    private int productId;
    private String productName;
    private int productPrice;
    private int productStock;
    private String productImg;
    private int categoryId;

    public Product(int productId, String productName, int productPrice, int productStock, String productImg, int categoryId) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productStock = productStock;
        this.productImg = productImg;
        this.categoryId = categoryId;
    }

    public Product(String productName, int productPrice, int productStock, String productImg, int categoryId) {
        this.productId = -1;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productStock = productStock;
        this.productImg = productImg;
        this.categoryId = categoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return productPrice == product.productPrice &&
                productStock == product.productStock &&
                categoryId == product.categoryId &&
                Objects.equals(productId, product.productId) &&
                Objects.equals(productName, product.productName);
    }

    @Override
    public int hashCode() {return Objects.hash(productId, productName, productPrice, productStock, categoryId); }

    @Override
    public String toString() {
        return "Product {" +
                "productId=" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", productPrice='" + productPrice + '\'' +
                ", productStock='" + productStock + '\'' +
                ", categoryId=" + categoryId + '}';
    }

}
