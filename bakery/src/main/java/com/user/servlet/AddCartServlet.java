package com.user.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.entity.Cart;
@WebServlet("/CartServlet")
public class AddCartServlet extends HttpServlet {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        String action = request.getParameter("action");
	        HttpSession session = request.getSession();

	        // Lấy danh sách giỏ hàng từ session
	        List<Cart> cartList = (List<Cart>) session.getAttribute("cart");
	        if (cartList == null) {
	            cartList = new ArrayList<>();
	        }

	        if ("add".equals(action)) {
	            // Lấy thông tin sản phẩm từ form
	            String productId = request.getParameter("productId");
	            String productName = request.getParameter("productName");
	            double price = Double.parseDouble(request.getParameter("price"));
	            String thumbnail = request.getParameter("thumbnail");
	            int quantity = Integer.parseInt(request.getParameter("quantity"));

	            // Kiểm tra xem sản phẩm đã có trong giỏ chưa
	            boolean exists = false;
	            for (Cart item : cartList) {
	                if (item.getProductId().equals(productId)) {
	                    item.setQuantity(item.getQuantity() + quantity);
	                    exists = true;
	                    break;
	                }
	            }

	            // Nếu chưa có, thêm mới
	            if (!exists) {
	                Cart cartItem = new Cart();
	                cartItem.setProductId(productId);
	                cartItem.setProductName(productName);
	                cartItem.setPrice(price);
	                cartItem.setThumbnail(thumbnail);
	                cartItem.setQuantity(quantity);
	                cartList.add(cartItem);
	            }

	            // Lưu lại giỏ hàng vào session
	            session.setAttribute("cart", cartList);
	            response.sendRedirect("cart.jsp");
	        }
	    }
	}

