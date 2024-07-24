package com.nhnacademy.shoppingmall.order.repository.impl;

import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.order.domain.Order;
import com.nhnacademy.shoppingmall.order.repository.OrderRepository;
import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
public class OrderRepositoryImpl implements OrderRepository {

    @Override
    public Optional<Order> findByOrderId(String orderId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "select * from orders where order_id = ?";
        log.debug("sql:{}", sql);

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, orderId);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Order(
                            rs.getString("order_id"),
                            rs.getString("receiver_name"),
                            rs.getString("receiver_address"),
                            Order.Status.valueOf(rs.getString("order_Status")),
                            rs.getTimestamp("created_at").toLocalDateTime(),
                            Objects.nonNull(rs.getTimestamp("delivered_at")) ? rs.getTimestamp("delivered_at").toLocalDateTime() : null,
                            rs.getString("user_id")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public int save(Order order) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "insert into orders (order_id, receiver_name, receiver_address, order_status, created_at, user_id) values (?, ?, ?, ?, ?, ?)";
        log.debug("sql:{}", sql);

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, order.getOrderId());
            preparedStatement.setString(2, order.getReceiverName());
            preparedStatement.setString(3, order.getReceiverAddress());
            preparedStatement.setString(4, order.getOrderStatus().toString());
            preparedStatement.setString(5, order.getCreatedAt().toString());
            preparedStatement.setString(6, order.getUserId());

            int result = preparedStatement.executeUpdate();
            log.debug("order save : {}", result);
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int deleteByOrderId(String orderId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "delete from orders where order_id = ?";
        log.debug("sql:{}", sql);

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, orderId);

            int result = preparedStatement.executeUpdate();
            log.debug("order delete : {}", result);
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int updateOrderStatusByOrderId(String orderId, Order.Status status) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "update orders set order_status = ? where order_id = ?";
        log.debug("sql:{}", sql);

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, status.toString());
            preparedStatement.setString(2, orderId);

            int result = preparedStatement.executeUpdate();
            log.debug("order status update : {}", result);
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int updateDeliveredAtByOrderId(String orderId, LocalDateTime deliveredAt) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "update orders set delivered_at = ? where order_id = ?";
        log.debug("sql:{}", sql);

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, deliveredAt.toString());
            preparedStatement.setString(2, orderId);

            int result = preparedStatement.executeUpdate();
            log.debug("ordern deliveredAt update : {}", result);
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int countByOrderId(String orderId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "select count(*) from orders where order_id = ?";
        log.debug("sql:{}", sql);
        int count = 0;

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, orderId);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return count;
    }

    @Override
    public long totalCount(String userId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "select count(*) from orders where user_id = ?";
        log.debug("sql : {}", sql);

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, userId);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return 0l;
    }

    @Override
    public Page<Order> findAll(String userId, int page, int pageSize) {

        Connection connection = DbConnectionThreadLocal.getConnection();
        int offset = (page - 1) * pageSize;
        int limit = pageSize;

        String sql = "select * from orders where user_id = ? order by created_at desc limit ?, ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, userId);
            preparedStatement.setInt(2, offset);
            preparedStatement.setInt(3, limit);
            List<Order> orderList = new ArrayList<>(pageSize);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    orderList.add(new Order(
                            rs.getString("order_id"),
                            rs.getString("receiver_name"),
                            rs.getString("receiver_address"),
                            Order.Status.valueOf(rs.getString("order_Status")),
                            rs.getTimestamp("created_at").toLocalDateTime(),
                            Objects.nonNull(rs.getTimestamp("delivered_at")) ? rs.getTimestamp("delivered_at").toLocalDateTime() : null,
                            rs.getString("user_id")
                    ));
                }
            }

            long total = 0;

            if (!orderList.isEmpty()) {
                total = totalCount(userId);
            }

            return new Page<Order>(orderList, total);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
