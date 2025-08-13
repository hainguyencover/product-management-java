<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>

<div class="container mt-4">
    <div class="d-flex justify-content-between align-items-center mb-3">
        <h2>Quản Lý Danh Mục</h2>
        <%-- SỬA LẠI ĐƯỜNG DẪN NÀY --%>
        <a href="${pageContext.request.contextPath}/admin/categories/create" class="btn btn-success">Thêm Danh Mục Mới</a>
    </div>

    <table class="table table-bordered table-striped">
        <thead class="table-dark">
        <tr>
            <th style="width: 10%;">ID</th>
            <th>Tên Danh Mục</th>
            <th style="width: 15%;">Hành Động</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="category" items="${categories}">
            <tr>
                <td>${category.id}</td>
                <td><c:out value="${category.name}"/></td>
                <td>
                        <%-- SỬA LẠI CÁC ĐƯỜNG DẪN NÀY --%>
                    <a href="${pageContext.request.contextPath}/admin/categories/edit?id=${category.id}" class="btn btn-warning btn-sm">Sửa</a>
                    <a href="${pageContext.request.contextPath}/admin/categories/delete?id=${category.id}" class="btn btn-danger btn-sm" onclick="return confirm('Bạn có chắc chắn muốn xóa danh mục này?')">Xóa</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <a href="${pageContext.request.contextPath}/admin/dashboard" class="btn btn-secondary mt-3">Quay về Dashboard</a>
</div>

<jsp:include page="/WEB-INF/views/common/footer.jsp"/>