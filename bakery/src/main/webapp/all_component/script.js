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
fetch('/bakery/chart-data')
  .then((response) => response.json())
  .then((data) => {
    console.log('Fetched chart data:', data); // Kiểm tra dữ liệu nhận được

    // Biểu đồ Top 5 sản phẩm bán nhiều nhất
    const topProducts = data.topProducts || {};
    const productNames = Object.keys(topProducts);
    const productQuantities = Object.values(topProducts);

    // Biểu đồ Top 5 sản phẩm bán nhiều nhất
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
          name: 'Quantity Sold',
          data: productQuantities,
          colorByPoint: true,
        },
      ],
    });

    // Biểu đồ Dòng doanh thu theo tháng
    const salesOrders = data.salesOrders || {};
    const months = Object.keys(salesOrders).map((month) => `Month ${month}`);
    const salesCounts = Object.values(salesOrders);

    // Biểu đồ Dòng doanh thu theo tháng
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

	// Biểu đồ Top 5 sản phẩm có doanh thu cao nhất
	const topRevenueProducts = data.topRevenueProducts || {};
	const revenueProductNames = Object.keys(topRevenueProducts);
	const productRevenues = Object.values(topRevenueProducts);

	// Biểu đồ Top 5 sản phẩm có doanh thu cao nhất
	Highcharts.chart('top-revenue-products-chart', {
	  chart: {
	    type: 'column',
	  },
	  xAxis: {
	    categories: revenueProductNames,
	    title: {
	      text: 'Products',
	    },
	  },
	  yAxis: {
	    min: 0,
	    title: {
	      text: 'Revenue (in USD)',
	    },
	  },
	  series: [
	    {
	      name: 'Revenue',
	      data: productRevenues,
	      colorByPoint: true,
	    },
	  ],
	});

	// Biểu đồ Dòng doanh thu theo tháng cho tất cả sản phẩm
	const revenueByMonth = data.revenueByMonth || {};

	// Kiểm tra xem revenueByMonth có dữ liệu không
	if (Object.keys(revenueByMonth).length === 0) {
	  console.log("No revenue data available.");
	  return; // Nếu không có dữ liệu doanh thu, không hiển thị biểu đồ
	}

	// Chuyển đổi tháng từ dữ liệu thành 'Month - Year' để hiển thị trên trục X
	let monthsForRevenue = Object.keys(revenueByMonth).map((month) => {
	  const [year, monthNumber] = month.split('-'); // Đảm bảo dữ liệu tháng có định dạng 'Year-Month'
	  return {
	    label: `Month ${monthNumber} - ${year}`, // Định dạng lại tháng theo 'Month Number - Year'
	    value: month // Lưu trữ giá trị 'Year-Month' gốc để sắp xếp
	  };
	});

	// Sắp xếp theo tháng và năm
	monthsForRevenue.sort((a, b) => {
	  const [yearA, monthA] = a.value.split('-');
	  const [yearB, monthB] = b.value.split('-');
	  // Sắp xếp theo năm trước, sau đó theo tháng
	  if (yearA === yearB) {
	    return parseInt(monthA) - parseInt(monthB);
	  }
	  return parseInt(yearA) - parseInt(yearB);
	});

	// Lấy dữ liệu doanh thu
	const revenues = monthsForRevenue.map(month => revenueByMonth[month.value]);

	// Cập nhật lại danh sách tháng đã sắp xếp
	const sortedMonths = monthsForRevenue.map(month => month.label);

	// Biểu đồ Dòng doanh thu theo tháng cho tất cả sản phẩm
	Highcharts.chart('monthly-revenue-chart', {
	  chart: {
	    type: 'line',
	  },
	  title: {
	    text: 'Monthly Revenue for All Products',
	  },
	  xAxis: {
	    categories: sortedMonths, // Tháng đã sắp xếp
	    title: {
	      text: 'Month',
	    },
	    labels: {
	      rotation: -45, // Xoay nhãn để dễ đọc hơn
	      style: {
	        fontSize: '12px',
	      },
	    },
	  },
	  yAxis: {
	    title: {
	      text: 'Revenue (in USD)',
	    },
	  },
	  series: [
	    {
	      name: 'Revenue',
	      data: revenues,
	      color: '#246dec',
	    },
	  ],
	  tooltip: {
	    valuePrefix: 'VND', // Thêm dấu $ trước giá trị doanh thu
	    valueDecimals: 2, // Hiển thị 2 chữ số thập phân
	  },
	});

	// Biểu đồ Dòng doanh thu theo tháng cho tất cả sản phẩm
	Highcharts.chart('monthly-revenue-chart', {
	  chart: {
	    type: 'line',
	  },
	  title: {
	    text: 'Monthly Revenue for All Products',
	  },
	  xAxis: {
	    categories: sortedMonths, // Sử dụng danh sách các tháng đã sắp xếp
	    title: {
	      text: 'Month',
	    },
	    labels: {
	      rotation: -45, // Xoay nhãn để dễ đọc hơn
	      style: {
	        fontSize: '12px',
	      },
	    },
	  },
	  yAxis: {
	    title: {
	      text: 'Revenue (in USD)',
	    },
	  },
	  series: [
	    {
	      name: 'Revenue',
	      data: revenues, // Dữ liệu doanh thu đã sắp xếp theo thứ tự tháng
	      color: '#246dec',
	    },
	  ],
	  tooltip: {
	    valuePrefix: 'VND', // Thêm dấu $ trước giá trị doanh thu
	    valueDecimals: 2, // Hiển thị 2 chữ số thập phân
	  },
	});

  })
  .catch((error) => console.error('Error fetching data:', error)); // Lỗi khi không thể tải dữ liệu
