<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>

<div class="container mt-4">
    <h2>Giỏ Hàng Của Bạn</h2>
    <c:set var="cart" value="${sessionScope.cart}"/>

    <c:choose>
        <c:when test="${empty cart or cart.size() == 0}">
            <div class="alert alert-info">Giỏ hàng của bạn đang trống. <a href="${pageContext.request.contextPath}/home" class="alert-link">Tiếp tục mua sắm</a>.</div>
        </c:when>
        <c:otherwise>
            <table class="table table-hover align-middle">
                <thead>
                <tr>
                    <th>Sản phẩm</th>
                    <th>Giá</th>
                    <th style="width: 10%;">Số lượng</th>
                    <th>Thành tiền</th>
                    <th style="width: 5%;"></th>
                </tr>
                </thead>
                <tbody>
                <c:set var="total" value="0"/>
                <c:forEach var="entry" items="${cart}">
                    <c:set var="item" value="${entry.value}"/>
                    <c:set var="subtotal" value="${item.price * item.quantity}"/>
                    <c:set var="total" value="${total + subtotal}"/>
                    <tr>
                        <td><c:out value="${item.productName}"/></td>
                        <td>${String.format("%,.0f", item.price)} VNĐ</td>
                        <td>
                            <input type="number" class="form-control text-center" value="${item.quantity}" min="1" readonly>
                        </td>
                        <td>${String.format("%,.0f", subtotal)} VNĐ</td>
                        <td>
                                <%-- SỬA LẠI ĐƯỜNG DẪN XÓA --%>
                            <a href="${pageContext.request.contextPath}/cart/delete?id=${item.productId}" class="btn btn-danger btn-sm" title="Xóa">
                                <i class="fas fa-trash"></i>
                            </a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <div class="d-flex justify-content-end align-items-center">
                <h3 class="me-4">Tổng cộng: <span class="text-danger">${String.format("%,.0f", total)} VNĐ</span></h3>
                <a href="${pageContext.request.contextPath}/checkout" class="btn btn-lg btn-success">Tiến hành thanh toán</a>
            </div>
        </c:otherwise>
    </c:choose>
</div>

<jsp:include page="/WEB-INF/views/common/footer.jsp"/>