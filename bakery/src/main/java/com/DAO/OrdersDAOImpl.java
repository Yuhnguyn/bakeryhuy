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
	public boolean addOrder(Orders order) {
		boolean f=false;
		try {
			String sql="insert into orders(id;user_id;name;phone;address;";
		} catch (Exception e) {
			// TODO: handle exception
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
