<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>회원 관리</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.min.js"></script>
    <style>
        .header {
            padding: 10px;
            background-color: #f8f9fa;
            margin-bottom: 10px;
        }
    </style>
</head>
<body>
<div class="container mt-5">
    <div class="header">
        <h2>관리자 목록</h2>
    </div>
    <div class="list-group">
        <c:forEach items="${admins}" var="admin">
            <div class="list-group-item">
                <div class="row align-items-center">
                    <div class="col-md-10">
                            ${admin.userName} (ID: ${admin.userId})
                    </div>
                    <div class="col-md-2">
                        <form method="get" action="/admin/user/details.do" class="float-right">
                            <input type="hidden" name="userId" value="${admin.userId}"/>
                            <button class="btn btn-primary btn-sm" type="submit">상세보기</button>
                        </form>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>

    <div class="header">
        <h2>사용자 목록</h2>
    </div>
    <div class="list-group">
        <c:forEach items="${userPage.content}" var="user">
            <div class="list-group-item">
                <div class="row align-items-center">
                    <div class="col-md-10">
                            ${user.userName} (ID: ${user.userId})
                    </div>
                    <div class="col-md-2">
                        <form method="get" action="/admin/user/details.do">
                            <input type="hidden" name="userId" value="${user.userId}"/>
                            <button type="submit" class="btn btn-primary btn-sm" type="submit">상세보기</button>
                        </form>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
    <ul class="pagination justify-content-center">
        <c:if test="${currentPage > 1}">
            <li class="page-item"><a class="page-link" href="/admin/userPage.do?page=${currentPage - 1}">이전</a></li>
        </c:if>
        <c:forEach begin="${startPage}" end="${endPage}" var="i">
            <li class="page-item ${i == currentPage ? 'active' : ''}">
                <a class="page-link" href="/admin/userPage.do?page=${i}">${i}</a>
            </li>
        </c:forEach>
        <c:if test="${currentPage < pageCount}">
            <li class="page-item"><a class="page-link" href="/admin/userPage.do?page=${currentPage + 1}">다음</a></li>
        </c:if>
    </ul>
</div>

</body>
</html>
