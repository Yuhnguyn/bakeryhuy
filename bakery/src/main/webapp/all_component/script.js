// SIDEBAR TOGGLE
let sidebarOpen = false;
const sidebar = document.getElementById('sidebar');

function openSidebar() {
  if (!sidebarOpen) {
    sidebar.classList.add('sidebar-responsive');
    sidebarOpen = true;
  }
}

function closeSidebar() {
  if (sidebarOpen) {
    sidebar.classList.remove('sidebar-responsive');
    sidebarOpen = false;
  }
}

// Lấy dữ liệu từ API `/chart-data`
// Fetch data from the backend API
fetch('/bakery/chart-data')
  .then((response) => response.json())
  .then((data) => {
    // Process Top 5 Products Data
    const topProducts = data.topProducts || {};
    const productNames = Object.keys(topProducts);
    const productQuantities = Object.values(topProducts);

    // Render Bar Chart for Top 5 Products
    Highcharts.chart('bar-chart', {
      chart: {
        type: 'column',
      },
     
      xAxis: {
        categories: productNames,
        title: {
          text: 'Products',
        },
      },
      yAxis: {
        min: 0,
        title: {
          text: 'Quantity Sold',
        },
      },
      series: [
        {
          name: 'Quantity',
          data: productQuantities,
          colorByPoint: true,
        },
      ],
    });

    // Process Sales Orders Data
    const salesOrders = data.salesOrders || {};
    const months = Object.keys(salesOrders).map((month) => `Month ${month}`);
    const salesCounts = Object.values(salesOrders);

    // Render Area Chart for Sales Orders
    Highcharts.chart('area-chart', {
      chart: {
        type: 'line',
      },
     
      xAxis: {
        categories: months,
        title: {
          text: 'Month',
        },
      },
      yAxis: {
        title: {
          text: 'Number of Orders',
        },
      },
      series: [
        {
          name: 'Sales Orders',
          data: salesCounts,
          color: '#246dec',
        },
      ],
    });
  })
  .catch((error) => console.error('Error fetching data:', error));


