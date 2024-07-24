<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>

<html>
<head>
    <title>Error Page</title>
    <link rel="stylesheet" href="style.css" />
</head>
<body>

<table>
    <tbody>
    <tr>
        <th>상태 코드</th>
        <td>${status_code}</td>
    </tr>
    <tr>
        <th>예외 타입</th>
        <td>${exception_type}</td>
    </tr>
    <tr>
        <th>메세지</th>
        <td>${message}</td>
    </tr>
    </tbody>

</table>
</body>
</html>