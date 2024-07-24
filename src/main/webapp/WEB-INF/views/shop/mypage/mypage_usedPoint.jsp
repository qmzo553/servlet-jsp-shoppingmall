<%--
  Created by IntelliJ IDEA.
  User: parkheejun
  Date: 5/12/24
  Time: 12:33 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>사용한 포인트 내역</title>
</head>
<body>
<div class="container mt-4">
    <h4>사용한 포인트 내역</h4>
    <div class="list-group">
        <c:forEach items="${usedPointPage.content}" var="usedPoint">
            <div class="list-group-item">
                <div class="row align-items-center">
                    <div class="col-md-3">
                        포인트: ${usedPoint.usedPointAmount}
                    </div>
                    <div class="col-md-3">
                        사용 날짜: ${usedPoint.usedPointAt}
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
    <ul class="pagination justify-content-center">
        <c:if test="${currentPage > 1}">
            <li class="page-item"><a class="page-link" href="/mypage/usedPoint.do?page=${currentPage - 1}">이전</a></li>
        </c:if>
        <c:forEach begin="${startPage}" end="${endPage}" var="i">
            <li class="page-item ${i == currentPage ? 'active' : ''}">
                <a class="page-link" href="/mypage/usedPoint.do?page=${i}">${i}</a>
            </li>
        </c:forEach>
        <c:if test="${currentPage < pageCount}">
            <li class="page-item"><a class="page-link" href="/mypage/usedPoint.do?page=${currentPage + 1}">다음</a></li>
        </c:if>
    </ul>
    <button type="button" class="btn btn-secondary" onclick="window.location.href='/mypage.do'">뒤로 가기</button>
</div>
</body>
</html>
