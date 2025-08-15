<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>

<div class="container mt-5">
  <div class="row justify-content-center">
    <div class="col-md-5">
      <div class="card shadow">
        <div class="card-body p-4">
          <h2 class="text-center mb-4">Đăng Ký</h2>
          <c:if test="${not empty error}">
            <div class="alert alert-danger">${error}</div>
          </c:if>
          <c:if test="${not empty success}">
            <div class="alert alert-success">${success}</div>
          </c:if>
          <form action="${pageContext.request.contextPath}/register" method="post">
            <div class="mb-3">
              <label for="username" class="form-label">Tên đăng nhập</label>
              <input type="text" class="form-control" id="username" name="username" required>
            </div>
            <div class="mb-3">
              <label for="password" class="form-label">Mật khẩu</label>
              <input type="password" class="form-control" id="password" name="password" required>
            </div>
            <div class="mb-3">
              <label for="confirmPassword" class="form-label">Nhập lại mật khẩu</label>
              <input type="password" class="form-control" id="confirmPassword" name="confirmPassword" required>
            </div>
            <select name="role">
              <option value="">Chọn vai trò</option>
              <option value="ADMIN">ADMIN</option>
              <option value="USER">USER</option>
            </select>
            <button type="submit" class="btn btn-success w-100">Đăng ký</button>
          </form>
          <hr>
          <div class="text-center">
            <span>Đã có tài khoản?</span>
            <a href="${pageContext.request.contextPath}/login" class="btn btn-outline-primary w-100 mt-2">Đăng nhập</a>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>

<jsp:include page="/WEB-INF/views/common/footer.jsp"/>