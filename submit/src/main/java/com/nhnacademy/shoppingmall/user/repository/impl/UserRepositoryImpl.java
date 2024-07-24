package com.nhnacademy.shoppingmall.user.repository.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
public class UserRepositoryImpl implements UserRepository {

    @Override
    public Optional<User> findByUserIdAndUserPassword(String userId, String userPassword) {
        /*
          회원의 아이디와 비밀번호를 이용해서 조회하는 코드 입니다.(로그인)
          해당 코드는 SQL Injection이 발생합니다. SQL Injection이 발생하지 않도록 수정하세요.
         */
        Connection connection = DbConnectionThreadLocal.getConnection();
//        String sql =String.format("select user_id, user_name, user_password, user_birth, user_auth, user_point, created_at, latest_login_at from users where user_id='%s' and user_password ='%s'",
//                userId,
//                userPassword
//        );
        String sql = "select user_id, user_name, user_password, user_birth, user_auth, user_point, created_at, latest_login_at from users where user_id = ? and user_password = ?";
        log.debug("sql:{}",sql);

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, userId);
            preparedStatement.setString(2, userPassword);

            try(ResultSet rs = preparedStatement.executeQuery()) {
                if(rs.next()){
                    User user = new User(
                            rs.getString("user_id"),
                            rs.getString("user_name"),
                            rs.getString("user_password"),
                            rs.getString("user_birth"),
                            User.Auth.valueOf(rs.getString("user_auth")),
                            rs.getInt("user_point"),
                            Objects.nonNull(rs.getTimestamp("created_at")) ? rs.getTimestamp("created_at").toLocalDateTime() : null,
                            Objects.nonNull(rs.getTimestamp("latest_login_at")) ? rs.getTimestamp("latest_login_at").toLocalDateTime() : null
                    );
                    log.debug("user:{}",user);
                    return Optional.of(user);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    @Override
    public Optional<User> findById(String userId) {
        // 회원조회
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "select user_id, user_name, user_password, user_birth, user_auth, user_point, created_at, latest_login_at from users where user_id = ?";
        log.debug("sql:{}",sql);

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, userId);

            try(ResultSet rs = preparedStatement.executeQuery()) {
                if(rs.next()){
                    User user = new User(
                            rs.getString("user_id"),
                            rs.getString("user_name"),
                            rs.getString("user_password"),
                            rs.getString("user_birth"),
                            User.Auth.valueOf(rs.getString("user_auth")),
                            rs.getInt("user_point"),
                            Objects.nonNull(rs.getTimestamp("created_at")) ? rs.getTimestamp("created_at").toLocalDateTime() : null,
                            Objects.nonNull(rs.getTimestamp("latest_login_at")) ? rs.getTimestamp("latest_login_at").toLocalDateTime() : null
                    );
                    log.debug("user:{}",user);
                    return Optional.of(user);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    @Override
    public List<User> findByAuth(User.Auth auth) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "select * from users where user_auth = ?";
        log.debug("sql:{}",sql);
        List<User> users = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, auth.name());
            try(ResultSet rs = preparedStatement.executeQuery()) {
                while(rs.next()){
                    users.add(new User(
                            rs.getString("user_id"),
                            rs.getString("user_name"),
                            rs.getString("user_password"),
                            rs.getString("user_birth"),
                            User.Auth.valueOf(rs.getString("user_auth")),
                            rs.getInt("user_point"),
                            Objects.nonNull(rs.getTimestamp("created_at")) ? rs.getTimestamp("created_at").toLocalDateTime() : null,
                            Objects.nonNull(rs.getTimestamp("latest_login_at")) ? rs.getTimestamp("latest_login_at").toLocalDateTime() : null
                    ));
                }
            }
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    @Override
    public int save(User user) {
        // 회원등록, executeUpdate()을 반환합니다.
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "insert into users (user_id, user_name, user_password, user_birth, user_auth, user_point, created_at) values (?, ?, ?, ?, ?, ?, ?)";
        log.debug("sql:{}",sql);

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, user.getUserId());
            preparedStatement.setString(2, user.getUserName());
            preparedStatement.setString(3, user.getUserPassword());
            preparedStatement.setString(4, user.getUserBirth());
            preparedStatement.setString(5, user.getUserAuth().toString());
            preparedStatement.setLong(6, user.getUserPoint());
            preparedStatement.setString(7, user.getCreatedAt().toString());

            int result = preparedStatement.executeUpdate();
            log.debug("save result:{}",result);
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int deleteByUserId(String userId) {
        // 회원삭제, executeUpdate()을 반환합니다.
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "delete from users where user_id = ?";
        log.debug("sql:{}",sql);

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, userId);

            int result = preparedStatement.executeUpdate();
            log.debug("delete result:{}",result);
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int update(User user) {
        // 회원수정, executeUpdate()을 반환합니다.
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "update users set user_name = ?, user_password = ?, user_birth = ?, user_auth = ?, user_point = ? where user_id = ?";
        log.debug("sql:{}",sql);

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, user.getUserName());
            preparedStatement.setString(2, user.getUserPassword());
            preparedStatement.setString(3, user.getUserBirth());
            preparedStatement.setString(4, user.getUserAuth().toString());
            preparedStatement.setLong(5, user.getUserPoint());
            preparedStatement.setString(6, user.getUserId());

            int result = preparedStatement.executeUpdate();
            log.debug("update result:{}",result);
            return result;
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int updateLatestLoginAtByUserId(String userId, LocalDateTime latestLoginAt) {
        // 마지막 로그인 시간 업데이트, executeUpdate()을 반환합니다.
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "update users set latest_login_at = ? where user_id = ?";
        log.debug("sql:{}",sql);

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, latestLoginAt.toString());
            preparedStatement.setString(2, userId);

            int result = preparedStatement.executeUpdate();
            log.debug("updateLatestLoginAtByUserId result:{}",result);
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int countByUserId(String userId) {
        // userId와 일치하는 회원의 count를 반환합니다.
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "select count(*) from users where user_id = ?";
        log.debug("sql:{}",sql);
        int count = 0;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, userId);
            try(ResultSet rs = preparedStatement.executeQuery()) {
                if(rs.next()){
                    count = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        log.debug("countByUserId count:{}",count);
        return count;
    }

    @Override
    public long totalCount(User.Auth userAuth) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "select count(*) from users where user_auth = ?";
        log.debug("sql : {}", sql);

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, userAuth.name());
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
    public Page<User> findAll(User.Auth userAuth, int page, int pageSize) {

        Connection connection = DbConnectionThreadLocal.getConnection();
        int offset = (page - 1) * pageSize;
        int limit = pageSize;

        String sql = "select * from users where user_auth = ? order by created_at desc limit ?, ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, userAuth.name());
            preparedStatement.setInt(2, offset);
            preparedStatement.setInt(3, limit);
            List<User> userList = new ArrayList<>(pageSize);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    userList.add(new User(
                            rs.getString("user_id"),
                            rs.getString("user_name"),
                            rs.getString("user_password"),
                            rs.getString("user_birth"),
                            User.Auth.valueOf(rs.getString("user_auth")),
                            rs.getInt("user_point"),
                            Objects.nonNull(rs.getTimestamp("created_at")) ? rs.getTimestamp("created_at").toLocalDateTime() : null,
                            Objects.nonNull(rs.getTimestamp("latest_login_at")) ? rs.getTimestamp("latest_login_at").toLocalDateTime() : null
                    ));
                }
            }

            long total = 0;

            if (!userList.isEmpty()) {
                total = totalCount(userAuth);
            }

            return new Page<User>(userList, total);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
