package com.nhnacademy.shoppingmall.address.repository.impl;

import com.nhnacademy.shoppingmall.address.domain.Address;
import com.nhnacademy.shoppingmall.address.repository.AddressRepository;
import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public class AddressRepositoryImpl implements AddressRepository {

    @Override
    public Optional<Address> findByAddressId(int addressId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "SELECT * FROM address WHERE address_id = ?";
        log.debug("sql:{}", sql);

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, addressId);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Address(
                            rs.getInt("address_id"),
                            rs.getString("address_name"),
                            rs.getString("address_detail"),
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
    public List<Address> findByUserId(String userId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "SELECT * FROM address WHERE user_id = ?";
        log.debug("sql:{}", sql);
        List<Address> addressList = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, userId);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    addressList.add(new Address(
                            rs.getInt("address_id"),
                            rs.getString("address_name"),
                            rs.getString("address_detail"),
                            rs.getString("user_id")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return addressList;
    }

    @Override
    public int save(Address address) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "INSERT INTO address(address_name, address_detail, user_id) VALUES(?, ?, ?)";
        log.debug("sql:{}", sql);

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, address.getAddressName());
            preparedStatement.setString(2, address.getAddressDetail());
            preparedStatement.setString(3, address.getUserId());

            int result = preparedStatement.executeUpdate();
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int deleteByAddressId(int addressId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "DELETE FROM address WHERE address_id = ?";
        log.debug("sql:{}", sql);

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, addressId);

            int result = preparedStatement.executeUpdate();
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int deleteByUserId(String userId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "DELETE FROM address WHERE user_id = ?";
        log.debug("sql:{}", sql);

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, userId);

            int result = preparedStatement.executeUpdate();
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int findLastAddressId() {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "select max(address_id) from address";
        log.debug("sql:{}", sql);
        int addressId = 0;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    addressId = rs.getInt(1);
                }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return addressId;
    }

    @Override
    public int countByAddressId(int addressId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "SELECT count(*) FROM address WHERE address_id = ?";
        log.debug("sql:{}", sql);
        int count = 0;

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, addressId);
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
    public int countByUserId(String userId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "SELECT count(*) FROM address WHERE user_id = ?";
        log.debug("sql:{}", sql);
        int count = 0;

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, userId);
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
}
