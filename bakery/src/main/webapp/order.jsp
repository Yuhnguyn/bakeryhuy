<%@page import="com.DB.DBConnect"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.DAO.CartDAOImpl" %>
<%@ page import="com.entity.Cart" %>
<%@ page import="java.text.NumberFormat" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đặt hàng | The Cake Factory</title>
    <link rel="stylesheet" href="all_component/order.css">
    <link rel="stylesheet" href="all_component/style.css">
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
        CartDAOImpl cartDAO = new CartDAOImpl(DBConnect.getConn());
        List<Cart> cartList = cartDAO.getCartByUserId(userId);

        // Định dạng tiền tệ
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new java.util.Locale("vi", "VN"));
        double totalPrice = 0;
    %>
	<%@include file="all_component/header.jsp"%>
    <div class="container1">
        <!-- Form section -->
        <div class="order-form">
            <h1>Thông tin giao hàng</h1>
            <label for="fullname">Họ và tên</label>
            <input type="text" id="fullname" placeholder="Nhập họ và tên">
            
            <label for="phone">Số điện thoại</label>
            <input type="text" id="phone" placeholder="Nhập số điện thoại">
            
            <label for="address">Địa chỉ</label>
            <input type="text" id="address" placeholder="Nhập địa chỉ">
            
            <label for="city">Tỉnh/ Thành phố</label>
            <input type="text" id="city" placeholder="Nhập Tỉnh/ Thành phố">

            <label for="district">Quận/ Huyện</label>
            <input type="text" id="district" placeholder="Nhập Quận/ Huyệnỉ">

            <label for="ward">Phường / Xã</label>
            <input type="text" id="ward" placeholder="Nhập Phường / Xã">

            <div class="shipping-method">
                <h2>Phương thức vận chuyển</h2>
                <p>Vui lòng chọn tỉnh / thành để có danh sách phương thức vận chuyển.</p>
            </div>
            
            <div class="payment-method">
                <h2>Phương thức thanh toán</h2>
            <div class="payment-option">
                    <label>
                        <input type="radio" name="payment" value="cod" checked>
<!--                         <span class="icon">
                            <img src="/img/cod.svg" alt="COD Icon">
                        </span> -->
                        Thanh toán khi giao hàng (COD)
                    </label>
                </div>

                <div class="payment-option">
                    <label>
                        <input type="radio" name="payment" value="bank-transfer">
<!--                         <span class="icon">
                            <img src="/img/cod.svg" alt="Bank Transfer Icon">
                        </span> -->
                        Chuyển khoản qua ngân hàng
                    </label>
                    <div class="bank-details" id="bank-details" style="display: none;">
                        <p>Sau khi đặt hàng thành công, Snow Pastry sẽ liên hệ bạn để xác nhận đơn hàng.</p>
                        <p>Sau đó bạn vui lòng tiến hành chuyển khoản theo số tài khoản:</p>
                        <p><strong>STK:</strong> 0921004598</p>
                        <p><strong>NGUYEN MINH PHUONG</strong></p>
                        <p><strong>Ngân hàng Quân đội (MB Bank)</strong></p>
                    </div>
                </div>
                
            </div>
            
        </div>

        <div class="order-summary">
            <h2>Đơn hàng của bạn</h2>
  <%
                if (cartList != null && !cartList.isEmpty()) {
                    for (Cart cartItem : cartList) {
                        double itemPrice = cartItem.getPrice() * cartItem.getQuantity();
                        totalPrice += itemPrice;
            %>
            <div class="product-item">
                <img src="product/<%= cartItem.getThumbnail() %>" alt="<%= cartItem.getProductName() %>">
                <span><%= cartItem.getProductName() %></span>
                <span>Số lượng: <%= cartItem.getQuantity() %></span>
                <span>Thành tiền: <%= currencyFormat.format(itemPrice) %></span>
            </div>
            <%
                    }
                } else {
            %>
            <p>Giỏ hàng của bạn hiện đang trống. <a href="product-list.jsp">Quay lại mua sắm</a>.</p>
            <%
                }
            %>
            <hr>
            <div class="total">Tổng cộng: <%= currencyFormat.format(totalPrice) %></div>


            <button class="btn-submit">Hoàn tất đơn hàng</button>
        </div>
    </div>
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
    <script>
    document.addEventListener('DOMContentLoaded', () => {
        let menuIcon = document.querySelector('#menu-icon');
        let navbar = document.querySelector('.navbar');

        // Khi click vào menu-icon
        menuIcon.onclick = () => {
            navbar.classList.toggle('active'); // Thêm hoặc bỏ class 'active' khi click vào icon
        };
    });

    document.addEventListener('DOMContentLoaded', () => {
        // Swiper for the Carousel
        const carouselSwiper = new Swiper('.carousel', {
            loop: true, // Lặp lại các slide vô hạn
            autoplay: {
                delay: 3000, // Tự động chuyển sau 3 giây
                disableOnInteraction: false, // Không dừng lại khi người dùng tương tác
            },
            pagination: {
                el: '.swiper-pagination', // Phần tử pagination
                clickable: true, // Cho phép click vào các nút pagination
            },
            navigation: {
                nextEl: '.swiper-button-next', // Nút "Next"
                prevEl: '.swiper-button-prev', // Nút "Prev"
            },
        });

        // Swiper for the Home Section
        const homeSwiper = new Swiper('.home', {
            loop: true, // Lặp lại các slide vô hạn
            autoplay: {
                delay: 3000, // Tự động chuyển sau 3 giây
                disableOnInteraction: false, // Không dừng lại khi người dùng tương tác
            },
            pagination: {
                el: '.swiper-pagination', // Phần tử pagination
                clickable: true, // Cho phép click vào các nút pagination
            },
            navigation: {
                nextEl: '.swiper-button-next', // Nút "Next"
                prevEl: '.swiper-button-prev', // Nút "Prev"
            },
        });
    });

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


    document.addEventListener("DOMContentLoaded", function () {
        const bankDetails = document.getElementById("bank-details");
        const radioCod = document.querySelector('input[value="cod"]');
        const radioBankTransfer = document.querySelector('input[value="bank-transfer"]');

        // Lắng nghe sự kiện khi chọn radio button
        radioCod.addEventListener("change", function () {
            if (this.checked) {
                bankDetails.style.display = "none"; // Ẩn phần chi tiết ngân hàng
            }
        });

        radioBankTransfer.addEventListener("change", function () {
            if (this.checked) {
                bankDetails.style.display = "block"; // Hiển thị phần chi tiết ngân hàng
            }
        });
    });



    </script>
</body>
</html>
