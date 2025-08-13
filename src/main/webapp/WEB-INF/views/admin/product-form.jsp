<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>

<div class="container mt-4">
    <div class="row justify-content-center">
        <div class="col-md-8">
            <div class="card">
                <div class="card-header">
                    <h3>
                        <c:if test="${not empty product}">Chỉnh Sửa Sản Phẩm</c:if>
                        <c:if test="${empty product}">Thêm Sản Phẩm Mới</c:if>
                    </h3>
                </div>
                <div class="card-body">
                    <%-- SỬA LẠI ACTION CỦA FORM --%>
                    <form action="${pageContext.request.contextPath}/admin/products/${not empty product ? 'edit' : 'create'}" method="post">

                        <%-- Nếu là edit, cần gửi cả id --%>
                        <c:if test="${not empty product}">
                            <input type="hidden" name="id" value="${product.id}">
                        </c:if>

                        <div class="mb-3">
                            <label for="name" class="form-label">Tên Sản Phẩm</label>
                            <input type="text" class="form-control" id="name" name="name" value="<c:out value='${product.name}'/>" required>
                        </div>

                        <div class="row">
                            <div class="col-md-6 mb-3">
                                <label for="price" class="form-label">Giá</label>
                                <input type="number" step="1000" class="form-control" id="price" name="price" value="${product.price}" required>
                            </div>
                            <div class="col-md-6 mb-3">
                                <label for="quantity" class="form-label">Số Lượng</label>
                                <input type="number" class="form-control" id="quantity" name="quantity" value="${product.quantity}" required>
                            </div>
                        </div>

                        <div class="mb-3">
                            <label for="categoryId" class="form-label">Danh Mục</label>
                            <select class="form-select" id="categoryId" name="categoryId" required>
                                <option value="">-- Chọn Danh Mục --</option>
                                <c:forEach var="category" items="${categories}">
                                    <option value="${category.id}" ${category.id == product.category.id ? 'selected' : ''}>
                                        <c:out value="${category.name}"/>
                                    </option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="d-flex justify-content-end">
                            <a href="${pageContext.request.contextPath}/admin/products" class="btn btn-secondary me-2">Hủy</a>
                            <button type="submit" class="btn btn-primary">Lưu Sản Phẩm</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/views/common/footer.jsp"/>