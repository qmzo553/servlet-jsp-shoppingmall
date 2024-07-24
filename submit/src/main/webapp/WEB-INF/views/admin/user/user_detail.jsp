<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>회원 상세 정보</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>
<div class="container mt-4">
    <h2>회원 상세 정보</h2>
    <div class="card">
        <div class="card-body">
            <h4 class="card-title">${user.userName}의 정보</h4>
            <p class="card-text"><strong>ID:</strong> ${user.userId}</p>
            <p class="card-text"><strong>이름:</strong> ${user.userName}</p>
            <p class="card-text"><strong>비밀번호:</strong> ${user.userPassword}</p>
            <p class="card-text"><strong>생년월일:</strong> ${user.userBirth}</p>
            <p class="card-text"><strong>회원 등급:</strong> ${user.userAuth}</p>
            <p class="card-text"><strong>보유포인트:</strong> ${user.userPoint}</p>
            <p class="card-text"><strong>가입 날짜:</strong> ${user.createdAt}</p>
            <p class="card-text"><strong>최근 로그인:</strong> ${user.latestLoginAt}</p>
            <button type="button" class="btn btn-secondary" onclick="window.location.href='/admin/userPage.do'">뒤로 가기</button>
        </div>
    </div>
</div>
</body>
</html>
