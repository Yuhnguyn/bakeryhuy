<%@ page import="com.DAO.CartDAOImpl" %>
<%@ page import="com.entity.Cart" %>
<%@ page import="com.DB.DBConnect" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.NumberFormat" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Giỏ hàng</title>
    <link rel="stylesheet" href="all_component/style.css">
    <link rel="stylesheet" href="all_component/cart.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/boxicons@latest/css/boxicons.min.css">
</head>
<body>

<%@include file="all_component/header.jsp"%>

<%
    List<Cart> cartList = null;
    NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new java.util.Locale("vi", "VN"));
    double totalPrice = 0;

    Object userObj = session.getAttribute("userobj");

    if (userObj != null) {
        // Đã đăng nhập → lấy giỏ hàng từ DB
        try {
            int userId = (int) session.getAttribute("user_id");
            CartDAOImpl dao = new CartDAOImpl(DBConnect.getConn());
            cartList = dao.getCartByUserId(userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    } else {
        // Chưa đăng nhập → lấy giỏ hàng từ session
        cartList = (List<Cart>) session.getAttribute("guest_cart");
    }
%>

<main class="cart-container">
    <h1>Giỏ hàng</h1>
    <%
        if (cartList != null && !cartList.isEmpty()) {
    %>
        <p>Bạn đang có <strong><%= cartList.size() %></strong> sản phẩm trong giỏ hàng</p>

    <%
            for (Cart cartItem : cartList) {
                // Nếu người dùng chưa đăng nhập, cartItem có thể thiếu tên/giá/ảnh, bạn cần query thêm nếu muốn
                double itemPrice = (cartItem.getPrice() > 0 ? cartItem.getPrice() : 0) * cartItem.getQuantity();
                totalPrice += itemPrice;
    %>
        <div class="cart-item">
            <img src="product/<%= cartItem.getThumbnail() != null ? cartItem.getThumbnail() : "default.jpg" %>" alt="<%= cartItem.getProductName() %>" class="main-image">
            <div class="item-details">
    <h2><%= cartItem.getProductName() != null ? cartItem.getProductName() : "Tên sản phẩm" %></h2>
    <p class="price">Giá: <%= cartItem.getPrice() > 0 ? currencyFormat.format(cartItem.getPrice()) : "?" %></p>
    <p class="quantity">Số lượng: <%= cartItem.getQuantity() %></p>
    <p class="total-item-price">Thành tiền: <%= currencyFormat.format(itemPrice) %></p>

    <div class="item-controls">
        <form action="<%= userObj != null ? "UpdateCartServlet" : "UpdateGuestCartServlet" %>" method="post">
            <input type="hidden" name="productId" value="<%= cartItem.getProductId() %>">
            <button type="submit" name="action" value="decrease" class="btn-quantity">-</button>
            <input type="number" name="quantity" value="<%= cartItem.getQuantity() %>" min="1">
            <button type="submit" name="action" value="increase" class="btn-quantity">+</button>
        </form>
        <form action="<%= userObj != null ? "DeleteCartServlet" : "DeleteGuestCartServlet" %>" method="post">
            <input type="hidden" name="productId" value="<%= cartItem.getProductId() %>">
            <button type="submit" class="btn-delete"><i class='bx bx-trash'></i></button>
        </form>
    </div>
</div>

        </div>
    <%
            }
        } else {
    %>
        <p>Giỏ hàng của bạn hiện đang trống.</p>
    <%
        }
    %>

    <aside class="order-summary">
        <h3>Thông tin đơn hàng</h3>
        <p><span>Tổng tiền:</span> <strong><%= currencyFormat.format(totalPrice) %></strong></p>

        <%
            if (userObj != null) {
        %>
            <a href="OrderServlet" class="checkout-btn">Thanh toán</a>
        <%
            } else {
        %>
            <a href="login.jsp" class="checkout-btn">Đăng nhập để thanh toán</a>
        <%
            }
        %>
        <a href="product.jsp" class="continue-shopping">Tiếp tục mua hàng →</a>
    </aside>
</main>
<!-- Footer -->
    <section class="footer" id="footer">
        <div class="footer-box">
            <a href="#" class="logo"><i class='bx bxs-basket'></i>Snow Pastry</a>
            <p>24, Phố Hai Bà Trưng, Tràng Tiền <br>Hoàn Kiếm, Hà Nội</p>
            <div class="social">
                <a href="#"><i class='bx bxl-facebook'></i></a>
                <a href="#"><i class='bx bxl-twitter'></i></a>
                <a href="#"><i class='bx bxl-instagram'></i></a>
                <a href="#"><i class='bx bxl-youtube'></i></a>
            </div>
        </div>
        <div class="footer-box">
            <h2>Danh mục</h2>
            <a href="#">Bread</a>
            <a href="#">Pastry & Pie</a>
            <a href="#">Bingsu</a>
            <a href="#">Cake</a>
        </div>
        <div class="footer-box">
            <h2>Thông tin liên hệ</h2>
            <a href="#">Hotline tư vấn: 1900 779 907</a>
            <a href="#">Hotline khiếu nại: 0948225982</a>
            <a href="#">Liên hệ hợp tác: 0921004598</a>
            <a href="#">Email: support@snow.vn</a>
        </div>
        <div class="footer-box">
            <h2>Newsletter</h2>
            <p>Get 10% Discount with <br>Email Newsletter</p>
            <form action="">
                <i class='bx bxs-envelope'></i>
                <input type="email" name="" id="" placeholder="Nhập Email của bạn">
                <i class='bx bx-arrow-back bx-rotate-180'></i>
            </form>
        </div>
    </section>
     <!-- Copyright -->
     <div class="copy-right">
        <p>&#169; CarpoolVenom All Rights Reserved. Công ty TNHH Snow Việt Nam.</p>
    </div>
</body>
</html>
