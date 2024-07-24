package com.nhnacademy.shoppingmall.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

public class OrderIdUtils {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMdd");
    private static final AtomicInteger sequence = new AtomicInteger(1);
    private static final int MAX_SEQUENCE = 0xFFFF; // 16진수로 FFFF까지 가능 (65535)

    // 인스턴스 생성 방지
    private OrderIdUtils() {
        throw new IllegalStateException("Utility class");
    }

    // 주문번호 생성 메소드
    public static String generateOrderId() {
        // 오늘 날짜를 yyMMdd 형식으로 포맷
        String date = dateFormat.format(new Date());
        // 시퀀스 번호를 16진수로 변환
        String hexSequence = toHexadecimal(sequence.getAndIncrement());
        // 주문번호 조합
        return date + hexSequence;
    }

    // 숫자를 16진수 문자열로 변환, 항상 4자리 유지
    private static String toHexadecimal(int num) {
        if (num > MAX_SEQUENCE) {
            sequence.set(1); // 시퀀스 리셋
            num = 1;
        }
        return String.format("%04X", num);
    }
}
