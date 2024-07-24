package com.nhnacademy.shoppingmall.point.usedPoint.service.impl;

import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.point.savedPoint.exception.SavedPointAlreadyExistsException;
import com.nhnacademy.shoppingmall.point.savedPoint.exception.SavedPointNotFoundException;
import com.nhnacademy.shoppingmall.point.usedPoint.domain.UsedPoint;
import com.nhnacademy.shoppingmall.point.usedPoint.repository.UsedPointRepository;
import com.nhnacademy.shoppingmall.point.usedPoint.service.UsedPointService;

import java.util.List;
import java.util.Optional;

public class UsedPointServiceImpl implements UsedPointService {

    private UsedPointRepository usedPointRepository;

    public UsedPointServiceImpl(UsedPointRepository usedPointRepository) {
        this.usedPointRepository = usedPointRepository;
    }

    @Override
    public UsedPoint getUsedPoint(String usedPointId) {
        if (usedPointId == null) {
            return null;
        }

        Optional<UsedPoint> usedPointOptional = usedPointRepository.findByUsedPointId(usedPointId);
        return usedPointOptional.orElse(null);
    }

    @Override
    public List<UsedPoint> getRecentUsedPoints(String userId, int count) {
        if(count <= 0) {
            throw new IllegalArgumentException("Count must be greater than 0");
        }

        return usedPointRepository.findRecentUsedPoints(userId, count);
    }

    @Override
    public void saveUsedPoint(UsedPoint usedPoint) {
        if (usedPointRepository.countByUsedPointId(usedPoint.getUsedPointId()) > 0) {
            throw new SavedPointAlreadyExistsException("Saved point already exists");
        }

        usedPointRepository.save(usedPoint);
    }

    @Override
    public void deleteUsedPoint(String usedPointId) {
        if(usedPointRepository.countByUsedPointId(usedPointId) <= 0) {
            throw new SavedPointNotFoundException("Saved point not found");
        }

        usedPointRepository.deleteByUsedPointId(usedPointId);
    }

    @Override
    public long getUsedPointTotalCount(String userId) {
        return usedPointRepository.totalCount(userId);
    }

    @Override
    public Page<UsedPoint> getUsedPoints(String userId, int page, int pageSize) {
        return usedPointRepository.findAll(userId, page, pageSize);
    }
}
