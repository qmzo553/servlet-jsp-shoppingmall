<%--
  Created by IntelliJ IDEA.
  User: parkheejun
  Date: 5/1/24
  Time: 11:15 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>상품등록</title>
    <link rel="stylesheet" href="/style.css" />
    <meta charset="UTF-8" />
</head>

<body>
<c:choose>
    <c:when test="${empty product}">
        <c:set var="action" value="/admin/product/register.do" />
    </c:when>
    <c:otherwise>
        <c:set var="action" value="/admin/product/update.do" />
    </c:otherwise>
</c:choose>

<form method="post" action="${action}" enctype="multipart/form-data">
    <input type="hidden" name="productId" value="${product.productId}">
    <table>
        <tbody>
        <tr>
            <th>상품이름</th>
            <td><input type="text" name="productName" value="${product.productName}" required /></td>
        </tr>
        <tr>
            <th>상품가격</th>
            <td><input type="number" name="productPrice" value="${product.productPrice}" required /></td>
        </tr>
        <tr>
            <th>재고수량</th>
            <td><input type="number" name="productStock" value="${product.productStock}" required /></td>
        </tr>
        <tr>
            <th>상품이미지</th>
            <td><input type="file" name="productImg" /></td>
        </tr>
        <tr>
            <th>카테고리번호</th>
            <td><input type="number" name="categoryId" value="${product.categoryId}" required /></td>
        </tr>
        </tbody>
    </table>
    <p>
        <button type="submit">
            <c:choose>
                <c:when test="${empty product}">
                    등록
                </c:when>
                <c:otherwise>
                    수정
                </c:otherwise>
            </c:choose>
        </button>
    </p>
</form>
</body>
</html>
