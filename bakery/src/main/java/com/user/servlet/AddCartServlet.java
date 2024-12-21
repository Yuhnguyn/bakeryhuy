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
import java.util.List;

@WebServlet("/AddCartServlet") // Định nghĩa URL của Servlet
public class AddCartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        // Kiểm tra người dùng đăng nhập
        Object userObj = session.getAttribute("userobj");
        if (userObj == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Lấy userId từ session
        int userId = (int) session.getAttribute("user_id");

        // Lấy dữ liệu từ request
        String productId = request.getParameter("productId");
        String quantityStr = request.getParameter("quantity");
        

        if (productId == null || quantityStr == null) {
            response.sendRedirect("error.jsp");
            return;
        }

        int quantity = Integer.parseInt(quantityStr);

        // Tạo đối tượng Cart
        Cart cartItem = new Cart();
        cartItem.setUserId(userId);
        cartItem.setProductId(productId);
        cartItem.setQuantity(quantity);

        // Gọi DAO để thêm hoặc cập nhật sản phẩm
        CartDAOImpl cartDAO = new CartDAOImpl(DBConnect.getConn());
        boolean success = cartDAO.addCartItem(cartItem);

        if (success) {
            response.sendRedirect("cart.jsp");
        } else {
            response.sendRedirect("error.jsp");
        }
    }


}
