package com.admin.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.DAO.CategoryDAOImpl;
import com.DB.DBConnect;

@WebServlet("/delete")
public class CategoryDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			String id = req.getParameter("id");
			CategoryDAOImpl dao = new CategoryDAOImpl(DBConnect.getConn());
			boolean f = dao.deleteCategory(id);

			HttpSession session = req.getSession();
			if (f) {
				session.setAttribute("succMsg", "Xóa danh mục thành công!");
			} else {
				session.setAttribute("failMsg", "Xóa danh mục thất bại");				
			}
			resp.sendRedirect("admin/all_category.jsp");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}