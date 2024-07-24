package com.nhnacademy.shoppingmall.point.usedPoint.repository.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.point.usedPoint.domain.UsedPoint;
import com.nhnacademy.shoppingmall.point.usedPoint.repository.UsedPointRepository;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public class UsedPointRepositoryImpl implements UsedPointRepository {

    @Override
    public Optional<UsedPoint> findByUsedPointId(String usedPointId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "select * from used_point where used_point_id = ?";
        log.debug("sql: {}", sql);

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, usedPointId);

            try(ResultSet rs = preparedStatement.executeQuery()) {
                if(rs.next()) {
                    return Optional.of(new UsedPoint(
                            rs.getString("used_point_id"),
                            rs.getInt("used_point_amount"),
                            rs.getTimestamp("used_point_at").toLocalDateTime(),
                            rs.getString("payment_id"),
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
    public int save(UsedPoint usedPoint) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "insert into used_point values (?, ?, ?, ?, ?)";
        log.debug("sql: {}", sql);

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, usedPoint.getUsedPointId());
            preparedStatement.setInt(2, usedPoint.getUsedPointAmount());
            preparedStatement.setString(3, usedPoint.getUsedPointAt().toString());
            preparedStatement.setString(4, usedPoint.getPaymentId());
            preparedStatement.setString(5, usedPoint.getUserId());

            int result = preparedStatement.executeUpdate();
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int deleteByUsedPointId(String usedPointId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "delete from used_point where used_point_id = ?";
        log.debug("sql: {}", sql);

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, usedPointId);

            int result = preparedStatement.executeUpdate();
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int countByUsedPointId(String usedPointId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "select count(*) from used_point where used_point_id = ?";
        log.debug("sql: {}", sql);
        int count = 0;
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, usedPointId);

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

    @Override
    public List<UsedPoint> findRecentUsedPoints(String userId, int count) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "select * from used_point where user_id = ? order by used_point_at desc limit ?";
        log.debug("sql:{}",sql);
        List<UsedPoint> usedPoints = new ArrayList<>();

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, userId);
            preparedStatement.setInt(2, count);

            try(ResultSet rs = preparedStatement.executeQuery()){
                while(rs.next()){
                    usedPoints.add(new UsedPoint(
                            rs.getString("used_point_id"),
                            rs.getInt("used_point_amount"),
                            rs.getTimestamp("used_point_at").toLocalDateTime(),
                            rs.getString("payment_id"),
                            rs.getString("user_id")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return usedPoints;
    }

    @Override
    public long totalCount(String userId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "select count(*) from used_point where user_id = ?";
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
    public Page<UsedPoint> findAll(String userId, int page, int pageSize) {

        Connection connection = DbConnectionThreadLocal.getConnection();
        int offset = (page - 1) * pageSize;
        int limit = pageSize;

        String sql = "select * from used_point where user_id = ? order by used_point_at desc limit ?, ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, userId);
            preparedStatement.setInt(2, offset);
            preparedStatement.setInt(3, limit);
            List<UsedPoint> usedPointList = new ArrayList<>(pageSize);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    usedPointList.add(new UsedPoint(
                            rs.getString("used_point_id"),
                            rs.getInt("used_point_amount"),
                            rs.getTimestamp("used_point_at").toLocalDateTime(),
                            rs.getString("payment_id"),
                            rs.getString("user_id")
                    ));
                }
            }

            long total = 0;

            if (!usedPointList.isEmpty()) {
                total = totalCount(userId);
            }

            return new Page<UsedPoint>(usedPointList, total);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
