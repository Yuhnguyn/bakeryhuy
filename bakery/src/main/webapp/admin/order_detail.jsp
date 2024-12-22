
<%@page import="com.entity.OrderDetails"%>
<%@page import="com.DAO.OrderDetailsDAOImpl"%>
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
		$('#order_details').DataTable();
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

#order_details th, #order_details td {
    padding: 5px; /* Giảm padding để thu nhỏ khoảng cách */
    text-align: center; /* Căn giữa nội dung */
    font-size: 14px; /* Giảm kích thước chữ */
    white-space: nowrap; /* Tránh ngắt dòng */
}

.history-table th {
    background-color: #f2f2f2;
}

.history-table {
    white-space: nowrap;
    width: 200px;
}
</style>

<body>
	<%@include file="header.jsp"%>
<%
			String orderId = request.getParameter("id");
			OrderDetailsDAOImpl dao = new OrderDetailsDAOImpl(DBConnect.getConn());
			List<OrderDetails> list = dao.getOrderDetailsByOrderId(orderId);
			for (OrderDetails order : list) {
			%>
	<h2>Chi tiết đơn hàng <%=order.getOrderId() %> </h2>
	<!-- Hiển thị thông báo -->
	<c:if test="${not empty succMsg}">
		<div class="message success">${succMsg}</div>
		<c:remove var="succMsg" scope="session" />
	</c:if>
	<c:if test="${not empty failMsg}">
		<div class="message error">${failMsg}</div>
		<c:remove var="failMsg" scope="session" />
	</c:if>


	<table id="order_details" class="display">
		<thead>
			<tr>


					
				<th>Mã sản phẩm</th>
				<th>Tên sản phẩm</th>
				<th>Số lượng</th>
				<th>Đơn giá</th>
				<th>Tổng tiền</th>


			</tr>
		</thead>
		<tbody>

			
			<tr>
				<td><%=order.getProductId() %></td>
				<td><%=order.getProductName() %></td>
				<td><%=order.getNum() %></td>
				<td><%=order.getPrice() %> VND</td>
				<td><%= order.getNum() * order.getPrice() %> VND</td> <!-- Tổng tiền -->

			</tr>
			<%
			}
			%>

		</tbody>
	</table>








</body>
</html>