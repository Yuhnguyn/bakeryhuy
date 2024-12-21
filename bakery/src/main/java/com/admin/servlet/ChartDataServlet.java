package com.admin.servlet;

import com.DAO.ProductDAOImpl;
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

        // Lấy dữ liệu từ DAO
        Map<String, Object> topProducts = productDAO.getTop5Products();
        Map<String, Object> salesOrders = orderDAO.getSalesOrdersByMonth();

        // Chuẩn bị dữ liệu trả về
        Map<String, Object> chartData = new HashMap<>();
        chartData.put("topProducts", topProducts);
        chartData.put("salesOrders", salesOrders);

        // Trả về JSON
        Gson gson = new Gson();
        response.getWriter().write(gson.toJson(chartData));
    }
}

