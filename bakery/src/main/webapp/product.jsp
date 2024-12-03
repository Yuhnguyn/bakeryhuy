<%@page import="com.entity.Product"%>
<%@page import="com.DAO.ProductDAOImpl"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.List" %>
<%@ page import="com.DB.DBConnect" %>
<%@ page import="com.DAO.CategoryDAOImpl" %>
<%@ page import="com.entity.Category" %>
<%@page isELIgnored="false"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Cake is for life</title>
<!--Link to CSS -->

<link rel="stylesheet" href="${pageContext.request.contextPath}/all_component/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/all_component/product.css">
<!--box Icons -->
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/boxicons@latest/css/boxicons.min.css">
<!--Link Swiper Css-->
<link rel="stylesheet"
	href="https://unpkg.com/swiper/swiper-bundle.min.css" />

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">

</head>
<body>
	<!-- Navbar -->
	<%@include file="all_component/header.jsp"%>
	<!-- Product-->
	<section id="products">
		<h2 style="margin-top:100px";>Tất cả sản phẩm</h2>
		<!-- Lặp qua các danh mục và hiển thị tên danh mục -->
        <%
            // Giả sử bạn đã có một phương thức trong DAO để lấy danh sách các danh mục từ CSDL
            CategoryDAOImpl dao = new CategoryDAOImpl(DBConnect.getConn());
        	ProductDAOImpl productDAO= new ProductDAOImpl(DBConnect.getConn());
            List<Category> categories = dao.getAllCategories();
            for (Category category : categories) {
            	List<Product> productsInCategory = productDAO.getProductsByCategoryId(category.getId());
        %>

        <div class="product-category">
            <!-- Hiển thị tên danh mục -->
            <h3 id="<%= category.getName()%>">
                <%= category.getName() %>
            </h3>
            <div class="products-container">
                <div class="product-grid">
                <%
                    for (Product product : productsInCategory) {
                %>
                <div class="product-card">
                
						<img img src="product/<%= product.getThumbnail() %>" alt="<%= product.getName() %>">
						<h3><%= product.getName() %></h3>
						<h4 class="price"><%= product.getPrice() %> VNĐ</h4>
						<a href="product-detail.jsp"> <i
							class='bx bx-cart-alt cart-icon'></i>
						</a> <i class='bx bx-heart heart-icon'></i>

					</div>
											
        <%
            }
        %>
                </div>
                
            </div>
            
        <% } %>
        </div>

	
	</section>

	<footer>
		<!-- Footer -->
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
	<!-- Copyright -->
	<div class="copy-right">
		<p>&#169; CarpoolVenom All Rights Reserved. Công ty TNHH Snow Việt
			Nam.</p>
	</div>
</body>
</html>
