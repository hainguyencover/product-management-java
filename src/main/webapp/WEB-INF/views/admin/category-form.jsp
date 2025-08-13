<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>

<div class="container mt-4">
    <div class="row justify-content-center">
        <div class="col-md-6">
            <div class="card">
                <div class="card-header">
                    <h3>
                        <c:if test="${not empty category}">Chỉnh Sửa Danh Mục</c:if>
                        <c:if test="${empty category}">Thêm Danh Mục Mới</c:if>
                    </h3>
                </div>
                <div class="card-body">
                    <%-- SỬA LẠI ACTION CỦA FORM --%>
                    <form action="${pageContext.request.contextPath}/admin/categories/${not empty category ? 'edit' : 'create'}" method="post">

                        <%-- Nếu là edit, gửi kèm ID --%>
                        <c:if test="${not empty category}">
                            <input type="hidden" name="id" value="${category.id}">
                        </c:if>

                        <div class="mb-3">
                            <label for="name" class="form-label">Tên Danh Mục</label>
                            <input type="text" class="form-control" id="name" name="name" value="<c:out value='${category.name}'/>" required autofocus>
                        </div>

                        <div class="d-flex justify-content-end">
                            <a href="${pageContext.request.contextPath}/admin/categories" class="btn btn-secondary me-2">Hủy</a>
                            <button type="submit" class="btn btn-primary">Lưu</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/views/common/footer.jsp"/>