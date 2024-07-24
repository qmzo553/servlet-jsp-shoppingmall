<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>My Orders</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-4">
    <h2>주문 명세 조회</h2>
    <table class="table table-hover">
        <thead class="thead-dark">
        <tr>
            <th>받는 사람 이름</th>
            <th>받을 주소</th>
            <th>주문 상태</th>
            <th>주문 날짜</th>
            <th>배송 날짜</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${orderPage.content}" var="order">
            <tr>
                <td>${order.receiverName}</td>
                <td>${order.receiverAddress}</td>
                <td>${order.orderStatus}</td>
                <td>${order.createdAt}</td>
                <td>${order.deliveredAt}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <!-- Pagination -->
    <ul class="pagination justify-content-center">
        <c:if test="${currentPage > 1}">
            <li class="page-item"><a class="page-link" href="/mypage/order.do?page=${currentPage - 1}">이전</a></li>
        </c:if>
        <c:forEach begin="${startPage}" end="${endPage}" var="i">
            <li class="page-item ${i == currentPage ? 'active' : ''}">
                <a class="page-link" href="/mypage/order.do?page=${i}">${i}</a>
            </li>
        </c:forEach>
        <c:if test="${currentPage < pageCount}">
            <li class="page-item"><a class="page-link" href="/mypage/order.do?page=${currentPage + 1}">다음</a></li>
        </c:if>
    </ul>
    <button type="button" class="btn btn-secondary" onclick="window.location.href='/mypage.do'">뒤로 가기</button>
</div>
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
