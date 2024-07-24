package com.nhnacademy.shoppingmall.payment.repository.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.payment.domain.Payment;
import com.nhnacademy.shoppingmall.payment.repository.PaymentRepository;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Slf4j
public class PaymentRepositoryImpl implements PaymentRepository {
    @Override
    public Optional<Payment> findByPaymentId(String paymentId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "SELECT * FROM payments WHERE payment_id = ?";
        log.debug("sql : {}", sql);

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, paymentId);

            try(ResultSet rs = preparedStatement.executeQuery()) {
                if(rs.next()) {
                    return Optional.of(new Payment(
                            rs.getString("payment_id"),
                            rs.getInt("payment_total"),
                            rs.getInt("payment_discount"),
                            rs.getInt("payment_delivery"),
                            rs.getInt("payment_final"),
                            rs.getTimestamp("payment_at").toLocalDateTime(),
                            rs.getString("order_id")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    @Override
    public int save(Payment payment) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "insert into payments values(?,?,?,?,?,?,?)";
        log.debug("sql : {}", sql);

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, payment.getPaymentId());
            preparedStatement.setInt(2, payment.getPaymentTotal());
            preparedStatement.setInt(3, payment.getPaymentDiscount());
            preparedStatement.setInt(4, payment.getPaymentDelivery());
            preparedStatement.setInt(5, payment.getPaymentFinal());
            preparedStatement.setString(6, payment.getPaymentAt().toString());
            preparedStatement.setString(7, payment.getOrderId());

            int result = preparedStatement.executeUpdate();
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int deleteByPaymentId(String paymentId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "delete from payments where payment_id = ?";
        log.debug("sql : {}", sql);

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, paymentId);
            int result = preparedStatement.executeUpdate();
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int countByPaymentId(String paymentId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "select count(*) from payments where payment_id = ?";
        log.debug("sql : {}", sql);
        int count = 0;

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, paymentId);

            try(ResultSet rs = preparedStatement.executeQuery()) {
                if(rs.next()) {
                    count =rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return count;
    }
}
