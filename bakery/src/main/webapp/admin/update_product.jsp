<%@page import="com.entity.Product"%>
<%@page import="com.DAO.ProductDAOImpl"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="com.DB.DBConnect"%>
<%@page import="com.entity.Category"%>
<%@page import="com.DAO.CategoryDAOImpl"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<style>
body {
	margin: 0;
	padding: 0;
	display: flex;
	justify-content: center;
	align-items: center;
}

.form-container {
	background-color: #fff;
	padding: 20px;
	border: 1px solid #ddd;
	border-radius: 5px;
	box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
	width: 400px;
	text-align: center; /* Canh giữa nội dung trong form-container */
}

h2 {
	margin-top: 100px;
	margin-bottom: 10px;
	font-size: 24px;
	color: #333;
}

form div {
	margin-bottom: 15px;
	text-align: left;
}

label {
	font-weight: bold;
	display: block;
	margin-bottom: 5px;
}

input[type="text"], input[type="number"], textarea, select, input[type="file"]
	{
	width: 100%;
	padding: 8px;
	border: 1px solid #ddd;
	border-radius: 4px;
	box-sizing: border-box;
}

button {
	width: 100%;
	padding: 10px 20px;
	border: none;
	border-radius: 5px;
	cursor: pointer;
	background-color: #4CAF50;
	color: white;
	font-size: 16px;
}

button:hover {
	background-color: #45a049;
}

.message {
            padding: 10px;
            margin-bottom: 15px;
            border-radius: 5px;
            font-size: 14px;
            font-weight: bold;
            text-align: center;
        }

        .message.success {
            color: #155724;
            background-color: #d4edda;
            border: 1px solid #c3e6cb;
        }

        .message.error {
            color: #721c24;
            background-color: #f8d7da;
            border: 1px solid #f5c6cb;
        }
</style>

</head>
<body>
	<%@include file="header.jsp"%>


	<div class="form-container">
		<h2>Chỉnh sửa sản phẩm</h2>
		
		<!-- Hiển thị thông báo -->
        <c:if test="${not empty succMsg}">
            <div class="message success">${succMsg}</div>
            <c:remove var="succMsg" scope="session" />
        </c:if>
        <c:if test="${not empty failMsg}">
            <div class="message error">${failMsg}</div>
            <c:remove var="failMsg" scope="session" />
        </c:if>
        
        <%
        String id=request.getParameter("id");
        ProductDAOImpl dao2=new ProductDAOImpl(DBConnect.getConn());
       	Product product= dao2.getProductById(id);
        
        %>
        
		<form action="../update_product" method="POST">

			<div>
				<label for="id">Mã sản phẩm:</label> <input type="text" id="id"
					name="id" value="<%=product.getId() %>" readonly>
			</div>

			<div>
				<label for="category">Danh mục:</label> <select id="category"
					name="category" >
										<option value="">Chọn danh mục</option>
					<%
				CategoryDAOImpl dao = new CategoryDAOImpl(DBConnect.getConn());
				List<Category> list = dao.getAllCategories();
				for (Category category : list) {
					String selected = "";
	                if (category.getId().equals(product.getCategoryId())) {
	                    selected = "selected";  // Chọn danh mục hiện tại của sản phẩm
	                }
					%>

					<option value="<%=category.getId() %>" <%= selected %>><%=category.getName() %></option>
					<%
						}
					%>
				</select>
			</div>
			<div>
				<label for="name">Tên sản phẩm:</label> <input type="text" id="name"
					name="name" value="<%=product.getName() %>">
			</div>
			<div>
				<label for="price">Giá sản phẩm:</label> <input type="number"
					id="price" name="price" value="<%=product.getPrice() %>">
			</div>
			<div>
				<label for="discount">Mức giảm giá:</label> <input type="number"
					id="discount" name="discount" value="<%=product.getDiscount() %>">
			</div>
			
			<div>
				<label for="description">Mô tả:</label>
				<textarea id="description" name="description" rows="4" ><%=product.getDescription() %></textarea>
			</div>
			<%-- <div>
			
				<label for="image">Hình ảnh:</label> 
				<img src="../product/<%=product.getThumbnail() %>" alt="<%=product.getThumbnail() %>" style="width: 50px; height: 50px;">
				<label for="image">Chọn hình ảnh mới:</label> 
				<input type="file" id="thumbnail"
					name="thumbnail" accept="image/*" >
					
			</div> --%>
			<button type="submit">Xác nhận</button>
		</form>
	</div>
</body>
</html>