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
import com.DB.DBConnect;
import com.entity.Category;

@WebServlet("/update_category")
public class CategoryUpdate extends HttpServlet {
	@Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
        	// Thiết lập encoding cho request và response
            req.setCharacterEncoding("UTF-8");
            resp.setCharacterEncoding("UTF-8");
            
            String id = req.getParameter("id");
            String name = req.getParameter("category");
            String description = req.getParameter("description");
            
            Category category=new Category();
            category.setId(id);
            category.setName(name);
            category.setDescription(description);
            CategoryDAOImpl dao=new CategoryDAOImpl(DBConnect.getConn());
            boolean f=dao.updateCategory(category);
            
            HttpSession session = req.getSession();
            if(f) {
            	session.setAttribute("succMSg", "Cập nhật thành công");
            	resp.sendRedirect("admin/update_category.jsp");
            }else {
            	session.setAttribute("failMsg", "Cập nhật thất bại");
            	resp.sendRedirect("admin/update_category.jsp");

            }
            
        }catch(Exception e) {
        	e.printStackTrace();
        }

}
}
