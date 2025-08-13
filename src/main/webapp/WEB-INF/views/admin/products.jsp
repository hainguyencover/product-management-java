<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>

<div class="container mt-4">
    <div class="d-flex justify-content-between align-items-center mb-3">
        <h2>Quản lý sản phẩm</h2>
        <div>
            <a href="${pageContext.request.contextPath}/admin/categories" class="btn btn-info">Quản Lý Danh Mục</a>
            <%-- SỬA LẠI ĐƯỜNG DẪN: trỏ đến URL "create" --%>
            <a href="${pageContext.request.contextPath}/admin/products/create" class="btn btn-success">Thêm sản phẩm mới</a>
        </div>
    </div>

    <table class="table table-striped table-bordered">
        <thead class="table-dark">
        <tr>
            <th>ID</th><th>Tên</th><th>Giá</th><th>Số lượng</th><th>Danh mục</th><th>Hành động</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="product" items="${products}">
            <tr>
                <td>${product.id}</td>
                <td><c:out value="${product.name}"/></td>
                <td>${String.format("%,.0f", product.price)} VNĐ</td>
                <td>${product.quantity}</td>
                <td><c:out value="${product.category.name}"/></td>
                <td>
                        <%-- SỬA LẠI CÁC ĐƯỜNG DẪN: trỏ đến URL "edit" và "delete" --%>
                    <a href="${pageContext.request.contextPath}/admin/products/edit?id=${product.id}" class="btn btn-sm btn-warning">Sửa</a>
                    <a href="${pageContext.request.contextPath}/admin/products/delete?id=${product.id}" class="btn btn-sm btn-danger" onclick="return confirm('Bạn chắc chắn muốn xóa?')">Xóa</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<jsp:include page="/WEB-INF/views/common/footer.jsp"/>