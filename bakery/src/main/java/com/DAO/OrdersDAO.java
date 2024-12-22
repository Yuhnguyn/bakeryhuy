package com.DAO;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import com.entity.Orders;

public interface OrdersDAO {
	public boolean saveOrder(Orders order);
	public Map<String, Object> getSalesOrdersByMonth();
	Orders getOrderById(String orderId);
	List<Orders> getOrdersByUserId(int userId);
	List<Orders> getAllOrders();
	boolean updateOrderStatus(String orderId, String status, Timestamp approvedAt);
}
