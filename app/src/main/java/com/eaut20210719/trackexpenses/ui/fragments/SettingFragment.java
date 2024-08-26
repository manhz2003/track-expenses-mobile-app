package com.eaut20210719.trackexpenses.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import com.eaut20210719.trackexpenses.database.AppDatabase;
import com.eaut20210719.trackexpenses.databinding.SettingFragmentBinding;
import com.eaut20210719.trackexpenses.viewmodels.MonthlyLimitViewModel;

import java.util.Random;

public class SettingFragment extends Fragment {

    private static final String PREFS_NAME = "SettingFragmentPrefs";
    private static final String KEY_EXPENSE_PROGRESS = "ExpenseProgress";

    private SettingFragmentBinding binding;
    private MonthlyLimitViewModel monthlyLimitViewModel;
    private SharedPreferences sharedPreferences;
    private View.OnClickListener addClickListener;
    private AppDatabase appDatabase;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = SettingFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        monthlyLimitViewModel = new ViewModelProvider(this).get(MonthlyLimitViewModel.class);
        sharedPreferences = requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        appDatabase = AppDatabase.getInstance(requireContext());

        setupUI();

        binding.tvAdd.setOnClickListener(v -> {
            if (addClickListener != null) {
                addClickListener.onClick(v);
            }
        });

        // Xử lý sự kiện khi nhấn vào TextView gmailsupport
        binding.gmailsupport.setOnClickListener(v -> openEmailSupport());
    }

    private void openEmailSupport() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"hotro.quanlychitieu@gmail.com"});
        intent.putExtra(Intent.EXTRA_SUBJECT, "Yêu cầu hỗ trợ");

        if (intent.resolveActivity(requireActivity().getPackageManager()) != null) {
            startActivity(Intent.createChooser(intent, "Chọn ứng dụng gửi email"));
        } else {
            Toast.makeText(getContext(), "Không tìm thấy ứng dụng email", Toast.LENGTH_SHORT).show();
        }
    }

    private void setupUI() {
        monthlyLimitViewModel.getLastMonthlyLimitMoney().observe(getViewLifecycleOwner(), lastMoneyDay -> {
            if (lastMoneyDay != null) {
                int maxAmount = (int) lastMoneyDay.doubleValue();
                binding.sbExpenses1.setMax(maxAmount);

                int savedProgress = sharedPreferences.getInt(KEY_EXPENSE_PROGRESS, 0);
                binding.sbExpenses1.setProgress(savedProgress);
                binding.money.setText(String.format("%,d VND", savedProgress));
            }
        });

        binding.sbExpenses1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                binding.money.setText(String.format("%,d VND", progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Không cần xử lý gì ở đây
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int progress = seekBar.getProgress();
                monthlyLimitViewModel.updateMoneyMonthSetting(progress);

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt(KEY_EXPENSE_PROGRESS, progress);
                editor.apply();
            }
        });

        binding.btnSave4.setOnClickListener(v -> {
            String inputMoneyStr = binding.edtMonthlySpending.getText().toString().trim();
            if (inputMoneyStr.isEmpty()) {
                Toast.makeText(getContext(), "Vui lòng nhập số tiền", Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                double inputMoney = Double.parseDouble(inputMoneyStr);
                if (inputMoney <= 0) {
                    Toast.makeText(getContext(), "Số tiền phải lớn hơn 0", Toast.LENGTH_SHORT).show();
                    return;
                }
                monthlyLimitViewModel.insertOrUpdateMonthlyLimit(inputMoney);
                binding.edtMonthlySpending.setText("");
                Toast.makeText(getContext(), "Thiết lập thành công", Toast.LENGTH_SHORT).show();
            } catch (NumberFormatException e) {
                Toast.makeText(getContext(), "Vui lòng nhập số tiền hợp lệ", Toast.LENGTH_SHORT).show();
            }
        });

        // Xử lý sự kiện nút xóa
        binding.deletedata.setOnClickListener(v -> showDeleteConfirmationDialog());
    }

    // Hiển thị hộp thoại xác nhận xóa
    private void showDeleteConfirmationDialog() {
        new AlertDialog.Builder(requireContext())
                .setTitle("Xác nhận xóa")
                .setMessage("Bạn có chắc chắn muốn xóa toàn bộ dữ liệu không?")
                .setPositiveButton("Xóa", (dialog, which) -> clearAllData())
                .setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss())
                .show();
    }

//    // Xóa toàn bộ dữ liệu khỏi cơ sở dữ liệu và SharedPreferences
    private void clearAllData() {
        // Xóa toàn bộ dữ liệu trong database
        AppDatabase.getDatabaseWriteExecutor().execute(() -> {
            appDatabase.categoryDao().deleteAll();
            appDatabase.dailyLimitDao().deleteAll();
            appDatabase.monthlyLimitDao().deleteAll();
            appDatabase.colorDao().deleteAll();
            appDatabase.transactionDao().deleteAll();
            appDatabase.settingDao().deleteAll();
        });

        // Xóa dữ liệu SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        // Cập nhật giao diện sau khi xóa
        requireActivity().runOnUiThread(() -> {
            monthlyLimitViewModel.updateMoneyMonthSetting(0);
            binding.sbExpenses1.setProgress(0);
            binding.money.setText("0 VND");
            Toast.makeText(getContext(), "Toàn bộ dữ liệu đã được xóa", Toast.LENGTH_SHORT).show();
        });
    }

    public void setAddClickListener(View.OnClickListener listener) {
        this.addClickListener = listener;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Giải phóng binding để tránh memory leak
    }
}
