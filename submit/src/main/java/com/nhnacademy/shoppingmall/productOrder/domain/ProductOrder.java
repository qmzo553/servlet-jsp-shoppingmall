package com.nhnacademy.shoppingmall.productOrder.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class ProductOrder {

    private int productId;
    private String orderId;
    private int productCount;
    private int productPrice;

    public ProductOrder(int productId, String orderId, int productCount, int productPrice) {
        this.productId = productId;
        this.orderId = orderId;
        this.productCount = productCount;
        this.productPrice = productPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductOrder productOrder = (ProductOrder) o;
        return productId == productOrder.productId &&
                productCount == productOrder.productCount &&
                productPrice == productOrder.productPrice &&
                Objects.equals(orderId, productOrder.orderId);
    }

    @Override
    public int hashCode() { return Objects.hash(productId, orderId, productCount, productPrice); }

    @Override
    public String toString() {
        return "productOrder {" +
                "productId = '" + productId + '\'' +
                ", orderId = '" + orderId + '\'' +
                ", productCount = '" + productCount + '\'' +
                ", productPrice = '" + productPrice + '}';
    }
}
