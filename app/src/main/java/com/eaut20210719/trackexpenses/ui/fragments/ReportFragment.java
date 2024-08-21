package com.eaut20210719.trackexpenses.ui.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.github.mikephil.charting.charts.PieChart;

import com.eaut20210719.trackexpenses.databinding.StatisticalFragmentBinding;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class ReportFragment extends Fragment {

    private StatisticalFragmentBinding binding;
    private View.OnClickListener addClickListener;

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

        // Initialize and setup the PieChart
        setupPieChart();
        setupPieChartThisday();
    }

    private void setupPieChart() {
        PieChart pieChart = binding.chart; // Assuming your PieChart is bound to this ID

        // Configure PieChart appearance
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleColor(Color.WHITE);
        pieChart.setTransparentCircleAlpha(110);
        pieChart.setHoleRadius(58f);
        pieChart.setTransparentCircleRadius(61f);
        pieChart.setDrawCenterText(true);
        pieChart.setRotationAngle(0);
        pieChart.setRotationEnabled(true);
        pieChart.setHighlightPerTapEnabled(true);

        // Add data to PieChart
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(40f, "Số tiền đã chi")); // Example percentage for Amount Spent
        entries.add(new PieEntry(60f, "Số tiền còn lại")); // Example percentage for Amount Remaining

        PieDataSet dataSet = new PieDataSet(entries, ""); // Pass an empty string for no label

        // Set specific colors for the two segments
        int[] colors = {Color.parseColor("#FA3D6A"), Color.parseColor("#FE862F")};
        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);

        pieChart.setData(data);
        pieChart.invalidate();
    }

    private void setupPieChartThisday() {
        PieChart pieChart = binding.chartthismonth; // Assuming your PieChart is bound to this ID

        // Configure PieChart appearance
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleColor(Color.WHITE);
        pieChart.setTransparentCircleAlpha(110);
        pieChart.setHoleRadius(58f);
        pieChart.setTransparentCircleRadius(61f);
        pieChart.setDrawCenterText(true);
        pieChart.setRotationAngle(0);
        pieChart.setRotationEnabled(true);
        pieChart.setHighlightPerTapEnabled(true);

        // Add data to PieChart
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(40f, "Số tiền đã chi")); // Example percentage for Amount Spent
        entries.add(new PieEntry(60f, "Số tiền còn lại")); // Example percentage for Amount Remaining

        PieDataSet dataSet = new PieDataSet(entries, ""); // Pass an empty string for no label

        // Set specific colors for the two segments
        int[] colors = {Color.parseColor("#FA3D6A"), Color.parseColor("#FE862F")};
        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);

        pieChart.setData(data);
        pieChart.invalidate();
    }



    // Phương thức để MainActivity thiết lập sự kiện click cho tvAdd
    public void setAddClickListener(View.OnClickListener listener) {
        this.addClickListener = listener;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Tránh memory leak
    }
}
