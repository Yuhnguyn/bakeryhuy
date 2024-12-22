package com.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.DB.DBConnect;
import com.entity.Orders;

public class OrdersDAOImpl implements OrdersDAO {
	private Connection conn;

	public OrdersDAOImpl(Connection conn) {
		this.conn = conn;
	}

	@Override
	public boolean saveOrder(Orders order) {
	    String sql = "INSERT INTO orders (id, user_id, name, phone, address, status, total_money,payment_method) VALUES (?, ?, ?, ?, ?, ?, ?,?)";
	    try (PreparedStatement ps = conn.prepareStatement(sql)) {
	        ps.setString(1, order.getId());
	        ps.setInt(2, order.getUserId());
	        ps.setString(3, order.getName());
	        ps.setString(4, order.getPhone());
	        ps.setString(5, order.getAddress());
	        ps.setString(6, order.getStatus());
	        ps.setDouble(7, order.getTotalMoney());
	        ps.setString(8, order.getPaymentMethod());
	        return ps.executeUpdate() > 0;
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return false;
	}
	@Override
	public Orders getOrderById(String orderId) {
        Orders order = null;
        try {
            String query = "SELECT * FROM orders WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, orderId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                order = new Orders();
                order.setId(rs.getString("id"));
                order.setUserId(rs.getInt("user_id"));
                order.setName(rs.getString("name"));
                order.setPhone(rs.getString("phone"));
                order.setAddress(rs.getString("address"));
                order.setTotalMoney(rs.getDouble("total_money"));
                order.setStatus(rs.getString("status"));
                order.setCreatedAt(rs.getTimestamp("created_at"));
                order.setPaymentMethod(rs.getString("payment_method"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return order;
    }
	@Override
	public List<Orders> getOrdersByUserId(int userId) {
        List<Orders> ordersList = new ArrayList<>();
        try {
            String query = "SELECT * FROM orders WHERE user_id = ? ORDER BY created_at DESC";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Orders order = new Orders();
                order.setId(rs.getString("id"));
                order.setUserId(rs.getInt("user_id"));
                order.setName(rs.getString("name"));
                order.setPhone(rs.getString("phone"));
                order.setAddress(rs.getString("address"));
                order.setStatus(rs.getString("status"));
                order.setTotalMoney(rs.getDouble("total_money"));
                order.setCreatedAt(rs.getTimestamp("created_at"));
                ordersList.add(order);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ordersList;
    }
	
	@Override
	public Map<String, Object> getSalesOrdersByMonth() {
	    Map<String, Object> salesOrders = new LinkedHashMap<>();

	    String sql = "SELECT MONTH(created_at) AS order_month, COUNT(id) AS sales_count " +
	                 "FROM orders " +
	                 "GROUP BY order_month " +
	                 "ORDER BY order_month";

	    try (Connection conn = DBConnect.getConn();
	         PreparedStatement ps = conn.prepareStatement(sql);
	         ResultSet rs = ps.executeQuery()) {

	        while (rs.next()) {
	            salesOrders.put(rs.getString("order_month"), rs.getInt("sales_count"));
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return salesOrders;
	}

	@Override
	public List<Orders> getAllOrders() {
		 List<Orders> ordersList = new ArrayList<>();
	        String query = "SELECT * FROM orders";

	        try (PreparedStatement preparedStatement = conn.prepareStatement(query);
	             ResultSet resultSet = preparedStatement.executeQuery()) {

	            while (resultSet.next()) {
	                Orders order = new Orders();
	                order.setId(resultSet.getString("id"));
	                order.setUserId(resultSet.getInt("user_id"));
	                order.setName(resultSet.getString("name"));
	                order.setPhone(resultSet.getString("phone"));
	                order.setAddress(resultSet.getString("address"));
	                order.setCreatedAt(resultSet.getTimestamp("created_at"));
	                order.setApprovedAt(resultSet.getTimestamp("approved_at"));
	                order.setStatus(resultSet.getString("status"));
	                order.setTotalMoney(resultSet.getDouble("total_money"));
	                order.setPaymentMethod(resultSet.getString("payment_method"));

	                ordersList.add(order);
	            }

	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        return ordersList;
	}
	@Override
	public boolean updateOrderStatus(String orderId, String status, Timestamp approvedAt) {
        boolean isUpdated = false;

        try {
            String sql = "UPDATE orders SET status = ?, approved_at = ? WHERE id = ? AND status = 'Pending'";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, status);
            pstmt.setTimestamp(2, approvedAt);
            pstmt.setString(3, orderId);

            int updatedRows = pstmt.executeUpdate();
            if (updatedRows > 0) {
                isUpdated = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isUpdated;
    }






	
}
