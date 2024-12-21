package com.DAO;

import java.util.Map;

import com.entity.Orders;

public interface OrdersDAO {
	public boolean addOrder(Orders order);
	public Map<String, Object> getSalesOrdersByMonth();
}
