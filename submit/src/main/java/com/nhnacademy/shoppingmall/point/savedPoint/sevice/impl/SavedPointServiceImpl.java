package com.nhnacademy.shoppingmall.point.savedPoint.sevice.impl;

import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.point.savedPoint.domain.SavedPoint;
import com.nhnacademy.shoppingmall.point.savedPoint.exception.SavedPointAlreadyExistsException;
import com.nhnacademy.shoppingmall.point.savedPoint.exception.SavedPointNotFoundException;
import com.nhnacademy.shoppingmall.point.savedPoint.repository.SavedPointRepository;
import com.nhnacademy.shoppingmall.point.savedPoint.sevice.SavedPointService;

import java.util.List;
import java.util.Optional;

public class SavedPointServiceImpl implements SavedPointService {

    private SavedPointRepository savedPointRepository;

    public SavedPointServiceImpl(SavedPointRepository savedPointRepository) {
        this.savedPointRepository = savedPointRepository;
    }

    @Override
    public SavedPoint getSavedPoint(String savedPointId) {

        if(savedPointId == null) {
            throw new IllegalArgumentException("savedPointId cannot be null");
        }

        Optional<SavedPoint> savedPointOptional = savedPointRepository.findBySavedPointId(savedPointId);
        return savedPointOptional.orElse(null);
    }

    @Override
    public List<SavedPoint> getRecentSavedPoints(String userId, int count) {
        if(count <= 0) {
            throw new IllegalArgumentException("count must be greater than 0");
        }

        return savedPointRepository.findRecentSavedPoints(userId, count);
    }

    @Override
    public void saveSavedPoint(SavedPoint savedPoint) {
        if(savedPointRepository.countBySavedPointId(savedPoint.getSavedPointId()) > 0) {
            throw new SavedPointAlreadyExistsException("Saved point already exists");
        }

        savedPointRepository.save(savedPoint);
    }

    @Override
    public void deleteSavedPoint(String savedPointId) {
        if(savedPointRepository.countBySavedPointId(savedPointId) <= 0) {
            throw new SavedPointNotFoundException("Saved point not found");
        }

        savedPointRepository.deleteBySavedPointId(savedPointId);
    }

    @Override
    public long getOrderTotalCount(String userId) {
        return savedPointRepository.totalCount(userId);
    }

    @Override
    public Page<SavedPoint> getOrders(String userId, int page, int pageSize) {
        return savedPointRepository.findAll(userId, page, pageSize);
    }
}
