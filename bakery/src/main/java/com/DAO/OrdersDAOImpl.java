package com.DAO;

import java.sql.Connection;

import com.entity.Orders;

public class OrdersDAOImpl implements OrdersDAO {
	private Connection conn;
	public OrdersDAOImpl(Connection conn) {
		this.conn=conn;
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
	
}
