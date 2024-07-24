<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>카테고리 관리</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container mt-4">
    <h4>카테고리 관리</h4>
    <button type="button" class="btn btn-primary btn-sm" data-toggle="modal" data-target="#categoryModal">
        카테고리 추가
    </button>
    <div id="categories">
        <ul class="list-group">
            <c:forEach items="${categories}" var="category">
                <li class="list-group-item">
                    <a href="/admin/subCategoryPage.do?categoryId=${category.categoryId}">${category.categoryName}</a>
                    <button class="btn btn-danger btn-sm float-right" onclick="deleteCategory(${category.categoryId})">삭제</button>
                </li>
            </c:forEach>
            <%-- 하위 카테고리 목록 표시 --%>
            <c:if test="${not empty subCategories}">
                <h5>하위 카테고리</h5>
                <ul class="list-group">
                    <c:forEach items="${subCategories}" var="subCategory">
                        <li class="list-group-item">${subCategory.categoryName}
                            <button class="btn btn-danger btn-sm float-right" onclick="deleteCategory(${subCategory.categoryId})">삭제</button>
                        </li>
                    </c:forEach>
                </ul>
            </c:if>
        </ul>
    </div>
</div>

<div class="modal fade" id="categoryModal" tabindex="-1" role="dialog" aria-labelledby="categoryModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="categoryModalLabel">카테고리 추가</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <form method="post" action="/admin/category/add.do">
                <div class="modal-body">
                    <div class="form-group">
                        <label for="categoryName">카테고리 이름</label>
                        <input type="text" class="form-control" id="categoryName" name="categoryName" required>
                    </div>
                    <div class="form-group">
                        <label for="parentCategoryName">상위 카테고리 이름</label>
                        <input type="text" class="form-control" id="parentCategoryName" name="parentCategoryName" required>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">닫기</button>
                    <button type="submit" class="btn btn-primary">저장</button>
                </div>
            </form>
        </div>
    </div>
</div>

<script>
    function deleteCategory(categoryId) {
        if(confirm('정말 삭제하시겠습니까?')) {
            document.location.href = '/admin/category/delete.do?categoryId=' + categoryId;
        }
    }
</script>
</body>
</html>
