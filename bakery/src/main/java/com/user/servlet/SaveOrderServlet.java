package com.user.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.DB.DBConnect;
import com.DAO.CartDAOImpl;
import com.DAO.OrderDetailsDAOImpl;
import com.DAO.OrdersDAOImpl;
import com.entity.Cart;
import com.entity.OrderDetails;
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
        String paymentMethod = request.getParameter("payment");
        double totalPrice = Double.parseDouble(request.getParameter("totalPrice"));

        String orderId = "OD-"+UUID.randomUUID().toString().substring(0, 8);
        // Tạo đối tượng Order
        Orders order = new Orders();
        order.setId(orderId);
        order.setUserId(userId);
        order.setName(name);
        order.setPhone(phone);
        order.setAddress(address);
        order.setPaymentMethod(paymentMethod);
        order.setStatus("Pending");
        order.setTotalMoney(totalPrice);

        OrdersDAOImpl orderDAO = new OrdersDAOImpl(DBConnect.getConn());
        boolean orderSaved = orderDAO.saveOrder(order);

        if (orderSaved) {
            // Fetch Cart Data from DB
            CartDAOImpl cartDAO = new CartDAOImpl(DBConnect.getConn());
            List<Cart> cartList = cartDAO.getCartByUserId(userId);

            if (cartList != null && !cartList.isEmpty()) {
                // Save Order Details
                OrderDetailsDAOImpl orderDetailDAO = new OrderDetailsDAOImpl(DBConnect.getConn());
                for (Cart cartItem : cartList) {
                    OrderDetails orderDetail = new OrderDetails();
                    orderDetail.setOrderId(orderId);
                    orderDetail.setProductId(cartItem.getProductId());
                    orderDetail.setPrice(cartItem.getPrice());
                    orderDetail.setNum(cartItem.getQuantity());
                    orderDetailDAO.saveOrderDetail(orderDetail);
                }
            }

            // Clear Cart for the User
            cartDAO.clearCartByUserId(userId);
            // Truyền thông tin sang trang order_success.jsp
            request.setAttribute("name", name);
            request.setAttribute("phone", phone);
            request.setAttribute("address", address);
            request.setAttribute("payment", paymentMethod);
            request.setAttribute("totalPrice", totalPrice);
            request.setAttribute("cartList", cartList);
            request.setAttribute("orderId", orderId);

            // Chuyển hướng đến order_success.jsp
            request.getRequestDispatcher("order_success.jsp").forward(request, response);
        } else {
            session.setAttribute("errorMsg", "Không thể lưu đơn hàng. Vui lòng thử lại.");
            response.sendRedirect("order.jsp");
        }
    }
}
