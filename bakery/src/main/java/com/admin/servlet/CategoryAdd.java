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

@WebServlet("/add_category")
@MultipartConfig
public class CategoryAdd extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
        	// Thiết lập encoding cho request và response
            req.setCharacterEncoding("UTF-8");
            resp.setCharacterEncoding("UTF-8");
            
            String id = req.getParameter("id");
            String name = req.getParameter("category");
            String description = req.getParameter("description");
            Part part = req.getPart("thumbnail");
            String fileName = part.getSubmittedFileName();
            

            
            
           
            // Lưu vào cơ sở dữ liệu
            CategoryDAOImpl dao = new CategoryDAOImpl(DBConnect.getConn());
            Category category = new Category(id, name, description, fileName, null, null);
            boolean isAdded = dao.addCategory(category);

            HttpSession session = req.getSession();
            if (isAdded) {
            	// Đường dẫn lưu ảnh
                String uploadPath = getServletContext().getRealPath("")+"category";
                File file=new File(uploadPath);
                
                part.write(uploadPath+File.separator+fileName);
                //System.out.println(uploadPath);

                session.setAttribute("succMsg", "Thêm danh mục thành công!");
                resp.sendRedirect("admin/all_category.jsp");
            } else {
                session.setAttribute("failMsg", "Thêm danh mục thất bại!");
                resp.sendRedirect("admin/add_category.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace();
            req.getSession().setAttribute("failMsg", "Có lỗi xảy ra!");
            resp.sendRedirect("admin/add_category.jsp");
        }
    }
}

	
