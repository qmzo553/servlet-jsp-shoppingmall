<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>마이페이지</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.min.js"></script>
    <style>
        .section-padding {
            padding-top: 20px;
            padding-bottom: 20px;
        }
        .header-line {
            background-color: #f8f9fa;
            border-bottom: 1px solid #e9ecef;
            padding: 10px 15px;
        }
    </style>
</head>
<body>
<div class="container mt-5">
<div class="header-line">
    <h4>기본 정보</h4>
</div>
<table class="table table-striped">
    <tbody>
    <tr>
        <th>ID</th>
        <td>${user.userId}</td>
    </tr>
    <tr>
        <th>이름</th>
        <td>${user.userName}</td>
    </tr>
    <tr>
        <th>비밀번호</th>
        <td>${user.userPassword}</td>
    </tr>
    <tr>
        <th>생년월일</th>
        <td>${user.userBirth}</td>
    </tr>
    <tr>
        <th>보유포인트</th>
        <td>${user.userPoint}</td>
    </tr>
    <tr>
        <th>회원등급</th>
        <td>${user.userAuth}</td>
    </tr>
    <tr>
        <th>가입날짜</th>
        <td>${user.createdAt}</td>
    </tr>
    </tbody>
</table>

<div class="header-line">
    <h4>주소 목록</h4>
    <button type="button" class="btn btn-primary btn-sm" data-toggle="modal" data-target="#addressModal">
        주소 추가
    </button>
</div>
<div class="list-group">
    <c:forEach items="${addresses}" var="address">
        <div class="list-group-item">
            <div class="row align-items-center">
                <div class="col-md-3">
                    주소 이름: ${address.addressName}
                </div>
                <div class="col-md-6">
                    상세 주소: ${address.addressDetail}
                </div>
                <div class="col-md-3">
                    <form method="post" action="/address/delete.do">
                        <input type="hidden" name="addressId" value="${address.addressId}" />
                        <button type="submit" class="btn btn-danger btn-sm">삭제</button>
                    </form>
                </div>
            </div>
        </div>
    </c:forEach>
</div>

<div class="header-line">
    <h4>장바구니</h4>
</div>
<c:choose>
    <c:when test="${empty cart}">
        <p>장바구니가 비었습니다.</p>
    </c:when>
    <c:otherwise>
        <div class="list-group">
            <c:forEach items="${cart}" var="product">
                <div class="list-group-item">
                    <div class="row align-items-center">
                        <div class="col-md-2">
                            <img src="${not empty product.productImg ? product.productImg : '/resources/no-image.png'}"
                                 alt="${product.productName}"
                                 style="width: 100%; max-height: 70px; object-fit: contain;">
                        </div>
                        <div class="col-md-3">
                                ${product.productName}
                        </div>
                        <div class="col-md-2">
                            <label for="price_${product.productId}">가격:</label>
                            $<span id="price_${product.productId}">${product.productPrice}</span>
                        </div>
                        <div class="col-md-2">
                            <label for="quantity_${product.productId}">수량:</label>
                            <input type="number" class="form-control form-control-sm" id="quantity_${product.productId}" value="1" min="1"
                                   oninput="updatePrice('${product.productId}', ${product.productPrice})">
                        </div>
                        <div class="col-md-1">
                            <button type="submit" class="btn btn-primary btn-sm" formaction="/product/view.do" formmethod="get">
                                <input type="hidden" name="productId" value="${product.productId}" />자세히보기
                            </button>
                        </div>
                        <div class="col-md-2">
                            <button type="submit" class="btn btn-danger btn-sm" formaction="/cart/delete.do" formmethod="post">
                                <input type="hidden" name="productId" value="${product.productId}" />삭제
                            </button>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </c:otherwise>
</c:choose>
</body>
<div class="btn-group" role="group">
    <a href="/mypage/order.do" class="btn btn-primary">주문 명세 조회</a>
    <a href="/mypage/savedPoint.do" class="btn btn-secondary">포인트 적립 내역</a>
    <a href="/mypage/usedPoint.do" class="btn btn-info">사용한 포인트 내역</a>
</div>
</html>
