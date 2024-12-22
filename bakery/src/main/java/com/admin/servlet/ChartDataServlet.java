package com.admin.servlet;

import com.DAO.ProductDAOImpl;
import com.DAO.OrderDetailsDAOImpl;
import com.DAO.OrdersDAOImpl;
import com.DB.DBConnect;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/chart-data")
public class ChartDataServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Tạo kết nối
        Connection conn = DBConnect.getConn();

        // Tạo các DAO với đối tượng Connection
        ProductDAOImpl productDAO = new ProductDAOImpl(conn);
        OrdersDAOImpl orderDAO = new OrdersDAOImpl(conn);
        OrderDetailsDAOImpl orderDetailsDAO = new OrderDetailsDAOImpl(conn);

        // Lấy dữ liệu từ DAO
        Map<String, Object> topProducts = productDAO.getTop5Products(); // Sản phẩm bán chạy
        Map<String, Object> salesOrders = orderDAO.getSalesOrdersByMonth(); // Doanh thu theo tháng
       
        Map<String, Double> revenueByMonth = orderDetailsDAO.getRevenueByMonth(); // Doanh thu theo tháng
        
        // Lấy dữ liệu Top 5 sản phẩm theo tuần, tháng, năm
        Map<String, Double> topRevenueProductsByWeek = orderDetailsDAO.getTop5RevenueProductsByWeek(); // Doanh thu theo tuần
        Map<String, Double> topRevenueProductsByMonth = orderDetailsDAO.getTop5RevenueProductsByMonth(); // Doanh thu theo tháng
        Map<String, Double> topRevenueProductsByYear = orderDetailsDAO.getTop5RevenueProductsByYear(); // Doanh thu theo năm

        // Kiểm tra dữ liệu lấy được
        System.out.println("Top Products: " + topProducts);
        System.out.println("Sales Orders: " + salesOrders);
     
        System.out.println("Revenue by Month: " + revenueByMonth);
        System.out.println("Top Revenue Products by Week: " + topRevenueProductsByWeek);
        System.out.println("Top Revenue Products by Month: " + topRevenueProductsByMonth);
        System.out.println("Top Revenue Products by Year: " + topRevenueProductsByYear);

        // Chuẩn bị dữ liệu trả về
        Map<String, Object> chartData = new HashMap<>();
        chartData.put("topProducts", topProducts); // Top sản phẩm
        chartData.put("salesOrders", salesOrders); // Doanh thu theo tháng
       
        chartData.put("revenueByMonth", revenueByMonth); // Doanh thu theo tháng
        chartData.put("topRevenueProductsByWeek", topRevenueProductsByWeek); // Top sản phẩm theo tuần
        chartData.put("topRevenueProductsByMonth", topRevenueProductsByMonth); // Top sản phẩm theo tháng
        chartData.put("topRevenueProductsByYear", topRevenueProductsByYear); // Top sản phẩm theo năm

        // Trả về JSON
        Gson gson = new Gson();
        response.getWriter().write(gson.toJson(chartData));
    }
}
