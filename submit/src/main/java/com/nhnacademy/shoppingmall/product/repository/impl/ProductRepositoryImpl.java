package com.nhnacademy.shoppingmall.product.repository.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public class ProductRepositoryImpl implements ProductRepository {

    @Override
    public Optional<Product> findById(int productId) {

        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "select * from products where product_id = ?";
        log.debug("sql : {}", sql);

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, productId);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Product(
                            rs.getInt("product_id"),
                            rs.getString("product_name"),
                            rs.getInt("product_price"),
                            rs.getInt("product_stock"),
                            rs.getString("product_img"),
                            rs.getInt("category_id")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    @Override
    public int save(Product product) {

        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "insert into products (product_name, product_price, product_stock, product_Img, category_id) values (?, ?, ?, ?, ?)";
        log.debug("sql : {}", sql);

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, product.getProductName());
            preparedStatement.setInt(2, product.getProductPrice());
            preparedStatement.setInt(3, product.getProductStock());
            preparedStatement.setString(4, product.getProductImg());
            preparedStatement.setInt(5, product.getCategoryId());

            int result = preparedStatement.executeUpdate();
            log.debug("save result : {}", result);
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int deleteByProductId(int productId) {

        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "delete from products where product_id = ?";
        log.debug("sql : {}", sql);

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, productId);

            int result = preparedStatement.executeUpdate();
            log.debug("delete result : {}", result);
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int update(Product product) {

        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "update products set product_name = ?, product_price = ?, product_stock = ?, product_img = ?, category_id = ? where product_id = ?";
        log.debug("sql : {}", sql);

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, product.getProductName());
            preparedStatement.setInt(2, product.getProductPrice());
            preparedStatement.setInt(3, product.getProductStock());
            preparedStatement.setString(4, product.getProductImg());
            preparedStatement.setInt(5, product.getCategoryId());
            preparedStatement.setInt(6, product.getProductId());

            int result = preparedStatement.executeUpdate();
            log.debug("update result : {}", result);
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int countByProductId(int productId) {

        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "select count(*) from products where product_id = ?";
        log.debug("sql : {}", sql);
        int count = 0;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, productId);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt(1);
                    log.debug("count result : {}", count);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return count;
    }

    @Override
    public int getLastProductId() {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "select max(product_id) from products";
        log.debug("sql : {}", sql);
        int productId = 0;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet rs = preparedStatement.executeQuery()) {
            if(rs.next()) {
                productId = rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return productId;
    }

    @Override
    public long totalCount() {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "select count(*) from products";
        log.debug("sql : {}", sql);

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet rs = preparedStatement.executeQuery()) {

            if (rs.next()) {
                return rs.getLong(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return 0l;
    }

    @Override
    public Page<Product> findAll(int page, int pageSize) {

        Connection connection = DbConnectionThreadLocal.getConnection();
        int offset = (page - 1) * pageSize;
        int limit = pageSize;

        String sql = "select product_id, product_name, product_price, product_stock, product_img, category_id from products limit ?, ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, offset);
            preparedStatement.setInt(2, limit);
            List<Product> productList = new ArrayList<>(pageSize);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    productList.add(new Product(
                            rs.getInt("product_id"),
                            rs.getString("product_name"),
                            rs.getInt("product_price"),
                            rs.getInt("product_stock"),
                            rs.getString("product_img"),
                            rs.getInt("category_id")
                    ));
                }
            }

            long total = 0;

            if (!productList.isEmpty()) {
                total = totalCount();
            }

            return new Page<Product>(productList, total);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
