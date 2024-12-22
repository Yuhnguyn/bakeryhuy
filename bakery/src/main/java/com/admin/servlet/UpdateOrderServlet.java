package com.admin.servlet;

import java.io.IOException;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.DAO.OrdersDAOImpl;
import com.DB.DBConnect;

@WebServlet("/UpdateOrderStatus")
public class UpdateOrderServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String orderId = request.getParameter("id");

        if (orderId != null) {
            OrdersDAOImpl dao = new OrdersDAOImpl(DBConnect.getConn());
            boolean isUpdated = dao.updateOrderStatus(orderId, "Compeleted", new Timestamp(System.currentTimeMillis()));

            if (isUpdated) {
                request.getSession().setAttribute("succMsg", "Đơn hàng giao thành công");
            } else {
                request.getSession().setAttribute("failMsg", "Failed to update the order!");
            }
        }
        response.sendRedirect("admin/orders.jsp"); // Redirect back to the orders page
    }
}

