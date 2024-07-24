package com.nhnacademy.shoppingmall.point.savedPoint.exception;

public class SavedPointAlreadyExistsException extends RuntimeException {
    public SavedPointAlreadyExistsException(String savedPointId) {super(String.format("Saved Point already exist:%s",savedPointId));}
}
