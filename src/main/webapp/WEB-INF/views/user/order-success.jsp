<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>

<div class="container text-center mt-5">
  <div class="py-5">
    <h1 class="display-4 text-success">🎉 Đặt hàng thành công!</h1>
    <p class="lead">Cảm ơn bạn đã mua sắm tại cửa hàng của chúng tôi. Chúng tôi sẽ xử lý đơn hàng của bạn trong thời gian sớm nhất.</p>
    <hr>
    <p>
      Bạn muốn tiếp tục mua sắm?
    </p>
    <a class="btn btn-primary btn-lg" href="${pageContext.request.contextPath}/home" role="button">Về Trang Chủ</a>
  </div>
</div>

<jsp:include page="/WEB-INF/views/common/footer.jsp"/>