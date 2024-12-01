package com.admin.servlet;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.DAO.CategoryDAOImpl;
import com.DAO.ProductDAOImpl;
import com.DB.DBConnect;
import com.entity.Category;
import com.entity.Product;
import com.google.protobuf.BoolValueOrBuilder;


@WebServlet("/add_product")
@MultipartConfig
public class ProductAdd extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			req.setCharacterEncoding("UTF-8");
            resp.setCharacterEncoding("UTF-8");
            
            String id = req.getParameter("id");
            String categoryId = req.getParameter("category");
            String name = req.getParameter("name");
            String price=req.getParameter("price");	
            String discount=req.getParameter("discount");
            String description = req.getParameter("description");
            Part part = req.getPart("thumbnail");
            String fileName = part.getSubmittedFileName();
            
            // Sử dụng phương thức getCategoryById từ CategoryDAOImpl để lấy thông tin danh mục
            CategoryDAOImpl categoryDao = new CategoryDAOImpl(DBConnect.getConn());
            Category category1 = categoryDao.getCategoryById(categoryId);  // Lấy thông tin danh mục
            String category=category1.getName();
            
            
            ProductDAOImpl dao=new ProductDAOImpl(DBConnect.getConn());
            Product product=new Product(id, categoryId,category,name, price, discount, description, null, null, fileName);
            
            boolean f=dao.addProduct(product);
            HttpSession session=req.getSession();
            if(f) {
                	// Đường dẫn lưu ảnh
                    String uploadPath = getServletContext().getRealPath("")+"product";
                    File file=new File(uploadPath);
                    
                    part.write(uploadPath+File.separator+fileName);
                    //System.out.println(uploadPath);

                    session.setAttribute("succMsg", "Thêm sản phẩm thành công!");
                    resp.sendRedirect("admin/add_product.jsp");
                } else {
                    session.setAttribute("failMsg", "Thêm sản phẩm thất bại!");
                    resp.sendRedirect("admin/add_product.jsp");
            }
            
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

}
