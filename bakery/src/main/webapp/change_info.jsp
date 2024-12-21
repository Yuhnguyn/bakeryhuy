<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Chỉnh sửa thông tin cá nhân</title>
<link rel="stylesheet" href="all_component/style.css">
<style>
.container1 {
    max-width: 800px;
    margin: 0 auto;
    background-color: #fff;
    border: 1px solid #ddd;
    border-radius: 8px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    padding: 120px;
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
</style>
</head>
<body>

<%@include file="all_component/header.jsp"%>

<div class="container1">
    <h2>Chỉnh sửa thông tin cá nhân</h2>
    <form action="UpdateUserServlet" method="post">
        <div class="form-group">
            <label for="name">Họ và tên:</label>
            <input type="text" id="name" name="name" value="${userobj.name}" required>
        </div>
        <div class="form-group">
            <label for="phone">Số điện thoại:</label>
            <input type="text" id="phone" name="phone" value="${userobj.phone}" required>
        </div>
        <div class="action-buttons">
            <button type="submit" class="save-btn">Lưu thay đổi</button>
            <button type="button" class="cancel-btn" onclick="window.location.href='infor_user.jsp'">Hủy</button>
        </div>
    </form>
</div>

</body>
</html>