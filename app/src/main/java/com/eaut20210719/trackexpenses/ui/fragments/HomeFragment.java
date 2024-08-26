package com.eaut20210719.trackexpenses.ui.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.eaut20210719.trackexpenses.R;
import com.eaut20210719.trackexpenses.database.entities.Transaction;
import com.eaut20210719.trackexpenses.databinding.HomeFragmentBinding;
import com.eaut20210719.trackexpenses.viewmodels.DailyLimitViewModel;
import com.eaut20210719.trackexpenses.viewmodels.TransactionViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HomeFragment extends Fragment {

    private static final String PREFS_NAME = "HomeFragmentPrefs";
    private static final String KEY_EXPENSE_PROGRESS = "ExpenseProgress";

    private HomeFragmentBinding binding;
    private TransactionViewModel transactionViewModel;
    private DailyLimitViewModel dailyLimitViewModel;
    private boolean isBalanceVisible = false;
    private EditText etInputMoney;
    private Button btnSave;
    private View.OnClickListener addClickListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = HomeFragmentBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        // Initialize ViewModel
        transactionViewModel = new ViewModelProvider(this).get(TransactionViewModel.class);
        dailyLimitViewModel = new ViewModelProvider(this).get(DailyLimitViewModel.class);

        // Initialize UI components
        etInputMoney = binding.etInputMoney;
        btnSave = binding.btnSave;

        // Toggle balance visibility
        ImageView imEye = binding.imEye;
        TextView tvBalance = binding.tvBalance;

        imEye.setOnClickListener(v -> {
            if (isBalanceVisible) {
                tvBalance.setText("*** *** *** VND");
                imEye.setImageResource(R.drawable.eye);
            } else {
                transactionViewModel.getTotalBalance().observe(getViewLifecycleOwner(), totalBalance -> {
                    if (totalBalance != null) {
                        tvBalance.setText(String.format("%,.0f VND", totalBalance));
                    }
                });
                imEye.setImageResource(R.drawable.icon_eye_24);
            }
            isBalanceVisible = !isBalanceVisible;
        });

        // Initialize SeekBar and TextView
        AppCompatSeekBar sbExpenses = binding.sbExpenses;
        TextView tvExpenseValue = binding.tvMoney;

        // Đọc trạng thái của thanh cuộn từ SharedPreferences
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        int savedProgress = sharedPreferences.getInt(KEY_EXPENSE_PROGRESS, 0);

        // Cập nhật số tiền chi tiêu từ bản ghi cuối cùng
        dailyLimitViewModel.getLastDailyLimitMoney().observe(getViewLifecycleOwner(), lastMoneyDay -> {
            if (lastMoneyDay != null) {
                int maxAmount = (int) lastMoneyDay.doubleValue();
                sbExpenses.setMax(maxAmount);

                // Đặt giá trị của SeekBar từ SharedPreferences chỉ khi chưa được thiết lập
                if (sbExpenses.getProgress() == 0) {
                    sbExpenses.setProgress(savedProgress);
                }

                tvExpenseValue.setText(String.format("%,d VND", sbExpenses.getProgress()));
            }
        });

        sbExpenses.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvExpenseValue.setText(String.format("%,d VND", progress));
                dailyLimitViewModel.updateMoneyDaySetting(progress);

                // Lưu trạng thái của thanh cuộn vào SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt(KEY_EXPENSE_PROGRESS, progress);
                editor.apply();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        // Handle Save button click
        btnSave.setOnClickListener(v -> {
            String inputMoneyStr = etInputMoney.getText().toString();
            if (!inputMoneyStr.isEmpty()) {
                try {
                    double inputMoney = Double.parseDouble(inputMoneyStr);
                    dailyLimitViewModel.insertOrUpdateDailyLimit(inputMoney);
                    etInputMoney.setText("");
                    Toast.makeText(getContext(), "Thiết lập thành công", Toast.LENGTH_SHORT).show();
                } catch (NumberFormatException e) {
                    Toast.makeText(getContext(), "Vui lòng nhập số tiền hợp lệ", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), "Vui lòng nhập số tiền", Toast.LENGTH_SHORT).show();
            }
        });

        // Tính toán tổng số tiền cho tháng hiện tại và cập nhật UI
        calculateAndDisplayMonthlyTotal();

        return view;
    }

    //    Lấy tháng hiện tại
    private int getCurrentMonth() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.MONTH) + 1;
    }

    // Lấy năm hiện tại
    private int getCurrentYear() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR);
    }

    // Lấy ngày hiện tại
    private int getCurrentDay() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    //    Tính toán và hiển thị tổng số tiền hàng tháng
    private void calculateAndDisplayMonthlyTotal() {
        transactionViewModel.getAllTransactions().observe(getViewLifecycleOwner(), transactions -> {
            if (transactions != null) {
                double totalIncome = sumAmountForCurrentMonth(transactions); // Tổng thu nhập
                double totalExpense = sumAmountForCurrentMonthChiTieu(transactions); // Tổng chi tiêu
                double totalExpenseToday = sumAmountForToday(transactions);

                Log.d("totalExpenseToday", "totalExpenseToday: "+totalExpenseToday);

                TextView tvMonthlyTotalIncome = binding.tv0d; // TextView cho thu nhập
                TextView tvMonthlyTotalExpense = binding.tv0d1; // TextView cho chi tiêu
                TextView tvTodayTotalExpense = binding.tien; // TextView cho chi tiêu hôm nay

                    if (tvMonthlyTotalIncome != null) {
                            tvMonthlyTotalIncome.setText(String.format("%,.0fđ", totalIncome));
                        }

                    if (tvMonthlyTotalExpense != null) {
                            tvMonthlyTotalExpense.setText(String.format("%,.0fđ", totalExpense));
                        }

                    if (tvTodayTotalExpense != null) {
                            tvTodayTotalExpense.setText(String.format("%,.0fđ", totalExpenseToday));}

                        }
                });
            }

    private String cleanDateString(String dateStr) {
        // Loại bỏ các phần không cần thiết (ví dụ: "Chiều", "Sáng")
        return dateStr.replace("Chiều", "").replace("Sáng", "").trim();
    }

    // Cập nhật phương thức `sumAmountForToday` để sử dụng định dạng 24 giờ
    private double sumAmountForToday(List<Transaction> transactions) {
        int currentDay = getCurrentDay();
        int currentMonth = getCurrentMonth();
        int currentYear = getCurrentYear();
        double totalAmount = 0.0;

        // Định dạng ngày giờ cho các chuỗi có định dạng kiểu 24 giờ
        SimpleDateFormat format24 = new SimpleDateFormat("HH:mm dd/MM/yyyy", Locale.getDefault());

        for (Transaction transaction : transactions) {
            String dateStr = cleanDateString(transaction.getDate());
            Date date = null;

            try {
                // Phân tích ngày giờ theo định dạng 24 giờ
                date = format24.parse(dateStr);
            } catch (ParseException e) {
                Log.e("HomeFragment", "Error parsing date: " + dateStr, e);
            }

            if (date != null) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);

                int recordDay = calendar.get(Calendar.DAY_OF_MONTH);
                int recordMonth = calendar.get(Calendar.MONTH) + 1; // Tháng bắt đầu từ 0
                int recordYear = calendar.get(Calendar.YEAR);

                if (recordDay == currentDay && recordMonth == currentMonth && recordYear == currentYear && transaction.getTypeId() == 1 || transaction.getTypeId() == 2) {
                    totalAmount += transaction.getAmount();
                }
            }
        }

        return totalAmount;
    }

    // Cập nhật phương thức `sumAmountForCurrentMonth` và `sumAmountForCurrentMonthChiTieu` tương tự
    private double sumAmountForCurrentMonth(List<Transaction> transactions) {
        int currentMonth = getCurrentMonth();
        int currentYear = getCurrentYear();
        double totalAmount = 0.0;

        SimpleDateFormat format24 = new SimpleDateFormat("HH:mm dd/MM/yyyy", Locale.getDefault());

        for (Transaction transaction : transactions) {
            String dateStr = cleanDateString(transaction.getDate());
            Date date = null;

            try {
                date = format24.parse(dateStr);
            } catch (ParseException e) {
                Log.e("HomeFragment", "Error parsing date: " + dateStr, e);
            }

            if (date != null) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);

                int recordMonth = calendar.get(Calendar.MONTH) + 1; // Tháng bắt đầu từ 0
                int recordYear = calendar.get(Calendar.YEAR);

                if (recordMonth == currentMonth && recordYear == currentYear && transaction.getTypeId() == 3) {
                    totalAmount += transaction.getAmount();
                }
            }
        }

        return totalAmount;
    }

    private double sumAmountForCurrentMonthChiTieu(List<Transaction> transactions) {
        int currentMonth = getCurrentMonth();
        int currentYear = getCurrentYear();
        double totalAmount = 0.0;

        SimpleDateFormat format24 = new SimpleDateFormat("HH:mm dd/MM/yyyy", Locale.getDefault());

        for (Transaction transaction : transactions) {
            String dateStr = cleanDateString(transaction.getDate());
            Date date = null;

            try {
                date = format24.parse(dateStr);
            } catch (ParseException e) {
                Log.e("HomeFragment", "Error parsing date: " + dateStr, e);
            }

            if (date != null) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);

                int recordMonth = calendar.get(Calendar.MONTH) + 1; // Tháng bắt đầu từ 0
                int recordYear = calendar.get(Calendar.YEAR);

                if (recordMonth == currentMonth && recordYear == currentYear && (transaction.getTypeId() == 1 || transaction.getTypeId() == 2)) {
                    totalAmount += transaction.getAmount();
                }
            }
        }

        return totalAmount;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
                super.onViewCreated(view, savedInstanceState);
                binding.tvAdd.setOnClickListener(v -> {
                    if (addClickListener != null) {
                        addClickListener.onClick(v);
                    }
                });
            }

    public void setAddClickListener(View.OnClickListener listener) {
                this.addClickListener = listener;
            }

    @Override
    public void onDestroyView() {
                super.onDestroyView();
                binding = null;
            }
    }

