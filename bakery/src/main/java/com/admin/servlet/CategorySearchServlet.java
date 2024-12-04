package com.admin.servlet;

import com.DAO.CategoryDAOImpl;
import com.DB.DBConnect;
import com.entity.Category;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/CategorySearchServlet") 
public class CategorySearchServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String keyword = request.getParameter("keyword");
        String sort = request.getParameter("sort");

        CategoryDAOImpl dao = new CategoryDAOImpl(DBConnect.getConn());
        List<Category> categories = dao.searchCategories(keyword, sort != null ? sort : "all"); // Default là "all"

        // Truyền các thuộc tính vào request
        request.setAttribute("categories", categories);
        request.setAttribute("keyword", keyword);
        request.setAttribute("sort", sort);

        // Chuyển tiếp đến trang all_category.jsp
        request.getRequestDispatcher("/admin/all_category.jsp").forward(request, response);
    }
}
