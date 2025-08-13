<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>

<div class="container mt-4">
  <div class="card">
    <div class="card-header">
      <h3>Chi Tiết Đơn Hàng #${order.id}</h3>
    </div>
    <div class="card-body">
      <div class="row mb-4">
        <div class="col-md-6">
          <strong>Khách hàng:</strong> <c:out value="${order.customerUsername}"/><br>
          <strong>Ngày đặt:</strong> ${order.orderDate}
        </div>
        <div class="col-md-6 text-end">
          <h4>Tổng cộng: <span class="text-danger">${String.format("%,.0f", order.total)} VNĐ</span></h4>
        </div>
      </div>

      <h5>Các sản phẩm trong đơn hàng:</h5>
      <table class="table table-bordered">
        <thead class="table-light">
        <tr>
          <th>Tên Sản Phẩm</th>
          <th>Giá</th>
          <th>Số Lượng</th>
          <th>Thành Tiền</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="item" items="${order.items}">
          <tr>
            <td><c:out value="${item.productName}"/></td>
            <td>${String.format("%,.0f", item.price)} VNĐ</td>
            <td>${item.quantity}</td>
            <td>${String.format("%,.0f", item.price * item.quantity)} VNĐ</td>
          </tr>
        </c:forEach>
        </tbody>
      </table>
      <a href="${pageContext.request.contextPath}/admin/orders" class="btn btn-secondary mt-3">Quay Lại Danh Sách</a>
    </div>
  </div>
</div>

<jsp:include page="/WEB-INF/views/common/footer.jsp"/>