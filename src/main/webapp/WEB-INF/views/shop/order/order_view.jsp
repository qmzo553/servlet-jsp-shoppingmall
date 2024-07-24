<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>주문하기</title>
    <script>
        const SHIPPING_COST = 5000; // 배송비 고정 금액
        const DISCOUNT_RATE = 0.10; // 10% 할인

        function updatePrice(productId, unitPrice) {
            var quantity = document.getElementById("quantity_" + productId).value;
            var newPrice = unitPrice * quantity;
            document.getElementById("price_" + productId).innerText = newPrice.toFixed(2);
            calculateTotal();
        }

        function calculateTotal() {
            var subtotal = 0;
            var checkboxes = document.querySelectorAll('input[type="checkbox"]:checked');
            checkboxes.forEach(function(checkbox) {
                var productId = checkbox.value;
                var price = parseFloat(document.getElementById("price_" + productId).innerText);
                subtotal += price;
            });
            var discount = subtotal * DISCOUNT_RATE;
            var total = subtotal - discount + SHIPPING_COST;
            document.getElementById("subtotalPrice").innerText = subtotal.toFixed(2);
            document.getElementById("discountPrice").innerText = discount.toFixed(2);
            document.getElementById("shippingPrice").innerText = SHIPPING_COST.toFixed(2);
            document.getElementById("totalPrice").innerText = total.toFixed(2);
        }

        function selectAllCheckboxes(source) {
            var checkboxes = document.querySelectorAll('input[type="checkbox"]');
            for (var i = 0; i < checkboxes.length; i++) {
                checkboxes[i].checked = source.checked;
            }
            calculateTotal();
        }
    </script>
</head>
<body>

<form method="post" action="/order/order.do">
    <div class="container mt-4">
        <h5>받는사람이름 : </h5>
        <input type="text" name="receiverName" required /><br>
        <h5>배송주소 : </h5>
        <input type="text" name="receiverAddress" required /><br>
        <h5>장바구니</h5>
        <input type="checkbox" onclick="selectAllCheckboxes(this)"> 모두 선택
        <c:choose>
            <c:when test="${empty cart}">
                <p>장바구니가 비었습니다.</p>
            </c:when>
            <c:otherwise>
                <div class="list-group">
                    <c:forEach items="${cart}" var="product">
                        <div class="list-group-item">
                            <div class="row align-items-center">
                                <div class="col-md-1">
                                    <input type="checkbox" name="productId[]" value="${product.productId}" onclick="calculateTotal()">
                                </div>
                                <div class="col-md-1">
                                    <img src="${not empty product.productImg ? product.productImg : '/resources/no-image.png'}"
                                         alt="${product.productName}"
                                         style="width: 100%; max-width: 70px; height: auto;">
                                </div>
                                <div class="col-md-3">
                                        ${product.productName}
                                </div>
                                <div class="col-md-2">
                                    <label for="price_${product.productId}">가격:</label>
                                    $<span id="price_${product.productId}">${product.productPrice}</span>
                                    <input type="hidden" name="price_${product.productId}" value="${product.productPrice}" />
                                </div>
                                <div class="col-md-2">
                                    <input type="number" name="quantity_${product.productId}" id="quantity_${product.productId}" value="1" min="1" oninput="updatePrice('${product.productId}', ${product.productPrice})">
                                    <input type="hidden" name="stock_${product.productId}" value="${product.productStock}" />
                                </div>
                                <div class="col-md-1">
                                    <!-- 자세히보기 버튼 분리 -->
                                    <button type="button" onclick="location.href='/product/view.do?productId=${product.productId}'" class="btn btn-primary btn-sm">자세히보기</button>
                                </div>
                                <div class="col-md-2">
                                    <!-- 삭제 버튼 분리 -->
                                    <button type="button" onclick="if(confirm('정말 삭제하시겠습니까?')) location.href='/order/delete.do?productId=${product.productId}'" class="btn btn-danger btn-sm">삭제</button>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
    <div class="container mt-4">
        <span>소계: $<span id="subtotalPrice">0.00</span></span><br>
        <span>할인금액: -$<span id="discountPrice">0.00</span></span><br>
        <span>배송금액: $<span id="shippingPrice">0.00</span></span><br>
        <span>총 가격: $<span id="totalPrice">0.00</span></span><br>
        <button type="submit" class="btn btn-success">주문하기</button>
    </div>
</form>
</body>
</html>
