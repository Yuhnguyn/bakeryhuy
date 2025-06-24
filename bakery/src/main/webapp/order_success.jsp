<%@page import="com.DB.DBConnect"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.DAO.CartDAOImpl"%>
<%@ page import="com.entity.Cart"%>
<%@ page import="java.text.NumberFormat"%>
<%@ page import="java.util.List"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Đặt hàng thành công</title>
<link rel="stylesheet" href="all_component/style.css">


<style>
body {
	font-family: Arial, sans-serif;
	margin: 0;
	padding: 0;
	background-color: #f9f9f9;
	padding: 100px 0;
}

.container2 {
	max-width: 1200px;
	margin: 0 auto;
	padding: 20px;
	display: flex;
	gap: 20px;
}

.main-content {
	flex: 2;
	background-color: #fff;
	padding: 20px;
	border-radius: 8px;
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.main-content h1 {
	font-size: 1.5rem;
	margin-bottom: 10px;
}

.order-info {
	margin-top: 20px;
}

.order-info h3 {
	margin-bottom: 10px;
	font-size: 1rem;
	font-weight: 600;
}

.order-info p {
	margin: 0;
	font-size: 0.9rem;
	color: #555;
}

.button-container {
	margin-top: 20px;
}

.button-container a {
	display: inline-block;
	text-decoration: none;
	padding: 10px 20px;
	background-color: #007bff;
	color: #fff;
	border-radius: 5px;
	text-align: center;
}

.button-container a:hover {
	background-color: #0056b3;
}

.order-detail {
	max-width: 400px;
	/* Đặt kích thước tối đa cho phần thông tin đơn hàng */
	margin: 0 auto;
	background-color: #fff;
	padding: 20px;
	border-radius: 8px;
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.order-detail h3 {
	font-size: 1.2rem;
	margin-bottom: 20px;
}

.finish-product-item1 {
	display: flex; /* Sử dụng flex để căn chỉnh ngang */
	align-items: center; /* Căn giữa theo chiều dọc */
	justify-content: flex-start; /* Căn trái nội dung */
	gap: 15px; /* Khoảng cách giữa ảnh và chi tiết sản phẩm */
	margin-bottom: 20px; /* Khoảng cách dưới mỗi sản phẩm */
}

.finish-product-image {
	width: 80px; /* Kích thước cố định cho ảnh */
	height: auto;
	border-radius: 8px; /* Bo góc ảnh */
	object-fit: cover; /* Giữ tỷ lệ ảnh */
}

.finish-product-details {
	flex: 1; /* Chiếm không gian còn lại */
	word-wrap: break-word; /* Đảm bảo nội dung không bị tràn */
}

.summary-item {
	display: flex;
	justify-content: space-between;
	padding: 8px 0;
}

.total {
	display: flex;
	justify-content: space-between;
	font-weight: bold;
	padding: 10px 0;
	border-top: 2px solid #ccc;
	margin-top: 10px;
}

hr {
	margin: 15px 0;
	border: none;
	border-top: 1px solid #eee;
}

.support-info {
	margin-top: 20px; /* Khoảng cách phía trên */
	text-align: left; /* Căn trái */
	font-size: 0.9rem; /* Kích thước chữ */
	color: #555; /* Màu chữ */
	display: flex;
	align-items: center;
	gap: 8px;
}

.support-info i {
	font-size: 1.1rem; /* Kích thước biểu tượng */
	color: #999; /* Màu biểu tượng */
}

.support-info a {
	color: #007bff; /* Màu link */
	text-decoration: none; /* Bỏ gạch chân */
}

.support-info a:hover {
	text-decoration: underline; /* Gạch chân khi hover */
}
/* Mobile: Màn hình dưới 740px */
@media ( max-width : 739px) {
	.container {
		flex-direction: column;
		align-items: center;
		gap: 20px;
	}
	.main-content, .order-summary {
		max-width: 100%;
		width: 100%;
		margin: 0;
	}
}
/* Tablet: Màn hình từ 740px đến 1023px */
@media ( max-width : 1023px) {
	.container {
		flex-direction: column;
		align-items: center;
		gap: 20px;
	}
	.main-content, .order-summary {
		max-width: 90%;
		width: 100%;
	}
}

/* Mobile: Màn hình dưới 740px */
@media ( max-width : 739px) {
	.container {
		flex-direction: column;
		align-items: center;
		gap: 20px;
	}
	.main-content, .order-summary {
		max-width: 100%;
		width: 100%;
		margin: 0;
	}
}
</style>


</head>
<body>

	<%
	// Lấy user_id từ session
	Integer userId = (Integer) session.getAttribute("user_id");

	// Kiểm tra xem user đã đăng nhập chưa
	if (userId == null) {
		response.sendRedirect("login.jsp");
		return;
	}

	// Lấy danh sách giỏ hàng từ database

	String name = (String) request.getAttribute("name");
	String phone = (String) request.getAttribute("phone");
	String address = (String) request.getAttribute("address");
	String paymentMethod = (String) request.getParameter("payment");
	double totalPrice = (double) request.getAttribute("totalPrice");
	String orderId = (String) request.getAttribute("orderId");
	List<Cart> cartList = (List<Cart>) request.getAttribute("cartList");

	// Định dạng tiền tệ
	NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new java.util.Locale("vi", "VN"));
	%>
	<%@include file="all_component/header.jsp"%>
	<div class="container2">
		<div class="main-content">
			<h1>Đặt hàng thành công</h1>
			<p>
				Mã đơn hàng: <strong><%=orderId%></strong>
			</p>
			<p>Cảm ơn bạn đã mua hàng!</p>
			<div class="order-info">
				<h3>Thông tin đơn hàng</h3>
				<p>
					<strong>Thông tin giao hàng:</strong>
				</p>
				<p>
					<strong>Họ và tên:</strong>
					<%=name%></p>
				<p>
					<strong>Số điện thoại:</strong>
					<%=phone%></p>
				<p>
					<strong>Địa chỉ:</strong>
					<%=address%></p>
				<p><strong>Phương thức thanh toán:</strong> <%= request.getAttribute("payment") %></p>
			</div>
			<div class="button-container">
				<a href="product.jsp">Tiếp tục mua hàng</a>
				<a href="review.jsp?orderId=<%=orderId%>">Đánh giá đơn hàng</a>
			</div>
			<div class="support-info">
				<p>
					<i class="bx bx-help-circle"></i> Cần hỗ trợ? <a
						href="contact.html">Liên hệ chúng tôi</a>
				</p>
			</div>
		</div>

		<div class="order-detail">
			<h3>Chi tiết đơn hàng</h3>
			<%
			if (cartList != null && !cartList.isEmpty()) {
				for (Cart cartItem : cartList) {
					double itemPrice = cartItem.getPrice() * cartItem.getQuantity();
			%>
			<div class="finish-product-item1">
				<img src="product/<%=cartItem.getThumbnail()%>"
					alt="<%=cartItem.getProductName()%>" class="finish-product-image">
				<div class="finish-product-details">
					<p>
						<strong><%=cartItem.getProductName()%></strong>
					</p>
					<p>
						Số lượng:
						<%=cartItem.getQuantity()%></p>
					<p>
						Giá:
						<%=currencyFormat.format(cartItem.getPrice())%></p>
				</div>
			</div>
			<%
			}
			} else {
			%>
			<p>Không có sản phẩm nào trong đơn hàng.</p>
			<%
			}
			%>
			<div class="total">
				<span>Tổng cộng:</span> <span><%=currencyFormat.format(totalPrice)%></span>
			</div>
		</div>
	</div>
</body>
</html>
