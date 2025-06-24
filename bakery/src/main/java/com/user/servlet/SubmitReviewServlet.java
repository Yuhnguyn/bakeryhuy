package com.user.servlet;

import com.DAO.ReviewDAOImpl;
import com.DB.DBConnect;
import com.entity.Review;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Timestamp;

@WebServlet("/SubmitReviewServlet")
public class SubmitReviewServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Integer userId = (Integer) session.getAttribute("user_id");

        if (userId == null) {
            resp.sendRedirect("login.jsp");
            return;
        }

        String orderId = req.getParameter("order_id");
        String ratingStr = req.getParameter("rating");
        String comment = req.getParameter("comment");

        try {
            int rating = Integer.parseInt(ratingStr);

            Review review = new Review();
            review.setUserId(userId);
            review.setOrderId(orderId);
            review.setRating(rating);
            review.setComment(comment);
            review.setCreatedAt(new Timestamp(System.currentTimeMillis()));

            ReviewDAOImpl dao = new ReviewDAOImpl(DBConnect.getConn());
            boolean success = dao.addReview(review);

            if (success) {
                session.setAttribute("succMsg", "Cảm ơn bạn đã đánh giá!");
            } else {
                session.setAttribute("failMsg", "Gửi đánh giá thất bại. Vui lòng thử lại.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("failMsg", "Lỗi xử lý đánh giá.");
        }

        resp.sendRedirect("review.jsp?success=1");
        session.removeAttribute("reviewCartList");
        session.removeAttribute("reviewTotalPrice");


    }
}
