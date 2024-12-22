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

    public boolean saveOrderDetail(OrderDetails orderDetail) {
        boolean result = false;
        try {
            String query = "INSERT INTO order_details (order_id, product_id, price, num) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, orderDetail.getOrderId());
            ps.setString(2, orderDetail.getProductId());
            ps.setDouble(3, orderDetail.getPrice());
            ps.setInt(4, orderDetail.getNum());
            result = ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    
    public List<OrderDetails> getOrderDetailsByOrderId(String orderId) {
        List<OrderDetails> orderDetails = new ArrayList<>();
        try {
            String query = "SELECT od.*, p.name AS product_name, p.thumbnail AS product_thumbnail " +
                           "FROM order_details od " +
                           "JOIN product p ON od.product_id = p.id " +
                           "WHERE od.order_id = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, orderId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                OrderDetails orderDetail = new OrderDetails();
                orderDetail.setId(rs.getInt("id"));
                orderDetail.setOrderId(rs.getString("order_id"));
                orderDetail.setProductId(rs.getString("product_id"));
                orderDetail.setPrice(rs.getInt("price"));
                orderDetail.setNum(rs.getInt("num"));
                orderDetail.setProductName(rs.getString("product_name"));
                orderDetail.setThumbnail(rs.getString("product_thumbnail"));
                orderDetails.add(orderDetail);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orderDetails;
    }
    
    
    
    
    
    
    
    
    

    // Lấy top 5 sản phẩm có doanh thu cao nhất trong tuần
    public Map<String, Double> getTop5RevenueProductsByWeek() {
        Map<String, Double> result = new HashMap<>();
        String query = "SELECT p.name, SUM(od.price * od.num) AS total_revenue " +
                       "FROM order_details od " +
                       "JOIN product p ON od.product_id = p.id " +
                       "JOIN orders o ON od.order_id = o.id " +
                       "WHERE WEEK(o.created_at) = WEEK(CURDATE()) " + // Lọc theo tuần hiện tại
                       "GROUP BY p.name " +
                       "ORDER BY total_revenue DESC " +
                       "LIMIT 5";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.put(rs.getString("name"), rs.getDouble("total_revenue"));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching top 5 products by revenue for the week: " + e.getMessage());
        }
        return result;
    }

    // Lấy top 5 sản phẩm có doanh thu cao nhất trong tháng
    public Map<String, Double> getTop5RevenueProductsByMonth() {
        Map<String, Double> topRevenueProducts = new HashMap<>();
        String sql = "SELECT p.name, SUM(od.price * od.num) AS revenue " +
                     "FROM order_details od " +
                     "JOIN product p ON od.product_id = p.id " +
                     "JOIN orders o ON od.order_id = o.id " +
                     "WHERE MONTH(o.created_at) = MONTH(CURDATE()) " +
                     "GROUP BY p.name " +
                     "ORDER BY revenue DESC " +
                     "LIMIT 5";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                topRevenueProducts.put(rs.getString("name"), rs.getDouble("revenue"));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching top 5 products by revenue for the month: " + e.getMessage());
        }
        return topRevenueProducts;
    }

    // Lấy top 5 sản phẩm có doanh thu cao nhất trong năm
    public Map<String, Double> getTop5RevenueProductsByYear() {
        Map<String, Double> result = new HashMap<>();
        String query = "SELECT p.name, SUM(od.price * od.num) AS total_revenue " +
                       "FROM order_details od " +
                       "JOIN product p ON od.product_id = p.id " +
                       "JOIN orders o ON od.order_id = o.id " +
                       "WHERE YEAR(o.created_at) = YEAR(CURDATE()) " + // Lọc theo năm hiện tại
                       "GROUP BY p.name " +
                       "ORDER BY total_revenue DESC " +
                       "LIMIT 5";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.put(rs.getString("name"), rs.getDouble("total_revenue"));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching top 5 products by revenue for the year: " + e.getMessage());
        }
        return result;
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

