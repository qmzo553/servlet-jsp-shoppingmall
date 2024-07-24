package com.nhnacademy.shoppingmall.user.repository;

import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.user.domain.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserRepository {
    Optional<User> findByUserIdAndUserPassword(String userId, String userPassword);
    Optional<User> findById(String userId);

    List<User> findByAuth(User.Auth auth);

    int save(User user);
    int deleteByUserId(String userId);
    int update(User user);
    int updateLatestLoginAtByUserId(String userId, LocalDateTime latestLoginAt);
    int countByUserId(String userId);

    long totalCount(User.Auth userAuth);

    Page<User> findAll(User.Auth userAuth, int page, int pageSize);
}
