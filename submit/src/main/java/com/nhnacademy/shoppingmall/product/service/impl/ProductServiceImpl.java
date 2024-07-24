package com.nhnacademy.shoppingmall.product.service.impl;

import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.exception.ProductAlreadyExistsException;
import com.nhnacademy.shoppingmall.product.exception.ProductNotFoundException;
import com.nhnacademy.shoppingmall.product.repository.ProductRepository;
import com.nhnacademy.shoppingmall.product.service.ProductService;

import java.util.Optional;

public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product getProduct(int productId) {

        Optional<Product> product = productRepository.findById(productId);
        if(product.isPresent()) {
            return product.get();
        } else {
            return null;
        }
    }

    @Override
    public void saveProduct(Product product) {

        if(productRepository.countByProductId(product.getProductId()) > 0) {
            throw new ProductAlreadyExistsException("Product already exists");
        }

        productRepository.save(product);
    }

    @Override
    public void updateProduct(Product product) {

        if(productRepository.countByProductId(product.getProductId()) <= 0) {
            throw new ProductNotFoundException("Product not found");
        }

        productRepository.update(product);
    }

    @Override
    public void deleteProduct(int productId) {

        if(productRepository.countByProductId(productId) <= 0) {
            throw new ProductNotFoundException("Product not found");
        }

        productRepository.deleteByProductId(productId);
    }

    @Override
    public long getProductTotalCount() {
        return productRepository.totalCount();
    }

    @Override
    public Page<Product> getProducts(int page, int pageSize) {
        return productRepository.findAll(page, pageSize);
    }
}
