<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>적립된 포인트</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-4">
    <h4>적립된 포인트 내역</h4>
    <div class="list-group">
        <c:forEach items="${savedPointPage.content}" var="savedPoint">
            <div class="list-group-item">
                <div class="row align-items-center">
                    <div class="col-md-6">
                        포인트: ${savedPoint.savedPointAmount}
                    </div>
                    <div class="col-md-6">
                        적립 날짜: ${savedPoint.savedPointAt}
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
    <ul class="pagination justify-content-center">
        <c:if test="${currentPage > 1}">
            <li class="page-item"><a class="page-link" href="/mypage/savedPoint.do?page=${currentPage - 1}">이전</a></li>
        </c:if>
        <c:forEach begin="${startPage}" end="${endPage}" var="i">
            <li class="page-item ${i == currentPage ? 'active' : ''}">
                <a class="page-link" href="/mypage/savedPoint.do?page=${i}">${i}</a>
            </li>
        </c:forEach>
        <c:if test="${currentPage < pageCount}">
            <li class="page-item"><a class="page-link" href="/mypage/savedPoint.do?page=${currentPage + 1}">다음</a></li>
        </c:if>
    </ul>
    <button type="button" class="btn btn-secondary mt-3" onclick="window.location.href='/mypage.do'">뒤로 가기</button>
</div>
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
