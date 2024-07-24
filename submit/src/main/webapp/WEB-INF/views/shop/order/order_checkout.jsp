<%--
  Created by IntelliJ IDEA.
  User: parkheejun
  Date: 5/8/24
  Time: 8:43 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>주문완료</title>
    <link rel="stylesheet" href="/style.css"/>
</head>
<body>
<c:forEach items="${productList}" var="entry">
    <div class="list-group-item">
        <div class="row align-items-center">
            <div class="col-md-1">
                <img src="${not empty entry.key.productImg ? entry.key.productImg : '/resources/no-image.png'}"
                     alt="${entry.key.productName}"
                     style="width: 100%; max-width: 70px; height: auto;">
            </div>
            <div class="col-md-3">
                    ${entry.key.productName}
            </div>
            <div class="col-md-2">
                수량 : ${entry.value}
            </div>
            <div class="col-md-3">
                가격 : $${entry.key.productPrice * entry.value}
            </div>
        </div>
    </div>
</c:forEach>
<table>
    <tbody>
    <tr>
        <th>구매한 물품 총 금액</th>
        <td>${payment.paymentTotal}</td>
    </tr>
    <tr>
        <th>할인된 총 금액</th>
        <td>${payment.paymentDiscount}</td>
    </tr>
    <tr>
        <th>배달비</th>
        <td>${payment.paymentDelivery}</td>
    </tr>
    <tr>
        <th>최종 결제 금액</th>
        <td>${payment.paymentFinal}</td>
    </tr>
    <tr>
        <th>적립된 포인트</th>
        <td>${savedPoint.savedPointAmount}</td>
    </tr>
    </tbody>
</table>
</body>
</html>
