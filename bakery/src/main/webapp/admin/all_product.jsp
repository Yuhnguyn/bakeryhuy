
<%@page import="com.entity.Product"%>
<%@page import="com.DAO.ProductDAOImpl"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="com.DB.DBConnect"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet"
	href="https://cdn.datatables.net/2.1.8/css/dataTables.dataTables.css" />
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<!-- Đảm bảo rằng jQuery đã được tải -->
<script src="https://cdn.datatables.net/2.1.8/js/dataTables.min.js"></script>
<!-- Tải DataTables JS -->
<script>
	$(document).ready(function() {
		$('#product').DataTable();
	});
</script>

<meta charset="UTF-8">
<title>Danh Sách Sản Phẩm</title>
</head>



<style>
h2 {
	margin-top:100px;
	padding: 20px;
	font-size: 20px;
}

.history-table {
	margin-top: 100px;
	margin-left: 50px;
}

.history-table table {
	width: 70%;
	border-collapse: collapse;
}

.history-table table, .history-table th, .history-table td {
	border: 1px solid #ddd;
}

.history-table th, .history-table td {
	padding: 10px;
	text-align: left;
	font-size: 16px;
}

.history-table th {
	background-color: #f2f2f2;
}

.history-table {
	white-space: nowrap; /* Không ngắt dòng */
	width: 200px; /* Điều chỉnh chiều rộng theo ý muốn */
}

/*button  */
.button {
	font-size: 14px;
	font-weight: bold;
	padding: 8px 16px;
	border: none;
	border-radius: 5px;
	cursor: pointer;
	transition: background-color 0.3s ease;
}

.btn-approve {
	background-color: #4CAF50;
	color: white;
}

.btn-approve:hover {
	background-color: #45a049;
}

.btn-reject {
	background-color: #f44336;
	color: white;
}

.btn-reject:hover {
	background-color: #d32f2f;
}

.message {
	text-align: center;
	margin: 20px 0;
	font-size: 16px;
	font-weight: bold;
}

.message.success {
	color: green;
}

.message.error {
	color: red;
}
/* Căn giữa và thêm khoảng cách cho nút "Thêm sản phẩm" */
tfoot td {
	text-align: center;
	padding: 20px 0; /* Thêm khoảng đệm trên và dưới */
}

tfoot .button {
	display: inline-block; /* Hiển thị dạng nút */
	margin-top: 10px; /* Khoảng cách giữa nút và nội dung khác */
	padding: 12px 24px; /* Tăng kích thước nút */
}
</style>

<body>
	<%@include file="header.jsp"%>

		<h2>Danh sách sản phẩm</h2>
 <!-- Hiển thị thông báo -->
    <c:if test="${not empty succMsg}">
        <div class="message success">${succMsg}</div>
        <c:remove var="succMsg" scope="session"/>
    </c:if>
    <c:if test="${not empty failMsg}">
        <div class="message error">${failMsg}</div>
        <c:remove var="failMsg" scope="session"/>
    </c:if>
		<!-- <!-- Form tìm kiếm 
		<form action="ProductSearchServlet" method="GET"
			style="margin-bottom: 20px;">
			<label for="searchKey">Tìm kiếm:</label> <input type="text"
				id="searchKey" name="searchKey"
				placeholder="Nhập mã sản phẩm hoặc tên sản phẩm..."
				style="padding: 8px; margin-right: 10px;"> <label
				for="filter">Sắp xếp theo:</label> <select id="filter" name="filter"
				style="padding: 8px; margin-right: 10px;">
				<option value="all">Tất cả</option>
				<option value="id">Danh mục</option>
				<option value="name">Mã sản phẩm</option>
				<option value="price">Giá tăng dần</option>
				<option value="price">Giá giảm dần</option>
				<option value="latest">Mới nhất</option>
			</select>

			<button type="submit"
				style="padding: 8px 16px; background-color: #4CAF50; color: white; border: none; border-radius: 5px;">Tìm
				kiếm</button>
		</form> -->


		<table id="product" class="display">
			<thead>
				<tr>


					<th>Mã DM</th>
					<th>Tên DM</th>
					<th>Mã SP</th>
					<th>Tên SP</th>
					<th>Giá</th>
					<th>Giảm giá</th>
					<th>Mô tả</th>
					<th>Thời gian tạo</th>
					<th>Thời gian cập nhật</th>
					<th>Hình ảnh</th>
					<th>Thao tác</th>

				</tr>
			</thead>
			<tbody>

				<%
				ProductDAOImpl dao = new ProductDAOImpl(DBConnect.getConn());
				List<Product> list = dao.getAllProducts();
				for (Product product : list) {
				%>
				<tr>

					<td><%=product.getCategoryId()%></td>
					<td><%=product.getCategory()%></td>
					<td><%=product.getId()%></td>
					<td><%=product.getName()%></td>
					<td><%=product.getFormattedBalance()%> </td>
					<td><%=product.getDiscount()%></td>
					<td><%=product.getDescription()%></td>
					<td><%=product.getCreatedAt()%></td>
					<td><%=product.getUpdatedAt()%></td>

					<td><img src="../product/<%=product.getThumbnail() %>" alt="<%=product.getThumbnail() %>" style="width: 50px; height: 50px;"></td>

					<td><a href="update_product.jsp?id=<%=product.getId()%>"
						class="button btn-approve">Chỉnh sửa</a> <a
						href="javascript:void(0);" class="button btn-reject"
						onclick="confirmDelete('../deleteProduct?id=<%=product.getId()%>')">Xóa</a>
					</td>


				</tr>
				<%
				}
				%>

			</tbody>
			<tfoot>
				<tr>
					<td colspan="11" style="text-align: center;"><a
						href="add_product.jsp" class="button btn-approve"
						style="font-size: 16px; padding: 10px 20px;">Thêm sản phẩm</a></td>
				</tr>
			</tfoot>
		</table>
	</div>

	<!-- Hộp thoại xác nhận -->
	<div id="confirm-dialog" style="display: none;">
		<div
			style="position: fixed; top: 0; left: 0; width: 100%; height: 100%; background: rgba(0, 0, 0, 0.5);">
			<div
				style="background: white; padding: 20px; border-radius: 5px; width: 300px; margin: 15% auto; text-align: center;">
				<p>Bạn có chắc chắn muốn xóa sản phẩm này?</p>
				<div>
					<button id="confirm-btn"
						style="margin-right: 10px; background-color: #4CAF50; color: white; padding: 10px 20px; border: none; border-radius: 5px; cursor: pointer;">Xác
						nhận</button>
					<button onclick="closeDialog()"
						style="background-color: #f44336; color: white; padding: 10px 20px; border: none; border-radius: 5px; cursor: pointer;">Hủy</button>
				</div>
			</div>
		</div>
	</div>


	<script>
		let deleteUrl = "";

		function confirmDelete(url) {
			deleteUrl = url; // Lưu URL xóa vào biến
			document.getElementById("confirm-dialog").style.display = "block";
		}

		function closeDialog() {
			document.getElementById("confirm-dialog").style.display = "none";
		}

		document.getElementById("confirm-btn").onclick = function() {
			// Điều hướng đến URL xóa
			window.location.href = deleteUrl;
		};
	</script>


</body>
</html>