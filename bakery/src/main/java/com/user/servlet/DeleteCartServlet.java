package com.user.servlet;

import com.DAO.CartDAOImpl;
import com.DB.DBConnect;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebServlet("/DeleteCartServlet")
public class DeleteCartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productId = request.getParameter("productId");

        if (productId == null) {
            response.sendRedirect("error.jsp");
            return;
        }

        try {
            int userId = (int) request.getSession().getAttribute("user_id");
            CartDAOImpl dao = new CartDAOImpl(DBConnect.getConn());
            dao.deleteCartItem(userId, productId);
            response.sendRedirect("cart.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}
