package com.nhnacademy.shoppingmall.point.usedPoint.service;

import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.point.usedPoint.domain.UsedPoint;

import java.util.List;

public interface UsedPointService {

    UsedPoint getUsedPoint(String usedPointId);

    List<UsedPoint> getRecentUsedPoints(String userId, int count);

    void saveUsedPoint(UsedPoint usedPoint);
    void deleteUsedPoint(String usedPointId);

    long getUsedPointTotalCount(String userId);

    Page<UsedPoint> getUsedPoints(String userId, int page, int pageSize);
}
