package com.nhnacademy.shoppingmall.productOrder.repository.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.productOrder.domain.ProductOrder;
import com.nhnacademy.shoppingmall.productOrder.repository.ProductOrderRepository;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public class ProductOrderRepositoryImpl implements ProductOrderRepository {

    @Override
    public Optional<ProductOrder> findByProductIdAndOrderId(int productId, String orderId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "select * from products_orders where product_id = ? and order_id = ?";
        log.debug("sql:{}", sql);

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, productId);
            preparedStatement.setString(2, orderId);

            try(ResultSet rs = preparedStatement.executeQuery()) {
                if(rs.next()) {
                    return Optional.of(new ProductOrder(
                            rs.getInt("product_id"),
                            rs.getString("order_id"),
                            rs.getInt("product_count"),
                            rs.getInt("product_price")
                    ));
                }
            }
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public List<ProductOrder> findByOrderId(String orderId) {
        List<ProductOrder> productOrders = new ArrayList<>();
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "select * from products_orders where order_id = ?";
        log.debug("sql:{}", sql);

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, orderId);

            try(ResultSet rs = preparedStatement.executeQuery()) {
                while(rs.next()) {
                    productOrders.add(new ProductOrder(
                            rs.getInt("product_id"),
                            rs.getString("order_id"),
                            rs.getInt("product_count"),
                            rs.getInt("product_price")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return productOrders;
    }

    @Override
    public int save(ProductOrder productOrder) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "insert into products_orders values (?, ?, ?, ?)";
        log.debug("sql:{}", sql);

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, productOrder.getProductId());
            preparedStatement.setString(2, productOrder.getOrderId());
            preparedStatement.setInt(3, productOrder.getProductCount());
            preparedStatement.setInt(4, productOrder.getProductPrice());

            int result = preparedStatement.executeUpdate();
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int deleteByOrderId(String orderId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "delete from products_orders where order_id = ?";
        log.debug("sql:{}", sql);

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, orderId);

            int result = preparedStatement.executeUpdate();
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int deleteByProductIdAndOrderId(int productId, String orderId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "delete from products_orders where product_id = ? and order_id = ?";
        log.debug("sql:{}", sql);

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, productId);
            preparedStatement.setString(2, orderId);

            int result = preparedStatement.executeUpdate();
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int countByOrderId(String orderId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "select count(*) from products_orders where order_id = ?";
        log.debug("sql:{}", sql);
        int count = 0;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, orderId);

            try(ResultSet rs = preparedStatement.executeQuery()) {
                if(rs.next()) {
                    count = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return count;
    }
}
