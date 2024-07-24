package com.nhnacademy.shoppingmall.point.savedPoint.exception;

public class SavedPointNotFoundException extends RuntimeException {
    public SavedPointNotFoundException(String savedPointId) {super(String.format("Saved point with id %s not found", savedPointId));}
}
