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

@WebServlet("/deleteProduct")
public class ProductDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			String id = req.getParameter("id");
			ProductDAOImpl dao = new ProductDAOImpl(DBConnect.getConn());
			boolean f = dao.deleteProduct(id);

			HttpSession session = req.getSession();
			if (f) {
				session.setAttribute("succMsg", "Xóa sản phẩm thành công!");
			} else {
				session.setAttribute("failMsg", "Xóa sản phẩm thất bại");				
			}
			resp.sendRedirect("admin/all_product.jsp");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}