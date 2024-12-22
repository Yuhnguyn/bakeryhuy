
<%@page import="com.entity.Orders"%>
<%@page import="com.DAO.OrdersDAOImpl"%>
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
		$('#orders').DataTable();
	});
</script>

<meta charset="UTF-8">
<title>Danh Sách Đơn Hàng</title>
</head>



<style>
h2 {
	margin-top: 100px;
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

.user-id-column {
	width: 50px; /* Adjust the width as needed */
	text-align: center; /* Center-align the content if desired */
}

/* Ensure other table columns are responsive */
#orders th, #orders td {
	text-align: left;
	padding: 10px;
}

/*button  */
.button {
	font-size: 12px;
	font-weight: bold;
	padding: 4px 8px;
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
</style>

<body>
	<%@include file="header.jsp"%>

	<h2>Danh sách đơn hàng</h2>
	<!-- Hiển thị thông báo -->
	<c:if test="${not empty succMsg}">
		<div class="message success">${succMsg}</div>
		<c:remove var="succMsg" scope="session" />
	</c:if>
	<c:if test="${not empty failMsg}">
		<div class="message error">${failMsg}</div>
		<c:remove var="failMsg" scope="session" />
	</c:if>


	<table id="orders" class="display">
		<thead>
			<tr>


				<th>Mã Đơn</th>
				<th class="user-id-column">UserID</th>
				<th>Họ Tên</th>
				<th>Số điện thoại</th>
				<th>Địa chỉ</th>
				<th>Ngày đặt</th>
				<th>Tổng tiền</th>
				<th>Thanh toán</th>
				<th>Trạng thái</th>
				<th>Ngày giao</th>
				<th>Thao tác</th>


			</tr>
		</thead>
		<tbody>

			<%
			OrdersDAOImpl dao = new OrdersDAOImpl(DBConnect.getConn());
			List<Orders> list = dao.getAllOrders();
			for (Orders order : list) {
			%>
			<tr>
				<td><%=order.getId()%></td>
				<td class="user-id-column"><%=order.getUserId()%></td>
				<td><%=order.getName()%></td>
				<td><%=order.getPhone()%></td>
				<td><%=order.getAddress()%></td>
				<td><%=order.getCreatedAt()%></td>
				<td><%=order.getTotalMoney()%></td>
				<td><%=order.getPaymentMethod()%></td>
				<td><%=order.getStatus()%></td>
				<td><%=order.getApprovedAt()%></td> 
				<td><a href="../UpdateOrderStatus?id=<%=order.getId()%>""
					class="button btn-approve"> <i class="fas fa-check-circle"></i> </a> 
					<a class="button btn-reject" href="order_detail.jsp?id=<%=order.getId()%>"><i class="fas fa-info-circle"></i></a></td>


			</tr>
			<%
			}
			%>

		</tbody>
	</table>
	</div>







</body>
</html>