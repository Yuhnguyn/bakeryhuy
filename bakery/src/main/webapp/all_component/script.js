
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
	  title: {
	  	    text: 'Top 5 sản phẩm bán nhiều nhất',
	  	  },
      xAxis: {
        categories: productNames,
        title: {
          text: 'Sản phẩm',
        },
      },
      yAxis: {
        min: 0,
        title: {
          text: 'Số lượng',
        },
      },
      series: [
        {
          name: 'Số lượng',
          data: productQuantities,
          colorByPoint: true,
        },
      ],
    });

    // Biểu đồ Dòng doanh thu theo tháng
    const salesOrders = data.salesOrders || {};
    const months = Object.keys(salesOrders).map((month) => `Tháng ${month}`);
    const salesCounts = Object.values(salesOrders);

    // Biểu đồ Dòng doanh thu theo tháng
    Highcharts.chart('area-chart', {
      chart: {
        type: 'line',
      },
	  title: {
	  	    text: 'Số lượng đơn hàng theo tháng',
	  	  },
      xAxis: {
        categories: months,
        title: {
          text: 'Tháng',
        },
      },
      yAxis: {
        title: {
          text: 'Đơn hàng',
        },
      },
      series: [
        {
          name: 'Đơn hàng',
          data: salesCounts,
          color: '#246dec',
        },
      ],
    });
	
	
///top 5 doanh thu san pham 
document.getElementById('time-period').addEventListener('change', function () {
  const selectedPeriod = this.value;  // Lấy giá trị của khoảng thời gian (week, month, year)

  // Gửi yêu cầu tới API với tham số 'period'
  fetch(`/bakery/chart-data?period=${selectedPeriod}`)
    .then((response) => {
      if (!response.ok) {
        throw new Error('Error fetching chart data. Status: ' + response.status);
      }
      return response.json();  // Chuyển đổi dữ liệu trả về thành JSON
    })
    .then((data) => {
      // Kiểm tra xem dữ liệu có đúng hay không
      console.log('Dữ liệu nhận được:', data);

      let topRevenueProducts = {};

      // Kiểm tra khoảng thời gian đã chọn và lấy dữ liệu tương ứng
      if (selectedPeriod === 'week') {
        topRevenueProducts = data.topRevenueProductsByWeek || {};
      } else if (selectedPeriod === 'month') {
        topRevenueProducts = data.topRevenueProductsByMonth || {};
      } else if (selectedPeriod === 'year') {
        topRevenueProducts = data.topRevenueProductsByYear || {};
      }

      const revenueProductNames = Object.keys(topRevenueProducts);  // Danh sách tên sản phẩm
      const productRevenues = Object.values(topRevenueProducts);  // Danh sách doanh thu sản phẩm

      // Log lại kết quả kiểm tra dữ liệu
      console.log('Danh sách sản phẩm:', revenueProductNames);
      console.log('Doanh thu sản phẩm:', productRevenues);

      // Kiểm tra nếu không có sản phẩm hoặc doanh thu, hiển thị thông báo lỗi
      if (revenueProductNames.length === 0) {
        alert('Khong co du lieu cho khoang thoi gian nay');
        return;
      }

      // Cập nhật biểu đồ nếu có dữ liệu
      Highcharts.chart('top-revenue-products-chart', {
        chart: {
          type: 'column',  // Chọn loại biểu đồ cột
        },
        title: {
          text: `Top 5 sản phẩm có doanh thu cao nhất  (${selectedPeriod === 'week' ? 'Tuần' : selectedPeriod === 'month' ? 'Tháng' : 'Năm'})`, // Cập nhật tiêu đề dựa trên khoảng thời gian
        },
        xAxis: {
          categories: revenueProductNames,  // Đặt danh sách sản phẩm làm trục X
          title: {
            text: 'Sản phẩm',  // Tiêu đề trục X
          },
        },
        yAxis: {
          min: 0,
          title: {
            text: 'Doanh thu (VND)',  // Tiêu đề trục Y
          },
        },
        series: [
          {
            name: 'Doanh thu',
            data: productRevenues,  // Đặt dữ liệu doanh thu cho biểu đồ
            colorByPoint: true,  // Mỗi cột sẽ có màu sắc riêng
          },
        ],
      });
    })
    .catch((error) => {
      // Nếu có lỗi khi gọi API, log lỗi và hiển thị thông báo
      console.error('Error fetching chart data:', error);
      alert('Da xay ra loi khi tai du lieu: ' + error.message);  // Hiển thị thông báo lỗi chi tiết hơn
    });
});

// Khi trang được tải, mặc định chọn 'month' và vẽ biểu đồ theo tháng
window.addEventListener('load', () => {
  document.getElementById('time-period').value = 'month';  // Chọn mặc định là tháng
  document.getElementById('time-period').dispatchEvent(new Event('change'));  // Gửi sự kiện thay đổi để cập nhật biểu đồ
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
	    label: `Tháng ${monthNumber} - ${year}`, // Định dạng lại tháng theo 'Month Number - Year'
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
	    text: 'Doanh thu cửa hàng theo tháng  ',
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
	    text: 'Doanh thu cửa hàng theo tháng',
	  },
	  xAxis: {
	    categories: sortedMonths, // Sử dụng danh sách các tháng đã sắp xếp
	    title: {
	      text: 'Tháng',
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
	      text: 'VND',
	    },
	  },
	  series: [
	    {
	      name: 'Doanh thu',
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
