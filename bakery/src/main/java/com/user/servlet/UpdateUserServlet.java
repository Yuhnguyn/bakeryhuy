package com.user.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.DAO.UserDAO;
import com.DAO.UserDAOImpl;
import com.DB.DBConnect;
import com.entity.User;

@WebServlet("/UpdateUserServlet")
public class UpdateUserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("userobj");

        if (user != null) {
            String name = request.getParameter("name");
            String phone = request.getParameter("phone");

            user.setName(name);
            user.setPhone(phone);

            UserDAO dao = new UserDAOImpl(DBConnect.getConn());
            boolean updated = dao.updateUserInfo(user);

            if (updated) {
                session.setAttribute("userobj", user);
                session.setAttribute("succMsg", "");
                response.sendRedirect("infor_user.jsp");
            } else {
                session.setAttribute("errorMsg", "Cập nhật thông tin thất bại.");
                response.sendRedirect("change_info.jsp");
            }
        } else {
            response.sendRedirect("login.jsp");
        }
    }
}