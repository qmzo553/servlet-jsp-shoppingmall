package com.nhnacademy.shoppingmall.user.service.impl;

import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.user.exception.UserAlreadyExistsException;
import com.nhnacademy.shoppingmall.user.exception.UserNotFoundException;
import com.nhnacademy.shoppingmall.user.service.UserService;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUser(String userId){
        // 회원조회
        if(userId == null) {
            throw new RuntimeException();
        }

        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()) {
            return user.get();
        }

        return null;
    }

    @Override
    public List<User> getUsersByAuth(User.Auth auth) {
        return userRepository.findByAuth(auth);
    }

    @Override
    public void saveUser(User user) {
        // 회원등록
        if(userRepository.countByUserId(user.getUserId()) > 0) {
            throw new UserAlreadyExistsException("User already exists");
        }

        userRepository.save(user);
    }

    @Override
    public void updateUser(User user) {
        // 회원수정
        if(userRepository.countByUserId(user.getUserId()) == 0) {
            throw new UserNotFoundException("User does not exist");
        }
        userRepository.update(user);
    }

    @Override
    public void deleteUser(String userId) {
        // 회원삭제
        if(userRepository.countByUserId(userId) == 0) {
            throw new UserNotFoundException("User does not exist");
        }

        userRepository.deleteByUserId(userId);
    }

    @Override
    public User doLogin(String userId, String userPassword) {
        // 로그인 구현, userId, userPassword로 일치하는 회원 조회
        Optional<User> user = userRepository.findByUserIdAndUserPassword(userId, userPassword);

        if(user.isPresent()) {
            userRepository.updateLatestLoginAtByUserId(userId, LocalDateTime.now());
            return user.get();
        } else {
            throw new UserNotFoundException("User does not exist");
        }
    }

    @Override
    public long getAuthTotalCount(User.Auth userAuth) {
        return userRepository.totalCount(userAuth);
    }

    @Override
    public Page<User> getUsers(User.Auth userAuth, int page, int pageSize) {
        return userRepository.findAll(userAuth, page, pageSize);
    }

}
