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
    <title>등록</title>
    <link rel="stylesheet" href="/style.css" />
    <meta charset="UTF-8" />
</head>

<body>
<c:choose>
    <c:when test="${empty user}">
        <c:set var="action" value="/user/register.do" />
    </c:when>
    <c:otherwise>
        <c:set var="action" value="/user/update.do" />
    </c:otherwise>
</c:choose>

<form method="post" action="${action}">
    <table>
        <tbody>
        <tr>
            <th>ID</th>
            <td><input type="text" name="userId" value="${user.userId}" required /></td>
        </tr>
        <tr>
            <th>이름</th>
            <td><input type="text" name="userName" value="${user.userName}" required /></td>
        </tr>
        <tr>
            <th>비밀번호</th>
            <td><input type="text" name="userPassword" value="${user.userPassword}" required /></td>
        </tr>
        <tr>
            <th>생일</th>
            <td><input type="text" name="userBirth" value="${user.userBirth}" required /></td>
        </tr>
        </tbody>
    </table>
    <p>
        <button type="submit">
            <c:choose>
                <c:when test="${empty user}">
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
