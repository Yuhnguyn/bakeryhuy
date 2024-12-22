package com.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedHashMap;
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
	    String sql = "INSERT INTO orders (id, user_id, name, phone, address, status, total_money) VALUES (?, ?, ?, ?, ?, ?, ?)";
	    try (PreparedStatement ps = conn.prepareStatement(sql)) {
	        ps.setString(1, order.getId());
	        ps.setInt(2, order.getUserId());
	        ps.setString(3, order.getName());
	        ps.setString(4, order.getPhone());
	        ps.setString(5, order.getAddress());
	        ps.setString(6, order.getStatus());
	        ps.setDouble(7, order.getTotalMoney());
	        return ps.executeUpdate() > 0;
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return false;
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




	
}
