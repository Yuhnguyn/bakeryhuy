<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
    <!-- Navbar -->
	<%@include file="all_component/header.jsp"%>
    <main class="cart-container">
        <h1>Giỏ hàng</h1>
        <p>Bạn đang có <strong>1 sản phẩm</strong> trong giỏ hàng</p>
        
        <div class="cart-item">
            <img src="/img/catebread.png" alt="">
            <div class="item-details">
                <h2>Bánh kem sinh nhật</h2>
                <p class="price">100,000₫</p>
                <div class="item-controls">
                    <button class="btn-quantity">-</button>
                    <input type="number" value="1" min="1">
                    <button class="btn-quantity">+</button>
                    <i class='bx bx-trash'></i>
                </div>
            </div>
        </div>

        <textarea placeholder="Ghi chú đơn hàng"></textarea>
        
        <aside class="order-summary">
            <h3>Thông tin đơn hàng</h3>
            <p><span>Tổng tiền:</span> <strong>900,000₫</strong></p>
            <button class="checkout-btn">Thanh toán</button>
            <a href="#" class="continue-shopping">Tiếp tục mua hàng →</a>
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
    <!-- Link To JS -->
    <script src="/main.js"></script>
</body>
</html>