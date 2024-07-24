package com.nhnacademy.shoppingmall.point.savedPoint.repository.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.point.savedPoint.domain.SavedPoint;
import com.nhnacademy.shoppingmall.point.savedPoint.repository.SavedPointRepository;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public class SavedPointRepositoryImpl implements SavedPointRepository {
    @Override
    public Optional<SavedPoint> findBySavedPointId(String savedPointId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "select * from saved_point where saved_point_id = ?";
        log.debug("sql:{}",sql);

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, savedPointId);
            try(ResultSet rs = preparedStatement.executeQuery()){
                if(rs.next()){
                    return Optional.of(new SavedPoint(
                            rs.getString("saved_point_id"),
                            rs.getInt("saved_point_amount"),
                            rs.getTimestamp("saved_point_at").toLocalDateTime(),
                            rs.getString("payment_id"),
                            rs.getString("user_id")
                    ));
                }
            }
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public int save(SavedPoint savedPoint) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "insert into saved_point values(?,?,?,?,?)";
        log.debug("sql:{}",sql);

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, savedPoint.getSavedPointId());
            preparedStatement.setInt(2, savedPoint.getSavedPointAmount());
            preparedStatement.setString(3, savedPoint.getSavedPointAt().toString());
            preparedStatement.setString(4, savedPoint.getPaymentId());
            preparedStatement.setString(5, savedPoint.getUserId());

            int result = preparedStatement.executeUpdate();
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int deleteBySavedPointId(String savedPointId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "delete from saved_point where saved_point_id = ?";
        log.debug("sql:{}",sql);

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, savedPointId);

            int result = preparedStatement.executeUpdate();
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int countBySavedPointId(String savedPointId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "select count(*) from saved_point where saved_point_id = ?";
        log.debug("sql:{}",sql);
        int count = 0;

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, savedPointId);
            try(ResultSet rs = preparedStatement.executeQuery()){
                if(rs.next()){
                    count = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return count;
    }

    @Override
    public List<SavedPoint> findRecentSavedPoints(String userId, int count) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "select * from saved_point where user_id = ? order by saved_point_at desc limit ?";
        log.debug("sql:{}",sql);
        List<SavedPoint> savedPoints = new ArrayList<>();

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, userId);
            preparedStatement.setInt(2, count);

            try(ResultSet rs = preparedStatement.executeQuery()){
                while(rs.next()){
                    savedPoints.add(new SavedPoint(
                            rs.getString("saved_point_id"),
                            rs.getInt("saved_point_amount"),
                            rs.getTimestamp("saved_point_at").toLocalDateTime(),
                            rs.getString("payment_id"),
                            rs.getString("user_id")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return savedPoints;
    }

    @Override
    public long totalCount(String userId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "select count(*) from saved_point where user_id = ?";
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
    public Page<SavedPoint> findAll(String userId, int page, int pageSize) {

        Connection connection = DbConnectionThreadLocal.getConnection();
        int offset = (page - 1) * pageSize;
        int limit = pageSize;

        String sql = "select * from saved_point where user_id = ? order by saved_point_at desc limit ?, ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, userId);
            preparedStatement.setInt(2, offset);
            preparedStatement.setInt(3, limit);
            List<SavedPoint> savedPointList = new ArrayList<>(pageSize);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    savedPointList.add(new SavedPoint(
                            rs.getString("saved_point_id"),
                            rs.getInt("saved_point_amount"),
                            rs.getTimestamp("saved_point_at").toLocalDateTime(),
                            rs.getString("payment_id"),
                            rs.getString("user_id")
                    ));
                }
            }

            long total = 0;

            if (!savedPointList.isEmpty()) {
                total = totalCount(userId);
            }

            return new Page<SavedPoint>(savedPointList, total);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
