package com.user.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.DAO.UserDAOImpl;
import com.DB.DBConnect;
import com.entity.User;
@WebServlet("/login")
public class LoginServlet extends HttpServlet{
	//source->override->doPost
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			UserDAOImpl dao=new UserDAOImpl(DBConnect.getConn());
			
			HttpSession session=req.getSession();
			session.removeAttribute("userobj");//xóa thông tin người dùng cũ
			String username = req.getParameter("username");
			String password = req.getParameter("password");
			
			if("admin@gmail.com".equals(username)&& "admin".equals(password)) {
				User us=new User();
				us.setName("Admin");
				session.setAttribute("userobj",us);
				resp.sendRedirect("admin/home.jsp");
			}else {
				
				User us=dao.login(username, password);
				if (us!=null) {
					session.setAttribute("userobj",us);
					session.setAttribute("user_id", us.getId());
					resp.sendRedirect("home.jsp");
				}
				else {
					session.setAttribute("failMsg","Tên đăng nhập hoặc mật khẩu không đúng");
					resp.sendRedirect("login.jsp");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
