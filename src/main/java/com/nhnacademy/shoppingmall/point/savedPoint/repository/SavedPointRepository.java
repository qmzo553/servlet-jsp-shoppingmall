package com.nhnacademy.shoppingmall.point.savedPoint.repository;

import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.point.savedPoint.domain.SavedPoint;

import java.util.List;
import java.util.Optional;

public interface SavedPointRepository {
    Optional<SavedPoint> findBySavedPointId(String savedPointId);
    int save(SavedPoint savedPoint);
    int deleteBySavedPointId(String savedPointId);
    int countBySavedPointId(String savedPointId);
    List<SavedPoint> findRecentSavedPoints(String userId, int count);

    long totalCount(String userId);

    Page<SavedPoint> findAll(String userId, int page, int pageSize);
}
