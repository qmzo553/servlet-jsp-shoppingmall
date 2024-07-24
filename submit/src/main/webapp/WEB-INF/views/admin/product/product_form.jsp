<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <style>
        .product-image {
            width: 150px; /* 고정 너비 */
            height: 150px; /* 고정 높이 */
            overflow: hidden; /* 컨테이너를 벗어나는 이미지 숨김 */
        }
        .product-image img {
            width: 100%; /* 컨테이너 너비에 맞춤 */
            height: 100%; /* 컨테이너 높이에 맞춤 */
            object-fit: cover; /* 비율을 유지하면서 요소를 채움 */
        }
    </style>

    <title>상품 관리</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container mt-4">
    <h4>상품 관리</h4>
    <form method="get" action="/admin/product/register.do">
        <button class="btn btn-success">상품 추가</button>
    </form>
    <div class="mt-3">
        <c:forEach items="${productPage.content}" var="product">
            <div class="card mb-3">
                <div class="row">
                    <div class="col-md-4">
                        <div class="product-image">
                            <img src="${not empty product.productImg ? product.productImg : '/resources/no-image.png'}"
                                 alt="${product.productName}">
                        </div>
                    </div>
                    <div class="col-md-8">
                        <h5 class="card-title">${product.productName}</h5>
                        <p class="card-text">가격: ${product.productPrice} | 재고: ${product.productStock}</p>
                        <div class="btn-group">
                            <form method="get" action="/admin/product/update.do">
                                <input type="hidden" name="productId" value="${product.productId}"/>
                                <button type="submit" class="btn btn-primary btn-sm" type="submit">수정</button>
                            </form>
                            <form method="post" action="/admin/product/delete.do">
                                <input type="hidden" name="productId" value="${product.productId}"/>
                                <button type="submit" class="btn btn-danger btn-sm">삭제</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
    <ul class="pagination justify-content-center">
        <c:if test="${currentPage > 1}">
            <li class="page-item"><a class="page-link" href="/admin/productPage.do?page=${currentPage - 1}">이전</a></li>
        </c:if>
        <c:forEach begin="${startPage}" end="${endPage}" var="i">
            <li class="page-item ${i == currentPage ? 'active' : ''}">
                <a class="page-link" href="/admin/productPage.do?page=${i}">${i}</a>
            </li>
        </c:forEach>
        <c:if test="${currentPage < pageCount}">
            <li class="page-item"><a class="page-link" href="/admin/productPage.do?page=${currentPage + 1}">다음</a></li>
        </c:if>
    </ul>
</div>

<script>
    function deleteProduct(productId) {
        if(confirm('정말 삭제하시겠습니까?')) {
            $.ajax({
                url: '/deleteProduct', // API endpoint to handle delete
                type: 'POST',
                data: { productId: productId },
                success: function() {
                    alert('상품이 성공적으로 삭제되었습니다.');
                    location.reload(); // Reload the page to update the list
                },
                error: function() {
                    alert('상품 삭제에 실패했습니다.');
                }
            });
        }
    }
</script>
</body>
</html>
