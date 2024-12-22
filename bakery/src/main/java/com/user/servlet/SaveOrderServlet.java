package com.user.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.DB.DBConnect;
import com.DAO.OrdersDAOImpl;
import com.entity.Orders;
@WebServlet("/SaveOrderServlet")
public class SaveOrderServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	// Thiết lập encoding cho request và response
    	request.setCharacterEncoding("UTF-8");
    	response.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();

        // Lấy user_id từ session
        Integer userId = (Integer) session.getAttribute("user_id");
        if (userId == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Lấy dữ liệu từ form
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        double totalPrice = Double.parseDouble(request.getParameter("totalPrice"));
        System.out.println("Total Price: " + request.getParameter("totalPrice"));


        // Tạo đối tượng Order
        Orders order = new Orders();
        order.setId("OD-"+UUID.randomUUID().toString().substring(0, 8));
        order.setUserId(userId);
        order.setName(name);
        order.setPhone(phone);
        order.setAddress(address);
        order.setStatus("Pending");
        order.setTotalMoney(totalPrice);

        // Lưu order vào database
        try (Connection conn = DBConnect.getConn()) {
            OrdersDAOImpl orderDAO = new OrdersDAOImpl(conn);
            boolean isSaved = orderDAO.saveOrder(order);

            if (isSaved) {
                // Xóa giỏ hàng sau khi đặt hàng thành công
                String deleteCartSql = "DELETE FROM cart WHERE user_id = ?";
                PreparedStatement pstmt = conn.prepareStatement(deleteCartSql);
                pstmt.setInt(1, userId);
                pstmt.executeUpdate();

                // Chuyển hướng người dùng đến trang xác nhận
                response.sendRedirect("order_success.jsp");
            } else {
                response.sendRedirect("error.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
