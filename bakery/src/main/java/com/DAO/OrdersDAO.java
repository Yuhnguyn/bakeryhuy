package com.DAO;

import java.util.List;
import java.util.Map;

import com.entity.Orders;

public interface OrdersDAO {
	public boolean saveOrder(Orders order);
	public Map<String, Object> getSalesOrdersByMonth();
	Orders getOrderById(String orderId);
	List<Orders> getOrdersByUserId(int userId);
}
