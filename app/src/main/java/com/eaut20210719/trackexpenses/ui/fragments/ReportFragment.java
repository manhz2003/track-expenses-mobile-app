package com.eaut20210719.trackexpenses.ui.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.eaut20210719.trackexpenses.database.entities.Transaction;
import com.eaut20210719.trackexpenses.viewmodels.DailyLimitViewModel;
import com.eaut20210719.trackexpenses.viewmodels.MonthlyLimitViewModel;
import com.eaut20210719.trackexpenses.viewmodels.TransactionViewModel;
import com.eaut20210719.trackexpenses.viewmodels.TypeViewModel;
import com.github.mikephil.charting.charts.PieChart;

import com.eaut20210719.trackexpenses.databinding.StatisticalFragmentBinding;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.text.DecimalFormat;

public class ReportFragment extends Fragment {

    private StatisticalFragmentBinding binding;
    private View.OnClickListener addClickListener;
    private TransactionViewModel transactionViewModel;
    private MonthlyLimitViewModel monthlyLimitViewModel;
    private DailyLimitViewModel dailyLimitViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = StatisticalFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Thiết lập sự kiện cho tvAdd
        binding.tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addClickListener != null) {
                    addClickListener.onClick(v);
                }
            }
        });

        transactionViewModel = new ViewModelProvider(this).get(TransactionViewModel.class);
        monthlyLimitViewModel = new ViewModelProvider(this).get(MonthlyLimitViewModel.class);
        dailyLimitViewModel = new ViewModelProvider(this).get(DailyLimitViewModel.class);

        // Lấy danh sách giao dịch cho ngày/tháng/năm hiện tại từ ViewModel
        String todayDate = getTodayDate();
        transactionViewModel.getTransactionsByDate(todayDate).observe(getViewLifecycleOwner(), transactions -> {
            if (transactions != null && !transactions.isEmpty()) {
                // Cập nhật báo cáo hôm nay khi dữ liệu thay đổi
                updateTodayReport(transactions);
                dailyLimitViewModel.getLastDailyLimitMoney().observe(getViewLifecycleOwner(), moneyDay -> {
                    // Initialize and setup the PieChart
                    setupPieChart(transactions, moneyDay);
                });
            }
        });

        // Chuyển đổi định dạng từ ngày/tháng/năm sang tháng/năm
        String monthYear = convertToMonthYear(todayDate);
        // Lấy danh sách giao dịch cho tháng/năm hiện tại từ ViewModel
        transactionViewModel.getTransactionsByDate(todayDate).observe(getViewLifecycleOwner(), transactions -> {
            if (transactions != null && !transactions.isEmpty()) {
                // Cập nhật báo cáo hôm nay khi dữ liệu thay đổi
                updateThisMonthReport(transactions);
                monthlyLimitViewModel.getLastMonthlyLimitMoney().observe(getViewLifecycleOwner(), moneyMonth -> {
                    setupPieChartThisday(transactions, moneyMonth);
                });

            }
        });

    }

    // Thống kê ngày
    private void setupPieChart(List<Transaction> transactions, Double moneyDay) {

        Pair<Double, Double> totalAmountForToday = calculateTotalAmountForToday(transactions);

        double amountSpentToday = (double) totalAmountForToday.first;
        double moneyToDayLimit = (double)moneyDay;

        float monthlySpendingRatio = (float)((amountSpentToday / moneyToDayLimit) * 100);
        float monthlyBalanceRatio = (float)(100 - monthlySpendingRatio);


        PieChart pieChart = binding.chart; // Assuming your PieChart is bound to this ID

        // Cấu hình giao diện của PieChart
        pieChart.setUsePercentValues(true); // Sử dụng giá trị phần trăm
        pieChart.getDescription().setEnabled(false); // Tắt mô tả
        pieChart.setDrawHoleEnabled(true); // Vẽ lỗ giữa
        pieChart.setHoleColor(Color.WHITE); // Màu của lỗ giữa
        pieChart.setTransparentCircleColor(Color.WHITE); // Màu vòng tròn trong suốt
        pieChart.setTransparentCircleAlpha(110); // Độ mờ của vòng tròn trong suốt
        pieChart.setHoleRadius(58f); // Bán kính của lỗ giữa
        pieChart.setTransparentCircleRadius(61f); // Bán kính của vòng tròn trong suốt
        pieChart.setDrawCenterText(true); // Vẽ văn bản ở giữa
        pieChart.setRotationAngle(0); // Góc quay ban đầu
        pieChart.setRotationEnabled(true); // Cho phép quay
        pieChart.setHighlightPerTapEnabled(true); // Bật nổi bật khi chạm
        pieChart.setCenterText("Chi tiêu ngày");
        pieChart.setCenterTextSize(16f);
        pieChart.setCenterTextColor(Color.BLACK);


        // Add data to PieChart
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(monthlySpendingRatio, "Số tiền đã chi")); // Example percentage for Amount Spent
        entries.add(new PieEntry(monthlyBalanceRatio, "Số tiền còn lại")); // Example percentage for Amount Remaining

        PieDataSet dataSet = new PieDataSet(entries, ""); // Pass an empty string for no label

        // Set specific colors for the two segments
        int[] colors = {Color.parseColor("#FA3D6A"), Color.parseColor("#FE862F")};
        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setValueTextSize(13f); // Kích thước văn bản giá trị
        data.setValueTextColor(Color.WHITE); // Màu văn bản giá trị

        // Bộ định dạng tùy chỉnh để chỉ hiển thị phần trăm
        data.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return String.format("%.0f%%", value); // Chỉ hiển thị phần trăm
            }
        });

        pieChart.setData(data);

        // Cấu hình phần chú thích để đảm bảo nó hiển thị các nhãn
        Legend legend = pieChart.getLegend();
        legend.setEnabled(true); // Đảm bảo phần chú thích được bật
        legend.setForm(Legend.LegendForm.CIRCLE); // Hình dạng của phần chú thích
        legend.setTextSize(13f); // Kích thước văn bản của phần chú thích
        legend.setTextColor(Color.BLACK); // Màu văn bản của phần chú thích
        // Tăng khoảng cách giữa các mục trong phần chú thích
        legend.setXEntrySpace(30f); // Khoảng cách giữa các mục theo chiều ngang
        legend.setYEntrySpace(5f);  // Khoảng cách giữa các mục theo chiều dọc
        // Cấu hình PieChart để ẩn nhãn trên biểu đồ
        pieChart.setEntryLabelColor(Color.TRANSPARENT); // Ẩn các nhãn bên trong biểu đồ

        pieChart.invalidate(); // Làm mới PieChart
    }

    // Thống kê tháng
    private void setupPieChartThisday(List<Transaction> transactions, Double moneyMonth) {

        Pair<Double, Double> totalAmountForToday = calculateTotalAmountForToday(transactions);

        double amountSpentToday = (double) totalAmountForToday.first;
        double moneyToDayLimit = (double)moneyMonth;

        float monthlySpendingRatio = (float)((amountSpentToday / moneyToDayLimit) * 100);
        float monthlyBalanceRatio = (float)(100 - monthlySpendingRatio);

        PieChart pieChart = binding.chartthismonth; // Assuming your PieChart is bound to this ID

        // Cấu hình giao diện của PieChart
        pieChart.setUsePercentValues(true); // Sử dụng giá trị phần trăm
        pieChart.getDescription().setEnabled(false); // Tắt mô tả
        pieChart.setDrawHoleEnabled(true); // Vẽ lỗ giữa
        pieChart.setHoleColor(Color.WHITE); // Màu của lỗ giữa
        pieChart.setTransparentCircleColor(Color.WHITE); // Màu vòng tròn trong suốt
        pieChart.setTransparentCircleAlpha(110); // Độ mờ của vòng tròn trong suốt
        pieChart.setHoleRadius(58f); // Bán kính của lỗ giữa
        pieChart.setTransparentCircleRadius(61f); // Bán kính của vòng tròn trong suốt
        pieChart.setDrawCenterText(true); // Vẽ văn bản ở giữa
        pieChart.setRotationAngle(0); // Góc quay ban đầu
        pieChart.setRotationEnabled(true); // Cho phép quay
        pieChart.setHighlightPerTapEnabled(true); // Bật nổi bật khi chạm
        pieChart.setCenterText("Chi tiêu tháng");
        pieChart.setCenterTextSize(16f);
        pieChart.setCenterTextColor(Color.BLACK);

        // Add data to PieChart
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(monthlySpendingRatio, "Số tiền đã chi")); // Example percentage for Amount Spent
        entries.add(new PieEntry(monthlyBalanceRatio, "Số tiền còn lại")); // Example percentage for Amount Remaining

        PieDataSet dataSet = new PieDataSet(entries, ""); // Truyền chuỗi rỗng nếu không có nhãn
        // Đặt màu sắc cụ thể cho hai phần
        int[] colors = {Color.parseColor("#FA3D6A"), Color.parseColor("#FE862F")};
        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setValueTextSize(13f); // Kích thước văn bản giá trị
        data.setValueTextColor(Color.WHITE); // Màu văn bản giá trị

        // Bộ định dạng tùy chỉnh để chỉ hiển thị phần trăm
        data.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return String.format("%.0f%%", value); // Chỉ hiển thị phần trăm
            }
        });

        pieChart.setData(data);

        // Cấu hình phần chú thích để đảm bảo nó hiển thị các nhãn
        Legend legend = pieChart.getLegend();
        legend.setEnabled(true); // Đảm bảo phần chú thích được bật
        legend.setForm(Legend.LegendForm.CIRCLE); // Hình dạng của phần chú thích
        legend.setTextSize(13f); // Kích thước văn bản của phần chú thích
        legend.setTextColor(Color.BLACK); // Màu văn bản của phần chú thích
        // Tăng khoảng cách giữa các mục trong phần chú thích
        legend.setXEntrySpace(30f); // Khoảng cách giữa các mục theo chiều ngang
        legend.setYEntrySpace(5f);  // Khoảng cách giữa các mục theo chiều dọc
        // Cấu hình PieChart để ẩn nhãn trên biểu đồ
        pieChart.setEntryLabelColor(Color.TRANSPARENT); // Ẩn các nhãn bên trong biểu đồ

        pieChart.invalidate(); // Làm mới PieChart
    }

    // Phương thức để MainActivity thiết lập sự kiện click cho tvAdd
    public void setAddClickListener(View.OnClickListener listener) {
        this.addClickListener = listener;
    }


    //Trung
    //Hàm cập nhật giao diện
    private void updateTodayReport(List<Transaction> transactions) {
        // Lấy ngày hiện tại
        String todayDate = getTodayDate();

        Pair<Double, Double> totalAmountForToday = calculateTotalAmountForToday(transactions);

        // Cập nhật nội dung cho TextView
        double amountSpentToday = (double) totalAmountForToday.first;
        double amountCollectedToday = (double) totalAmountForToday.second;

        // Định dạng số với dấu phân cách hàng nghìn
        DecimalFormat decimalFormat = new DecimalFormat("#,###");

        // Chuyển đổi double thành dạng decimal với dấu phân cách hàng nghìn
        String formattedAmountSpent = decimalFormat.format(amountSpentToday);
        String formattedAmountCollected = decimalFormat.format(amountCollectedToday);

        // Cập nhật nội dung cho TextView ngày
        binding.todayreport.setText(String.format("Báo cáo hôm nay (%s):", todayDate));
        binding.amountspent.setText(String.format(formattedAmountSpent + "đ"));
        binding.amountcollected.setText(String.format(formattedAmountCollected + "đ"));

    }

    private void updateThisMonthReport(List<Transaction> transactions) {
        // Lấy ngày hiện tại
        String todayDate = getTodayDate();
        // Chuyển đổi định dạng từ ngày/tháng/năm sang tháng/năm
        String monthYear = convertToMonthYear(todayDate);

        Pair<Double, Double> totalAmountForToday = calculateTotalAmountForToday(transactions);

        // Cập nhật nội dung cho TextView
        double amountSpentToday = (double) totalAmountForToday.first;
        double amountCollectedToday = (double) totalAmountForToday.second;

        // Định dạng số với dấu phân cách hàng nghìn
        DecimalFormat decimalFormat = new DecimalFormat("#,###");

        // Chuyển đổi double thành dạng decimal với dấu phân cách hàng nghìn
        String formattedAmountSpent = decimalFormat.format(amountSpentToday);
        String formattedAmountCollected = decimalFormat.format(amountCollectedToday);

        // Cập nhật nội dung cho textView tháng
        binding.thismonthreport.setText(String.format("Báo cáo hôm nay (%s):", monthYear));
        binding.totalexpensesthismonth1.setText(String.format(formattedAmountSpent + "đ"));
        binding.totalrevenuethismonth1.setText(String.format(formattedAmountCollected + "đ"));
    }
    // Hàm lấy ra thu chi của ngày/tháng/năm và tháng năm
    private Pair<Double, Double> calculateTotalAmountForToday(List<Transaction> transactions) {
        double amountSpentToday = 0.0;
        double amountCollectedToday = 0.0;

        for (Transaction transaction : transactions) {
            // Sử dụng typeId để phân loại giao dịch
            if (transaction.getTypeId() == 1 || transaction.getTypeId() == 2) {
                amountSpentToday += transaction.getAmount();
            } else if (transaction.getTypeId() == 3) {
                amountCollectedToday += transaction.getAmount();
            }
        }

        return new Pair<>(amountSpentToday, amountCollectedToday);
    }


    // Hàm lấy ra ngày tháng trên máy android
    private String getTodayDate() {
        // Lấy ngày hiện tại theo định dạng "dd/MM/yyyy HH:mm"
        return new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
    }

    // Hàm chuyển đổi từ ngày/tháng/năm thành tháng/năm
    public static String convertToMonthYear(String date) {
        // Giả sử định dạng input luôn là dd/MM/yyyy
        String[] dateParts = date.split("/");
        return dateParts[1] + "/" + dateParts[2];
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Tránh memory leak
    }
}
