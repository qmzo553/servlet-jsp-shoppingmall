package com.nhnacademy.shoppingmall.point.usedPoint.repository;

import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.point.savedPoint.domain.SavedPoint;
import com.nhnacademy.shoppingmall.point.usedPoint.domain.UsedPoint;

import java.util.List;
import java.util.Optional;

public interface UsedPointRepository {
    Optional<UsedPoint> findByUsedPointId(String usedPointId);
    int save(UsedPoint usedPoint);
    int deleteByUsedPointId(String usedPointId);
    int countByUsedPointId(String usedPointId);
    List<UsedPoint> findRecentUsedPoints(String userId, int count);

    long totalCount(String userId);

    Page<UsedPoint> findAll(String userId, int page, int pageSize);
}
