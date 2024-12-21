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

@WebServlet("/OrderServlet")
public class OrderServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        // Lấy user_id từ session
        Integer userId = (Integer) session.getAttribute("user_id");
        if (userId == null) {
            response.sendRedirect("login.jsp"); // Nếu chưa đăng nhập, chuyển về trang đăng nhập
            return;
        }

        // Lấy danh sách sản phẩm trong giỏ hàng
        CartDAOImpl cartDAO = new CartDAOImpl(DBConnect.getConn());
        List<Cart> cartList = cartDAO.getCartByUserId(userId);

        // Kiểm tra nếu giỏ hàng trống
        if (cartList == null || cartList.isEmpty()) {
            response.sendRedirect("cart.jsp");
            return;
        }

        // Lưu giỏ hàng vào request và chuyển đến order.jsp
  
        request.getRequestDispatcher("order.jsp").forward(request, response);
    }
}
