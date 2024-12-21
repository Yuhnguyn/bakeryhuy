package com.user.servlet;

import com.DAO.CartDAOImpl;
import com.DB.DBConnect;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/UpdateCartServlet")
public class UpdateCartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String productId = request.getParameter("productId");
        String quantityStr = request.getParameter("quantity");

        if (action == null || productId == null) {
            response.sendRedirect("error.jsp");
            return;
        }

        try {
            int userId = (int) request.getSession().getAttribute("user_id");
            CartDAOImpl dao = new CartDAOImpl(DBConnect.getConn());

            if (action.equals("increase")) {
                dao.updateCartItem(userId, productId, 1); // Tăng số lượng lên 1
            } else if (action.equals("decrease")) {
                dao.updateCartItem(userId, productId, -1); // Giảm số lượng đi 1
            } else if (action.equals("set")) {
                int quantity = Integer.parseInt(quantityStr);
                dao.setCartItemQuantity(userId, productId, quantity); // Cập nhật số lượng cụ thể
            }
            response.sendRedirect("cart.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}
