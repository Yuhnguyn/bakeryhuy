package com.user.servlet;

import com.DAO.CartDAOImpl;
import com.DB.DBConnect;
import com.entity.Cart;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/AddCartServlet")
public class AddCartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        String productId = request.getParameter("productId");
        String quantityStr = request.getParameter("quantity");

        if (productId == null || quantityStr == null) {
            response.sendRedirect("error.jsp");
            return;
        }

        int quantity = Integer.parseInt(quantityStr);

        Object userObj = session.getAttribute("userobj");

        if (userObj != null) {
            // ✅ Người dùng đã đăng nhập: lưu vào DB
            int userId = (int) session.getAttribute("user_id");

            Cart cartItem = new Cart();
            cartItem.setUserId(userId);
            cartItem.setProductId(productId);
            cartItem.setQuantity(quantity);

            CartDAOImpl cartDAO = new CartDAOImpl(DBConnect.getConn());
            boolean success = cartDAO.addCartItem(cartItem);

            if (success) {
                response.sendRedirect("cart.jsp");
            } else {
                response.sendRedirect("error.jsp");
            }
        } else {
            // ✅ Người dùng chưa đăng nhập: lưu vào session
            List<Cart> guestCart = (List<Cart>) session.getAttribute("guest_cart");
            if (guestCart == null) guestCart = new ArrayList<>();

            boolean found = false;
            for (Cart item : guestCart) {
                if (item.getProductId().equals(productId)) {
                    item.setQuantity(item.getQuantity() + quantity);
                    found = true;
                    break;
                }
            }

            if (!found) {
                Cart cartItem = new Cart();
                cartItem.setProductId(productId);
                cartItem.setQuantity(quantity);
                guestCart.add(cartItem);
            }

            session.setAttribute("guest_cart", guestCart);
            response.sendRedirect("cart.jsp");
        }
    }
}
