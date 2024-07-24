package com.nhnacademy.shoppingmall.product.service;

import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.product.domain.Product;

public interface ProductService {

    Product getProduct(int productId);
    void saveProduct(Product product);
    void updateProduct(Product product);
    void deleteProduct(int productId);
    long getProductTotalCount();
    Page<Product> getProducts(int page, int pageSize);

}
