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

@WebServlet("/ChangePasswordServlet")
public class ChangePasswordServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("userobj");

        if (user != null) {
            String currentPassword = request.getParameter("currentPassword");
            String newPassword = request.getParameter("newPassword");
            String confirmPassword = request.getParameter("confirmPassword");

            // Kiểm tra nếu các trường không rỗng
            if (currentPassword == null || newPassword == null || confirmPassword == null 
                || currentPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
                request.setAttribute("errorMsg", "Vui lòng điền đầy đủ thông tin.");
                request.getRequestDispatcher("change_password.jsp").forward(request, response);
                return;
            }

            // Kiểm tra mật khẩu hiện tại
            if (!currentPassword.equals(user.getPassword())) {
                request.setAttribute("errorMsg", "Mật khẩu hiện tại không đúng.");
                request.getRequestDispatcher("change_password.jsp").forward(request, response);
                return;
            }

            // Kiểm tra mật khẩu mới và mật khẩu xác nhận có khớp
            if (!newPassword.equals(confirmPassword)) {
                request.setAttribute("errorMsg", "Mật khẩu mới và xác nhận mật khẩu không khớp.");
                request.getRequestDispatcher("change_password.jsp").forward(request, response);
                return;
            }

            // Kiểm tra mật khẩu mới có khác mật khẩu hiện tại không
            if (newPassword.equals(currentPassword)) {
                request.setAttribute("errorMsg", "Mật khẩu mới phải khác mật khẩu hiện tại.");
                request.getRequestDispatcher("change_password.jsp").forward(request, response);
                return;
            }

            // Cập nhật mật khẩu mới vào cơ sở dữ liệu
            try {
                UserDAO dao = new UserDAOImpl(DBConnect.getConn());
                boolean updated = dao.updatePassword(user.getId(), newPassword);

                if (updated) {
                    // Cập nhật mật khẩu trong session
                    user.setPassword(newPassword);
                    session.setAttribute("userobj", user);
                    request.setAttribute("succMsg", "Mật khẩu đã được thay đổi thành công.");
                } else {
                    request.setAttribute("errorMsg", "Đổi mật khẩu thất bại. Vui lòng thử lại.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("errorMsg", "Lỗi hệ thống. Vui lòng thử lại sau.");
            }

            request.getRequestDispatcher("change_password.jsp").forward(request, response);
        } else {
            response.sendRedirect("login.jsp");
        }
    }
}

