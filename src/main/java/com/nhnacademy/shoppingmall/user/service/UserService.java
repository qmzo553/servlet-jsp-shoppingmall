package com.nhnacademy.shoppingmall.user.service;

import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.user.domain.User;

import java.util.List;

public interface UserService {

    User getUser(String userId);

    List<User> getUsersByAuth(User.Auth auth);

    void saveUser(User user);

    void updateUser(User user);

    void deleteUser(String userId);

    User doLogin(String userId, String userPassword);

    long getAuthTotalCount(User.Auth userAuth);

    Page<User> getUsers(User.Auth userAuth, int page, int pageSize);
}
