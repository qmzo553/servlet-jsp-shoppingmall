<%--
  Created by IntelliJ IDEA.
  User: parkheejun
  Date: 5/5/24
  Time: 12:14 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>상품-자세히보기</title>
    <link rel="stylesheet" href="/style.css"/>
</head>
<body>
<table>
    <tbody>
    <tr>
        <th>상품이름</th>
        <td>${product.productName}</td>
    </tr>
    <tr>
        <th>상품가격</th>
        <td>${product.productPrice}</td>
    </tr>
    <tr>
        <th>재고수량</th>
        <td>${product.productStock}</td>
    </tr>
    <tr>
        <th>상품사진</th>
        <td>${product.productImg}</td>
    </tr>
    <tr>
        <th>카테고리</th>
        <td>${product.categoryId}</td>
    </tr>
    </tbody>
</table>
<ul>
    <%
        HttpSession session = request.getSession(false);
        String userId = null;

        if (session != null) {
            userId = (String) session.getAttribute("userId");
        }

        if (userId != null) {
    %>
        <form method="post" action="/cart/add.do">
            <input type="hidden" name="productId" value="${product.productId}"/>
            <button type="submit" class="btn btn-sm btn-outline-secondary" type="submit">장바구니에 추가</button>
        </form>
    <%
        }
    %>
</ul>
</body>
</html>
