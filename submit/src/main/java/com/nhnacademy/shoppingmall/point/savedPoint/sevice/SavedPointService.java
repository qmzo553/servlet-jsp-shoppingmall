package com.nhnacademy.shoppingmall.point.savedPoint.sevice;

import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.point.savedPoint.domain.SavedPoint;

import java.util.List;

public interface SavedPointService {
    SavedPoint getSavedPoint(String savedPointId);

    List<SavedPoint> getRecentSavedPoints(String userId, int count);

    void saveSavedPoint(SavedPoint savedPoint);
    void deleteSavedPoint(String savedPointId);

    long getOrderTotalCount(String userId);

    Page<SavedPoint> getOrders(String userId, int page, int pageSize);
}
