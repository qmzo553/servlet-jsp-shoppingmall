package com.nhnacademy.shoppingmall.thread.request.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.point.savedPoint.domain.SavedPoint;
import com.nhnacademy.shoppingmall.point.usedPoint.domain.UsedPoint;
import com.nhnacademy.shoppingmall.thread.request.ChannelRequest;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.repository.impl.UserRepositoryImpl;
import com.nhnacademy.shoppingmall.user.service.UserService;
import com.nhnacademy.shoppingmall.user.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PointChannelRequest extends ChannelRequest {

    private final UserService userService = new UserServiceImpl(new UserRepositoryImpl());
    private User user;
    private SavedPoint savedPoint;
    private UsedPoint usedPoint;

    public PointChannelRequest(User user, SavedPoint savedPoint, UsedPoint usedPoint) {
        this.user = user;
        this.savedPoint = savedPoint;
        this.usedPoint = usedPoint;
    }

    @Override
    public void execute() {
        DbConnectionThreadLocal.initialize();
        // 포인트 적립구현, connection은 point적립이 완료되면 반납합니다.
        int userPoint = user.getUserPoint();
        int savedPointAmount = savedPoint.getSavedPointAmount();
        int usedPointAmount = usedPoint.getUsedPointAmount();

        int totalPoint = userPoint + savedPointAmount - usedPointAmount;
        user.setUserPoint(totalPoint);
        userService.updateUser(user);
        log.debug("pointChannel execute");
        DbConnectionThreadLocal.reset();
    }
}
