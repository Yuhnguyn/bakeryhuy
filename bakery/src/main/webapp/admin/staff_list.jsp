<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.DAO.StaffDAOImpl" %>
<%@ page import="com.entity.Staff" %>
<%@ page import="java.util.List" %>
<%@ page import="com.DB.DBConnect" %>
<!DOCTYPE html>
<html lang="vi">
<head>
	<link rel="stylesheet" href="https://cdn.datatables.net/2.1.8/css/dataTables.dataTables.css" />
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script> <!-- Đảm bảo rằng jQuery đã được tải -->
    <script src="https://cdn.datatables.net/2.1.8/js/dataTables.min.js"></script> <!-- Tải DataTables JS -->
    <script>
        $(document).ready(function() {
            $('#staff').DataTable();  
        });
    </script>
    <meta charset="UTF-8">
    <title>Danh Sách Nhân Viên</title>
</head>

  <style>
        h2 {
            padding: 20px;
            font-size:20px;
        }
        
	
        .history-table {
            margin-top: 100px;
            margin-left: 50px;
        }

        .history-table table {
            width: 70%;
            border-collapse: collapse;
        }

        .history-table table, .history-table th, .history-table td {
            border: 1px solid #ddd;
        }

        .history-table th, .history-table td {
            padding: 10px;
            text-align: left;
            font-size: 16px;
        }

        .history-table th {
            background-color: #f2f2f2;
        }

        .history-table {
            white-space: nowrap; /* Không ngắt dòng */
            width: 200px; /* Điều chỉnh chiều rộng theo ý muốn */
        }


        .button{
            font-size: 14px;
            font-weight: bold;
            padding: 8px 16px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        .btn-approve {
            background-color: #4CAF50;
            color: white;
        }

        .btn-approve:hover {
            background-color: #45a049;
        }

        .btn-reject {
            background-color: #f44336;
            color: white;
        }

        .btn-reject:hover {
            background-color: #d32f2f;
            
            button:hover {
    background-color: #45a049;
}
        }
    </style>
<body>
<%@ include file="header.jsp" %>

<%
    String success = request.getParameter("success");
    String error = request.getParameter("error");
%>
<% if ("1".equals(success)) { %>
    <div style="color: green; text-align: center;">Thao tác thành công!</div>
<% } else if ("1".equals(error)) { %>
    <div style="color: red; text-align: center;">Có lỗi xảy ra!</div>
<% } %>

<div class="history-table">
    


  <%
        StaffDAOImpl staffDAO = new StaffDAOImpl();
        String searchKey = request.getParameter("searchKey");
        String filter = request.getParameter("filter");

        List<Staff> staffList;
        if (searchKey == null || searchKey.trim().isEmpty()) {
            staffList = staffDAO.getAllStaff();
        } else {
            staffList = staffDAO.searchStaff(searchKey, filter != null && !filter.isEmpty() ? filter : "all");
        }
    %>
<h2>Danh sách nhân viên</h2>
    <table id="staff" class="display">
        <thead>
            <tr>
                <th>ID</th>
                <th>Họ tên</th>
                <th>Số điện thoại</th>
                <th>Tên đăng nhập</th>
                <th>Mật khẩu</th>
                <th>Vai trò</th>
                <th>Thời gian tạo</th>
                <th>Hành động</th>
            </tr>
        </thead>
        <tbody>
            <%
                if (staffList != null && !staffList.isEmpty()) {
                    for (Staff staff : staffList) {
            %>
            <tr>
                <td><%= staff.getId() %></td>
                <td><%= staff.getName() %></td>
                <td><%= staff.getPhone() %></td>
                <td><%= staff.getUsername() %></td>
                <td><%= staff.getPassword() %></td>
                <td><%= staff.getRole() %></td>
                <td><%= staff.getCreatedAt() != null ? staff.getCreatedAt().toString() : "N/A" %></td>
                <td>
                    <a href="<%= request.getContextPath() %>/admin/edit_staff.jsp?id=<%= staff.getId() %>" class="button btn-approve">Chỉnh sửa</a>
                    <a href="<%= request.getContextPath() %>/admin/delete_staff.jsp?id=<%= staff.getId() %>" class="button btn-reject">Xóa</a>
                </td>
            </tr>
            <%
                    }
                } else {
            %>
            <tr>
                <td colspan="6" style="text-align:center;">Không tìm thấy nhân viên nào.</td>
            </tr>
            <%
                }
            %>
        </tbody>
    </table>
</div>
</body>
</html>
