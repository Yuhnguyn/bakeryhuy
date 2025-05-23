<%@page import="com.entity.Product"%>
<%@page import="java.util.List"%>
<%@page import="com.DB.DBConnect"%>
<%@page import="com.DAO.ProductDAOImpl"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@page isELIgnored="false"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Cake is for life</title>
<!-- Link to CSS -->

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/all_component/product.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/all_component/product-detail.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/all_component/style.css">
<!-- box Icons -->
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/boxicons@latest/css/boxicons.min.css">
<style>
</style>

</head>
<body>
	<!-- Navbar -->

	<%@include file="all_component/header.jsp"%>
	<!-- Product Detail Section -->


	<%
                // Lấy productId từ tham số URL
                String id = request.getParameter("id");

                // Tạo đối tượng DAO để truy xuất dữ liệu
                ProductDAOImpl productDAO = new ProductDAOImpl(DBConnect.getConn());
                Product product = productDAO.getProductById(id); // Lấy sản phẩm theo id
            %>
	<section class="product-detail">
		<div class="product-detail-container">
			<div class="product-info">
				<h1><%=product.getName() %></h1>
				<p class="price"><%=product.getFormattedBalance()%>
					VND
				</p>
			</div>
			<!-- Product Image Section -->
			<div class="product-image">
				<img src="product/<%= product.getThumbnail() %>" alt="<%= product.getName() %>" class="main-image">
				<div class="thumbnail-container">
					<div class="thumbnail">
						<img src="product/<%= product.getThumbnail() %>" alt="<%= product.getName() %>">
					</div>
					<div class="thumbnail">
						<img src="img/product-xmas-cake1.webp" alt="Thumbnail">
					</div>
					<div class="thumbnail">
						<img src="img/product-xmas-cake5.jpg" alt="Thumbnail">
					</div>
				</div>
			</div>

			<div class="order-section">
			 <form action="AddCartServlet" method="post">
				<div class="order-controls">
					<button class="quantity-btn">-</button>
					<input type="number" value="1" min="1" name="quantity" class="quantity-input">
					<button class="quantity-btn">+</button>
				</div>
				<div class="order-buttons">
   
        <input type="hidden" name="action" value="add">
        <input type="hidden" name="productId" value="<%= product.getId() %>">
        <input type="hidden" name="productName" value="<%= product.getName() %>">
        <input type="hidden" name="price" value="<%= product.getPrice() %>">
        <input type="hidden" name="thumbnail" value="<%= product.getThumbnail() %>">
        <button type="submit" class="btn add-to-cart">THÊM VÀO GIỎ</button>
  
					<button class="btn buy-now" onclick="window.location.href='order.jsp?id=<%=product.getId() %>'">MUA NGAY</button>
				</div>
  </form>
				<div class="product-description">
					<h3>Mô tả</h3>
					<p>
						<strong>Chi tiết sản phẩm:</strong>
					</p>
					<ul>
						<li><strong>Size bánh:</strong>
							<ul>
								<li>Đường kính: 20cm</li>
								<li>Độ cao mỗi tầng: 6cm</li>
							</ul></li>
						<li><strong>Cốt bánh:</strong> Vanilla butter, Chocolate
							butter, Củ dền, Đậu phộng, Matcha, Chocomint</li>
						<li><strong>Nhân bánh:</strong> Mứt dâu/ thơm/ việt quất, Kem
							bơ truyền thống, Sốt phô mai/ sốt trứng muối</li>
						<li><small>* Kích thước trên chỉ là phần thân bánh,
								chưa bao gồm phần trang trí.</small></li>
					</ul>
					<h3>Hướng dẫn bảo quản:</h3>
					<ul>
						<li>Bánh để ngoài nhiệt độ thường 3-4 tiếng.</li>
						<li>Phòng máy lạnh và trời mát 4-6 tiếng.</li>
						<li>Không để bánh gần thực phẩm hoặc hộp kín, bảo quản trong
							ngăn mát tủ lạnh.</li>
					</ul>
				</div>
			</div>
		</div>
	</section>
	<!-- Footer -->
	<footer>
		<section class="footer" id="footer">
			<div class="footer-box">
				<a href="#" class="logo"><i class='bx bxs-basket'></i>Snow
					Pastry</a>
				<p>
					24, Phố Hai Bà Trưng, Tràng Tiền <br>Hoàn Kiếm, Hà Nội
				</p>
				<div class="social">
					<a href="#"><i class='bx bxl-facebook'></i></a> <a href="#"><i
						class='bx bxl-twitter'></i></a> <a href="#"><i
						class='bx bxl-instagram'></i></a> <a href="#"><i
						class='bx bxl-youtube'></i></a>
				</div>
			</div>
			<div class="footer-box">
				<h2>Danh mục</h2>
				<a href="#">Bread</a> <a href="#">Pastry & Pie</a> <a href="#">Bingsu</a>
				<a href="#">Cake</a>
			</div>
			<div class="footer-box">
				<h2>Thông tin liên hệ</h2>
				<a href="#">Hotline tư vấn: 1900 779 907</a> <a href="#">Hotline
					khiếu nại: 0948225982</a> <a href="#">Liên hệ hợp tác: 0921004598</a> <a
					href="#">Email: support@snow.vn</a>
			</div>
			<div class="footer-box">
				<h2>Newsletter</h2>
				<p>
					Get 10% Discount with <br>Email Newsletter
				</p>
				<form action="">
					<i class='bx bxs-envelope'></i> <input type="email" name="" id=""
						placeholder="Nhập Email của bạn"> <i
						class='bx bx-arrow-back bx-rotate-180'></i>
				</form>
			</div>
		</section>
	</footer>

	<div class="copy-right">
		<p>&#169; CarpoolVenom All Rights Reserved. Công ty TNHH Snow Việt
			Nam.</p>
	</div>

	<!-- Swiper JS -->
	<script
		src="https://cdn.jsdelivr.net/npm/swiper@11/swiper-bundle.min.js"></script>
	<script>
    document.addEventListener('DOMContentLoaded', () => {
        const thumbnails = document.querySelectorAll('.thumbnail img');
        const mainImage = document.querySelector('.main-image');

        thumbnails.forEach(thumbnail => {
            thumbnail.addEventListener('click', () => {
                // Thay đổi src của mainImage thành src của thumbnail được nhấn vào
                mainImage.src = thumbnail.src;
            });
        });
    });

    

    document.addEventListener('DOMContentLoaded', () => {
        const quantityInput = document.querySelector('.quantity-input');
        const quantityMinus = document.querySelector('.quantity-btn:first-child');
        const quantityPlus = document.querySelector('.quantity-btn:last-child');

        // Xử lý sự kiện click cho nút giảm số lượng
        quantityMinus.addEventListener('click', (event) => {
            event.preventDefault(); // Ngăn chặn hành vi mặc định của nút
            let currentQuantity = parseInt(quantityInput.value);
            if (currentQuantity > 1) { // Đảm bảo số lượng không giảm xuống dưới 1
                quantityInput.value = currentQuantity - 1;
            }
        });

        // Xử lý sự kiện click cho nút tăng số lượng
        quantityPlus.addEventListener('click', (event) => {
            event.preventDefault(); // Ngăn chặn hành vi mặc định của nút
            let currentQuantity = parseInt(quantityInput.value);
            quantityInput.value = currentQuantity + 1;
        });
    });



    
    </script>
</body>
</html>
