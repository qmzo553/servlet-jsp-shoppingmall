<%--
  Created by IntelliJ IDEA.
  User: nhn
  Date: 2023/11/08
  Time: 10:20 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link rel="stylesheet" href="/style.css" />

<div class="container mt-4">
    <h5>최근 본 상품</h5>
    <div class="row row-cols-1 row-cols-sm-2 row-cols-md-5 g-3">
        <c:forEach items="${recentProducts}" var="product">
            <div class="col">
                <div class="card shadow-sm">
                    <img class="bd-placeholder-img card-img-top"
                         src="${not empty product.productImg ? product.productImg : '/resources/no-image.png'}"
                         alt="${product.productName}"
                         width="100%"
                         height="225">
                    <div class="card-body">
                        <p class="card-text">${product.productName} - ${product.productPrice}원</p>
                        <div class="d-flex justify-content-between align-items-center">
                            <form method="get" action="/product/view.do">
                                <input type="hidden" name="productId" value="${product.productId}" />
                                <button type="submit" class="btn btn-sm btn-outline-secondary">자세히보기</button>
                            </form>
                            <small class="text-muted">남은 수량: ${product.productStock} 개</small>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
    <hr style="border-top: 1px solid #ccc; margin-top: 20px; margin-bottom: 20px;">
    <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">
        <c:forEach items="${productPage.content}" var="product">
            <div class="col">
                <div class="card shadow-sm">
                    <img class="bd-placeholder-img card-img-top"
                         src="${not empty product.productImg ? product.productImg : '/resources/no-image.png'}"
                         alt="${product.productName}"
                         width="100%"
                         height="225">
                    <div class="card-body">
                        <p class="card-text">${product.productName} - ${product.productPrice}원</p>
                        <div class="d-flex justify-content-between align-items-center">
                            <form method="get" action="/product/view.do">
                                <input type="hidden" name="productId" value="${product.productId}" />
                                <button type="submit" class="btn btn-sm btn-outline-secondary">자세히보기</button>
                            </form>
                            <small class="text-muted">남은 수량: ${product.productStock} 개</small>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
    <div class="mt-4">
        <nav aria-label="...">
            <ul class="pagination justify-content-center">
                <%
                    int startPage = Integer.parseInt(request.getAttribute("startPage").toString());
                    int endPage = Integer.parseInt(request.getAttribute("endPage").toString());
                    int pageBlock = Integer.parseInt(request.getAttribute("pageBlock").toString());
                    int pageCount = Integer.parseInt(request.getAttribute("pageCount").toString());

                    if(startPage > pageBlock) {
                %>
                    <li class="page-item"><a class="page-link" href="/index.do?page=<%= startPage - pageBlock%>">Prev</a></li>
                <%
                    }
                    for(int i=startPage; i<=endPage; i++){
                %>
                    <li class="page-item"><a class="page-link" href="/index.do?page=<%= i%>"><%= i%></a></li>
                <%
                    }
                    if (endPage < pageCount) {
                %>
                    <li class="page-item"><a class="page-link" href="/index.do?page=<%= startPage + pageBlock%>">Next</a></li>
                <%
                    }
                %>
            </ul>
        </nav>
    </div>
</div>