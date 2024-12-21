<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<title>Đổi mật khẩu</title>
<link rel="stylesheet" href="all_component/style.css">
<style>
.container1 {
    max-width: 600px;
    margin: 0 auto;
    background-color: #fff;
    border: 1px solid #ddd;
    border-radius: 8px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    padding: 40px;
}

.container1 h2 {
    text-align: left;
    font-size: 24px;
    font-weight: bold;
    border-bottom: 2px solid #007bff;
    padding-bottom: 10px;
    margin-bottom: 20px;
}

.form-group {
    margin-bottom: 15px;
}

.form-group label {
    display: block;
    font-weight: bold;
    margin-bottom: 5px;
}

.form-group input {
    width: 100%;
    padding: 10px;
    border: 1px solid #ddd;
    border-radius: 5px;
}

.action-buttons {
    text-align: center;
    margin-top: 20px;
}

.action-buttons button {
    font-size: 14px;
    padding: 10px 20px;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    margin: 5px;
    color: #fff;
}

.save-btn {
    background-color: #5cb85c;
}

.cancel-btn {
    background-color: #6c757d;
}

.message {
    text-align: center;
    margin-top: 20px;
    font-size: 16px;
}

.error-msg {
    color: red;
}

.success-msg {
    color: green;
}

.hidden {
    display: none;
}
</style>
<script>
// Tự động ẩn thông báo sau 3 giây
document.addEventListener("DOMContentLoaded", function () {
    setTimeout(function () {
        const message = document.getElementById("message-box");
        if (message) {
            message.classList.add("hidden");
        }
    }, 3000); // 3000ms = 3 giây
});
</script>
</head>
<body>
<%@include file="all_component/header.jsp"%>

<div class="container1">
    <h2>Đổi mật khẩu</h2>
    <form action="ChangePasswordServlet" method="post">
        <div class="form-group">
            <label for="currentPassword">Mật khẩu hiện tại:</label>
            <input type="password" id="currentPassword" name="currentPassword" required>
        </div>
        <div class="form-group">
            <label for="newPassword">Mật khẩu mới:</label>
            <input type="password" id="newPassword" name="newPassword" required>
        </div>
        <div class="form-group">
            <label for="confirmPassword">Xác nhận mật khẩu mới:</label>
            <input type="password" id="confirmPassword" name="confirmPassword" required>
        </div>
        <div class="action-buttons">
            <button type="submit" class="save-btn">Lưu thay đổi</button>
            <button type="button" class="cancel-btn" onclick="window.location.href='infor_user.jsp'">Hủy</button>
        </div>
    </form>

    <!-- Hiển thị thông báo chỉ khi có thông báo lỗi hoặc thành công -->
    <div id="message-box" class="message">
        <c:if test="${not empty errorMsg}">
            <p class="error-msg">${errorMsg}</p>
        </c:if>
        <c:if test="${not empty succMsg}">
            <p class="success-msg">${succMsg}</p>
        </c:if>
    </div>
</div>

</body>
</html>
