<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>

<div class="container mt-4">
    <h2 class="mb-3">Quản Lý Đơn Hàng</h2>
    <table class="table table-bordered table-striped">
        <thead class="table-dark">
        <tr>
            <th>Mã ĐH</th>
            <th>Khách Hàng</th>
            <th>Ngày Đặt</th>
            <th>Tổng Tiền</th>
            <th>Hành Động</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="order" items="${orders}">
            <tr>
                <td>#${order.id}</td>
                <td><c:out value="${order.customerUsername}"/></td>
                <td>${order.orderDate}</td>
                <td><strong class="text-danger">${String.format("%,.0f", order.total)} VNĐ</strong></td>
                <td>
                    <a href="${pageContext.request.contextPath}/admin/orders/view?id=${order.id}" class="btn btn-info btn-sm">Xem Chi Tiết</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <a href="${pageContext.request.contextPath}/admin/dashboard" class="btn btn-secondary mt-3">Quay về Dashboard</a>
</div>

<jsp:include page="/WEB-INF/views/common/footer.jsp"/>