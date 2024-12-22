package com.DAO;






import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.entity.OrderDetails;


public class OrderDetailsDAOImpl {
    
    private final Connection conn;

    // Constructor khởi tạo kết nối
    public OrderDetailsDAOImpl(Connection conn) {
        this.conn = conn;
    }


    // Phương thức hỗ trợ lấy top sản phẩm có doanh thu cao nhất theo tuần, tháng, năm
    private Map<String, Double> getTop5RevenueProducts(String timePeriod, String timeUnit) {

        Map<String, Double> result = new HashMap<>();
        String query = "SELECT p.name, SUM(od.price * od.num) AS total_revenue " +
                       "FROM order_details od " +
                       "JOIN product p ON od.product_id = p.id " +
                       "JOIN orders o ON od.order_id = o.id " +
                       "WHERE " + timeUnit + "(o.created_at) = " + timeUnit + "(CURDATE()) " +  // Lọc theo tuần/tháng/năm
                       "GROUP BY p.name " +
                       "ORDER BY total_revenue DESC " +
                       "LIMIT 5";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.put(rs.getString("name"), rs.getDouble("total_revenue"));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching top 5 products by revenue for " + timePeriod + ": " + e.getMessage());
        }
        return result;
    }

    // Lấy top 5 sản phẩm có doanh thu cao nhất trong tuần
    public Map<String, Double> getTop5RevenueProductsByWeek() {
        return getTop5RevenueProducts("week", "WEEK");
    }

    // Lấy top 5 sản phẩm có doanh thu cao nhất trong tháng
    public Map<String, Double> getTop5RevenueProductsByMonth() {
        return getTop5RevenueProducts("month", "MONTH");
    }

    // Lấy top 5 sản phẩm có doanh thu cao nhất trong năm
    public Map<String, Double> getTop5RevenueProductsByYear() {
        return getTop5RevenueProducts("year", "YEAR");
    }


    // Lấy doanh thu theo tháng
    public Map<String, Double> getRevenueByMonth() {
        Map<String, Double> result = new HashMap<>();
        String query = "SELECT YEAR(o.created_at) AS year, MONTH(o.created_at) AS month, SUM(od.price * od.num) AS total_revenue " +
                       "FROM order_details od " +
                       "JOIN orders o ON od.order_id = o.id " +
                       "GROUP BY YEAR(o.created_at), MONTH(o.created_at) " +
                       "ORDER BY year, month";  // Sắp xếp theo năm và tháng


        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String yearMonth = rs.getInt("year") + "-" + rs.getInt("month");
                result.put(yearMonth, rs.getDouble("total_revenue"));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching revenue by month: " + e.getMessage());
        }
        return result;
    }
}

