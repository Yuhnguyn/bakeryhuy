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

        // Khai báo các Map để chứa dữ liệu
        Map<String, Object> topProducts = new HashMap<>();
        Map<String, Object> salesOrders = new HashMap<>();
        Map<String, Double> revenueByMonth = new HashMap<>();
        Map<String, Double> topRevenueProductsByWeek = new HashMap<>();
        Map<String, Double> topRevenueProductsByMonth = new HashMap<>();
        Map<String, Double> topRevenueProductsByYear = new HashMap<>();

        // Lấy dữ liệu từ DAO
        try {
            topProducts = productDAO.getTop5Products(); // Sản phẩm bán chạy
            salesOrders = orderDAO.getSalesOrdersByMonth(); // Doanh thu theo tháng
            revenueByMonth = orderDetailsDAO.getRevenueByMonth(); // Doanh thu theo tháng
            topRevenueProductsByWeek = orderDetailsDAO.getTop5RevenueProductsByWeek(); // Doanh thu theo tuần
            topRevenueProductsByMonth = orderDetailsDAO.getTop5RevenueProductsByMonth(); // Doanh thu theo tháng
            topRevenueProductsByYear = orderDetailsDAO.getTop5RevenueProductsByYear(); // Doanh thu theo năm
        } catch (Exception e) {
            System.err.println("Error while fetching data: " + e.getMessage());
            // Set mặc định dữ liệu lỗi
            topProducts = new HashMap<>();
            salesOrders = new HashMap<>();
            revenueByMonth = new HashMap<>();
            topRevenueProductsByWeek = new HashMap<>();
            topRevenueProductsByMonth = new HashMap<>();
            topRevenueProductsByYear = new HashMap<>();
        }

        // Kiểm tra dữ liệu lấy được và log ra console
        System.out.println("Top Products: " + (topProducts.isEmpty() ? "No data" : topProducts));
        System.out.println("Sales Orders: " + (salesOrders.isEmpty() ? "No data" : salesOrders));
        System.out.println("Revenue by Month: " + (revenueByMonth.isEmpty() ? "No data" : revenueByMonth));
        System.out.println("Top Revenue Products by Week: " + (topRevenueProductsByWeek.isEmpty() ? "No data" : topRevenueProductsByWeek));
        System.out.println("Top Revenue Products by Month: " + (topRevenueProductsByMonth.isEmpty() ? "No data" : topRevenueProductsByMonth));
        System.out.println("Top Revenue Products by Year: " + (topRevenueProductsByYear.isEmpty() ? "No data" : topRevenueProductsByYear));

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

