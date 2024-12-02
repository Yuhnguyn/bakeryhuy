package com.admin.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.DAO.ProductDAOImpl;
import com.DB.DBConnect;
import com.entity.Product;

@WebServlet("/update_product")
public class ProductUpdate extends HttpServlet {
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
        	// Thiết lập encoding cho request và response
            req.setCharacterEncoding("UTF-8");
            resp.setCharacterEncoding("UTF-8");
            
            String id = req.getParameter("id");
            String categoryId = req.getParameter("category");
            String name = req.getParameter("name");
            String price=req.getParameter("price");	
            String discount=req.getParameter("discount");
            String description = req.getParameter("description");
            
            Product product=new Product();
            product.setId(id);
            product.setCategoryId(categoryId);
            product.setName(name);
            product.setPrice(price);
            product.setDiscount(discount);
            product.setDescription(description);
            
            ProductDAOImpl dao=new ProductDAOImpl(DBConnect.getConn());
            boolean f=dao.updateProduct(product);
            HttpSession session=req.getSession();
            if (f) {
            	session.setAttribute("succMsg", "Cập nhật sản phẩm thành công!");
                resp.sendRedirect("admin/all_product.jsp");
			}else {
				session.setAttribute("failMsg", "Cập nhật sản phẩm thất bại!");
                resp.sendRedirect("admin/all_product.jsp");
			}
            
            
            
        	}catch (Exception e) {
				e.printStackTrace();
			}
	}
}
