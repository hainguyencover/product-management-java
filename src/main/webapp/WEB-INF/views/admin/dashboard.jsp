<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>

<div class="container-fluid mt-4">
    <div class="d-flex align-items-center justify-content-between mb-4">
        <h1 class="h3 mb-0 text-gray-800">Dashboard</h1>

    </div>


    <div class="row">
        <div class="col-xl-3 col-md-6 mb-4">
            <div class="card border-left-primary shadow h-100 py-2">
                <div class="card-body">
                    <div class="row no-gutters align-items-center">
                        <div class="col mr-2">
                            <div class="text-xs font-weight-bold text-primary text-uppercase mb-1">Doanh Thu</div>
                            <div class="h5 mb-0 font-weight-bold text-gray-800">${String.format("%,.0f", totalRevenue)}
                                VNĐ
                            </div>
                        </div>
                        <div class="col-auto">
                            <i class="fas fa-dollar-sign fa-2x text-gray-300"></i></div>
                    </div>
                </div>
            </div>
        </div>

        <div class="col-xl-3 col-md-6 mb-4">
            <div class="card border-left-success shadow h-100 py-2">
                <div class="card-body">
                    <div class="row no-gutters align-items-center">
                        <div class="col mr-2">
                            <div class="text-xs font-weight-bold text-success text-uppercase mb-1">Đơn Hàng</div>
                            <div class="h5 mb-0 font-weight-bold text-gray-800">${totalOrders}</div>
                        </div>
                        <div class="col-auto">
                            <i class="fas fa-clipboard-list fa-2x text-gray-300"></i>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="col-xl-3 col-md-6 mb-4">
            <div class="card border-left-info shadow h-100 py-2">
                <div class="card-body">
                    <div class="row no-gutters align-items-center">
                        <div class="col mr-2">
                            <div class="text-xs font-weight-bold text-info text-uppercase mb-1">Sản Phẩm</div>
                            <div class="h5 mb-0 font-weight-bold text-gray-800">${totalProducts}</div>
                        </div>
                        <div class="col-auto">
                            <i class="fas fa-box fa-2x text-gray-300"></i>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="col-xl-3 col-md-6 mb-4">
            <div class="card border-left-warning shadow h-100 py-2">
                <div class="card-body">
                    <div class="row no-gutters align-items-center">
                        <div class="col mr-2">
                            <div class="text-xs font-weight-bold text-warning text-uppercase mb-1">Người Dùng</div>
                            <div class="h5 mb-0 font-weight-bold text-gray-800">${totalUsers}</div>
                        </div>
                        <div class="col-auto">
                            <i class="fas fa-users fa-2x text-gray-300"></i>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <hr>

    <div class="row">
        <h3 class="mb-3">Lối Tắt Quản Lý</h3>
        <div class="col-lg-4 mb-4">
            <div class="card bg-primary text-white shadow">
                <div class="card-body">
                    Quản Lý Sản Phẩm
                    <div class="text-white-50 small">Thêm, sửa, xóa sản phẩm</div>
                    <a href="${pageContext.request.contextPath}/admin/products" class="stretched-link"></a>
                </div>
            </div>
        </div>
        <div class="col-lg-4 mb-4">
            <div class="card bg-success text-white shadow">
                <div class="card-body">
                    Quản Lý Danh Mục
                    <div class="text-white-50 small">Thêm, sửa, xóa danh mục</div>
                    <a href="${pageContext.request.contextPath}/admin/categories" class="stretched-link"></a>
                </div>
            </div>
        </div>
        <div class="col-lg-4 mb-4">
            <div class="card bg-warning text-white shadow">
                <div class="card-body">
                    Quản Lý Đơn Hàng
                    <div class="text-white-50 small">Xem và xử lý đơn hàng</div>
                    <%-- SỬA LẠI ĐƯỜNG DẪN NÀY --%>
                    <a href="${pageContext.request.contextPath}/admin/orders" class="stretched-link"></a>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/views/common/footer.jsp"/>