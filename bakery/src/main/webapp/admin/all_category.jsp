<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.List"%>
<%@ page import="com.DB.DBConnect"%>
<%@ page import="com.DAO.CategoryDAOImpl"%>
<%@ page import="com.entity.Category"%>

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
    var table = $('#category').DataTable({
        pageLength: 5, // Số mục hiển thị trên mỗi trang
        lengthMenu: [5, 25, 50, 100], // Các tùy chọn số lượng mục
        autoWidth: false,
        columnDefs: [
            { width: "50px", targets: 4 }, // Định nghĩa độ rộng cột
        ],
    });

    // Áp dụng tìm kiếm cho từng cột
    $('#category thead tr:eq(1) th').each(function(i) {
        $('input', this).on('keyup change', function() {
            if (table.column(i).search() !== this.value) {
                table
                    .column(i)
                    .search(this.value)
                    .draw();
            }
        });
    });
});

</script>

<meta charset="UTF-8">
<title>Danh mục sản phẩm</title>
</head>
<style>
/* // style="text-align: center;" */
body {
	font-family: Arial, sans-serif;
	background-color: #f9f9f9;
}

h2 {
	text-align: center;
	padding: 20px;
	font-size: 24px;
	color: #333;
}

table {
	width: 90%;
	height: 60px;
	margin: 20px auto;
	border-collapse: collapse;
	background-color: #fff;
}

th, td {
	padding: 10px;
	text-align: center;
	border: 1px solid #ddd;
}

th {
	background-color: #f2f2f2;
	font-weight: bold;
}

.button {
	padding: 8px 16px;
	border: none;
	border-radius: 5px;
	cursor: pointer;
	color: white;
}

.btn-approve {
	background-color: #4CAF50;
}

.btn-reject {
	background-color: #f44336;
}

.btn-approve:hover {
	background-color: #45a049;
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
</style>
<body>
	<%@ include file="header.jsp"%>

	<h2>Danh mục sản phẩm</h2>

	<!-- Hiển thị thông báo -->
	<c:if test="${not empty succMsg}">
		<div class="message success">${succMsg}</div>
		<c:remove var="succMsg" scope="session" />
	</c:if>
	<c:if test="${not empty failMsg}">
		<div class="message error">${failMsg}</div>
		<c:remove var="failMsg" scope="session" />
	</c:if>



	<!-- Hiển thị bảng danh mục -->
	<table id="category" class="display">
		<thead>
    <tr>
        <th>Mã danh mục</th>
        <th>Tên danh mục</th>
        <th>Số lượng sản phẩm</th>
        <th>Hình ảnh</th>
        <th>Mô tả</th>
        <th>Thời gian tạo</th>
        <th>Thời gian cập nhật</th>
        <th>Thao tác</th>
    </tr>
    <tr>
        <th><input type="text" placeholder="Tìm mã danh mục" class="column-search"></th>
        <th><input type="text" placeholder="Tìm tên danh mục" class="column-search"></th>
        <th><input type="text" placeholder="Tìm số lượng sản phẩm" class="column-search"></th>
        <th></th>
        <th><input type="text" placeholder="Tìm mô tả" class="column-search"></th>
        <th><input type="text" placeholder="Tìm thời gian tạo" class="column-search"></th>
        <th><input type="text" placeholder="Tìm thời gian cập nhật" class="column-search"></th>
        <th></th>
    </tr>
</thead>


		
		<tbody>
		<%
		CategoryDAOImpl dao = new CategoryDAOImpl(DBConnect.getConn());
		List<Category> list = dao.getAllCategories();
		for (Category category : list) {
		%>
			<tr>
				<td><%=category.getId()%></td>
				<td><%=category.getName()%></td>
				<td><%=category.getProductCount()%></td>
				<td><img
					src="${pageContext.request.contextPath}/category/<%=category.getThumbnail() %>"
					alt="<%=category.getName() %>" style="width: 50px; height: 50px;" />
				</td>
				<td><%=category.getDescription()%></td>
				<td><%=category.getCreatedAt()%></td>
				<td><%=category.getUpdatedAt()%></td>
				<td><a href="update_category.jsp?id=<%=category.getId()%>"
					class="button btn-approve">Sửa</a> <a
					href="../delete?id=<%=category.getId()%>"
					class="button btn-reject">Xóa</a></td>
			</tr>
			<%
			}
			%>
		</tbody>
	</table>

	<!-- Nút thêm danh mục -->
	<div style="text-align: center; margin-top: 20px;">
		<a href="add_category.jsp" class="button btn-approve"
			style="font-size: 16px; padding: 10px 20px;">Thêm danh mục</a>
	</div>

</body>
</html>
