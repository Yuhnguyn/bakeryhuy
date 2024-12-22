package com.admin.servlet;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.DAO.OrderDetailsDAOImpl;
import com.DB.DBConnect;
import com.entity.OrderDetails;

@WebServlet("/OrderDetails")
public class OrderDetailServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String orderId = request.getParameter("id");

        if (orderId != null) {
            OrderDetailsDAOImpl dao = new OrderDetailsDAOImpl(DBConnect.getConn());
            List<OrderDetails> detailsList = dao.getOrderDetailsByOrderId(orderId);

            request.setAttribute("orderDetails", detailsList);
            request.setAttribute("orderId", orderId);
            request.getRequestDispatcher("admin/order_detail.jsp").forward(request, response);
        } else {
            response.sendRedirect("admin/orders.jsp");
        }
    }
}
