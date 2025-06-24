package com.DAO;

import com.entity.Review;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ReviewDAOImpl {
    private Connection conn;

    public ReviewDAOImpl(Connection conn) {
        this.conn = conn;
    }

    public boolean addReview(Review r) {
        boolean f = false;
        try {
            String sql = "INSERT INTO review(user_id, order_id, rating, comment, created_at) VALUES(?,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, r.getUserId());
            ps.setString(2, r.getOrderId());
            ps.setInt(3, r.getRating());
            ps.setString(4, r.getComment());
            ps.setTimestamp(5, r.getCreatedAt());

            int i = ps.executeUpdate();
            f = (i == 1);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return f;
    }
}
