<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.entity.Cart" %>
<%@ page import="java.text.NumberFormat" %>
<%@ include file="all_component/header.jsp" %>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <title>Đánh giá đơn hàng | Snow Pastry</title>
  <link rel="stylesheet" href="all_component/style.css">
  <style>
    body {
      background: #f3f9ff url('img/bg-review.png') no-repeat center center fixed;
      background-size: cover;
      font-family: 'Segoe UI', sans-serif;
    }
    .review-container {
      max-width: 800px;
      margin: 40px auto;
      padding: 30px;
      background: white;
      border-radius: 15px;
      box-shadow: 0 0 20px rgba(0, 0, 0, 0.08);
      
    }
    .review-container h2 {
      text-align: center;
      color: #333;
      margin-bottom: 20px;
    }
    .product-item {
      display: flex;
      align-items: center;
      margin-bottom: 15px;
      border-bottom: 1px solid #eee;
      padding-bottom: 10px;
    }
    .product-item img {
      width: 80px;
      height: 80px;
      border-radius: 10px;
      object-fit: cover;
      margin-right: 15px;
    }
    .product-info {
      flex: 1;
    }
    .product-info p {
      margin: 2px 0;
    }
    .form-group {
      margin-top: 20px;
    }
    label {
      display: block;
      font-weight: bold;
      margin-bottom: 5px;
    }
    input[type="text"], textarea, select {
      width: 100%;
      padding: 10px;
      border: 1px solid #ccc;
      border-radius: 6px;
    }
    textarea {
      resize: vertical;
      height: 100px;
    }
    .rating {
   flex-direction: row-reverse;
  display: flex;
  
}

.rating input {
  display: none;
}

.rating label {
  font-size: 24px;
  color: #ccc;
  cursor: pointer;
}

/* Bỏ màu mặc định khi chọn */
.rating input:checked ~ label {
  color: #ccc;
}

/* Tô màu trái → phải */
.rating input:checked + label,
.rating input:checked + label ~ label {
  color: #f8b400;
}

/* Hover từ trái sang phải */
.rating label:hover,
.rating label:hover ~ label {
  color: #f8b400;
}



    .btn-submit {
      width: 100%;
      padding: 12px;
      background: #f9a825;
      border: none;
      border-radius: 6px;
      color: white;
      font-weight: bold;
      cursor: pointer;
      margin-top: 20px;
    }
    .btn-submit:hover {
      background: #ef6c00;
    }
    .total {
      text-align: right;
      font-weight: bold;
      margin-top: 10px;
      font-size: 1.1rem;
    }
  </style>
</head>

<%
  String orderIdFromParam = request.getParameter("orderId");
  String successFlag = request.getParameter("success");
  if ("1".equals(successFlag)) {
%>
<script>
  alert("Cảm ơn bạn đã đánh giá! Bạn sẽ được chuyển về trang chủ.");
  setTimeout(function () {
    window.location.href = "home.jsp";
  }, 2000);
</script>
<%
  }

  List<Cart> cartList = (List<Cart>) session.getAttribute("reviewCartList");
  Double totalPrice = (Double) session.getAttribute("reviewTotalPrice");



  NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new java.util.Locale("vi", "VN"));
%>

<body>
  <div class="review-container">
    <h2>Đánh giá đơn hàng</h2>

    <% if (cartList != null && !cartList.isEmpty()) {
        for (Cart cart : cartList) {
    %>
    <div class="product-item">
      <img src="product/<%=cart.getThumbnail()%>" alt="<%=cart.getProductName()%>">
      <div class="product-info">
        <p><strong><%=cart.getProductName()%></strong></p>
        <p>Số lượng: <%=cart.getQuantity()%></p>
        <p>Giá: <%=currencyFormat.format(cart.getPrice())%></p>
        <p>Thành tiền: <%=currencyFormat.format(cart.getPrice() * cart.getQuantity())%></p>
      </div>
    </div>
    <% } %>
    <div class="total">
      Tổng cộng: <%=currencyFormat.format(totalPrice)%>
    </div>
    <% } else { %>
      <p>Không có sản phẩm trong đơn hàng.</p>
    <% } %>

    <form action="SubmitReviewServlet" method="post" style="margin-top: 20px; " >
      <input type="hidden" name="order_id" value="<%= orderIdFromParam != null ? orderIdFromParam : "" %>">

      <div class="form-group">
        <label style="display: block; text-align: right;"><strong>Chất lượng bánh:</strong></label>

        <div class="rating">
  <div class="rating">
  <input type="radio" id="star1" name="rating" value="1"><label for="star1">★</label>
  <input type="radio" id="star2" name="rating" value="2"><label for="star2">★</label>
  <input type="radio" id="star3" name="rating" value="3"><label for="star3">★</label>
  <input type="radio" id="star4" name="rating" value="4"><label for="star4">★</label>
  <input type="radio" id="star5" name="rating" value="5"><label for="star5">★</label>
</div>


</div>

      </div>

      <div class="form-group">
        <label style="display: block; text-align: center; for="comment">Nhận xét chi tiết:</label>
        <textarea id="comment" name="comment" placeholder="Viết nhận xét về bánh, đóng gói, shipper..." required></textarea>
      </div>

      <button type="submit" class="btn-submit">Gửi đánh giá</button>
    </form>
  </div>
</body>
</html>
