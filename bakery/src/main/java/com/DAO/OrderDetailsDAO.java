package com.DAO;

import java.util.Map;

import com.entity.OrderDetails;

public interface OrderDetailsDAO {
	
	public boolean saveOrderDetail(OrderDetails orderDetail);
	
    // Phương thức lấy Top 5 sản phẩm có doanh thu cao nhất
    public Map<String, Object> getTop5RevenueProducts();

    // Phương thức lấy doanh thu theo tháng
    public Map<String, Object> getRevenueByMonth();

    // Phương thức lấy Top 5 sản phẩm có doanh thu cao nhất theo tuần
    public Map<String, Object> getTop5RevenueProductsByWeek();

    // Phương thức lấy Top 5 sản phẩm có doanh thu cao nhất theo tháng
    public Map<String, Object> getTop5RevenueProductsByMonth();

    // Phương thức lấy Top 5 sản phẩm có doanh thu cao nhất theo năm
    public Map<String, Object> getTop5RevenueProductsByYear();
}
