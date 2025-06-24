<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="vi">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1.0">
    <title>Admin Dashboard</title>

    <!-- Montserrat Font -->
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@100;200;300;400;500;600;700;800;900&display=swap" rel="stylesheet">

    <!-- Material Icons -->
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons+Outlined" rel="stylesheet">

    <!-- Custom CSS -->
    <link rel="stylesheet" href="../all_component/dashboard.css">
  </head>
  <body>
    <div class="grid-container">

      <!-- Header -->
      <header class="header">
        <div class="menu-icon" onclick="openSidebar()">
          <span class="material-icons-outlined">menu</span>
        </div>
        <div class="header-left">
          <span class="material-icons-outlined">search</span>
        </div>
        <div class="header-right">
          <span class="material-icons-outlined">notifications</span>
          <span class="material-icons-outlined">email</span>
          <span class="material-icons-outlined">account_circle</span>
        </div>
      </header>
      <!-- End Header -->

      <!-- Sidebar -->
      <aside id="sidebar">
        <div class="sidebar-title">
          <div class="sidebar-brand">
            <span class="material-icons-outlined">inventory</span> Bakery Dashboard
          </div>
          <span class="material-icons-outlined" onclick="closeSidebar()">close</span>
        </div>

        <ul class="sidebar-list">
          <li class="sidebar-list-item">
            <a href="#" target="_blank">
              <span class="material-icons-outlined">dashboard</span> Biểu đồ
            </a>
          </li>
          <li class="sidebar-list-item">
            <a href="all_product.jsp" target="_blank">
              <span class="material-icons-outlined">inventory_2</span> Sản phẩm
            </a>
          </li>


          <li class="sidebar-list-item">
            <a href="orders.jsp" target="_blank">
              <span class="material-icons-outlined">shopping_cart</span> Đơn hàng
            </a>
          </li>
          <li class="sidebar-list-item">
            <a href="user_list.jsp" target="_blank">
              <span class="material-icons-outlined">person</span> Khách hàng
            </a>
          </li>
          <li class="sidebar-list-item">
            <a href="staff_list.jsp" target="_blank">
              <span class="material-icons-outlined">group</span> Nhân viên
            </a>
          </li>
        </ul>
        
      </aside>
      <!-- End Sidebar -->

      <!-- Main -->
      <main class="main-container">
        <div class="main-title">
          <p class="font-weight-bold">Báo cáo và thống kê</p>
        </div>

        <div class="main-cards">

          <div class="card">
            <div class="card-inner">
              <p class="text-primary">Sản phẩm</p>
              <span class="material-icons-outlined text-blue">inventory_2</span>
            </div>
            <span class="text-primary font-weight-bold">249</span>
          </div>


          <div class="card">
            <div class="card-inner">
              <p class="text-primary">Đơn hàng</p>
              <span class="material-icons-outlined text-green">shopping_cart</span>
            </div>
            <span class="text-primary font-weight-bold">79</span>
          </div>

          <div class="card">
            <div class="card-inner">
              <p class="text-primary">Khách hàng</p>
              <span class="material-icons-outlined text-blue">person</span>
            </div>
            <span class="text-primary font-weight-bold">56</span>
          </div>
		
		<div class="card">
            <div class="card-inner">
              <p class="text-primary">Nhân viên</p>
              <span class="material-icons-outlined text-red">group</span>
            </div>
            <span class="text-primary font-weight-bold">56</span>
          </div>
        </div>

        <div class="charts">
  <!-- 3 Biểu đồ đầu tiên -->
  <div class="chart-row">
    <div id="bar-chart" class="chart-card"></div>
    <div id="area-chart" class="chart-card"></div>
    <div id="monthly-revenue-chart" class="chart-card"></div>
  </div>

  <!-- Dropdown chọn khoảng thời gian -->
  <div class="chart-header">
    <label for="time-period" class="select-label">Chọn khoảng thời gian:</label>
    <select id="time-period" class="select-time">
      <option value="week">Tuần</option>
      <option value="month" selected>Tháng</option>
      <option value="year">Năm</option>
    </select>
  </div>

  <!-- Biểu đồ Top Revenue Products -->
  <div id="top-revenue-products-chart" class="chart-card"></div>
</div>

      </main>
      <!-- End Main -->

    </div>

    <!-- Scripts -->
   <script src="https://code.highcharts.com/highcharts.js"></script>
<script src="https://code.highcharts.com/modules/series-label.js"></script>
<script src="https://code.highcharts.com/modules/exporting.js"></script>
<script src="https://code.highcharts.com/modules/accessibility.js"></script>
   
    <!-- Custom JS -->
    <script src="../all_component/script.js"></script>
  </body>
</html>