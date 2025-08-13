<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="container mt-4">
  <div class="p-5 mb-4 bg-light rounded-3">
    <div class="container-fluid py-5">
      <h1 class="display-5 fw-bold">Chào mừng tới Shop!</h1>
      <p class="col-md-8 fs-4">Nơi bạn tìm thấy những sản phẩm công nghệ đỉnh cao.</p>
    </div>
  </div>

  <div class="row">
    <c:forEach var="product" items="${productList}">
      <div class="col-md-4 mb-4">
        <div class="card h-100">
          <img src="https://via.placeholder.com/150" class="card-img-top" alt="<c:out value="${product.name}"/>">
          <div class="card-body d-flex flex-column">
            <h5 class="card-title"><c:out value="${product.name}"/></h5>
            <p class="card-text">Số lượng: ${product.quantity}</p>
            <h6 class="card-subtitle mb-2 text-danger">${String.format("%,.0f", product.price)} VNĐ</h6>

            <div class="mt-auto">
                <%-- Đảm bảo đường dẫn này đúng --%>
              <a href="${pageContext.request.contextPath}/cart/add?id=${product.id}" class="btn btn-primary">Thêm vào giỏ</a>
            </div>
          </div>
        </div>
      </div>
    </c:forEach>
  </div>
</div>

<jsp:include page="/WEB-INF/views/common/footer.jsp"/>