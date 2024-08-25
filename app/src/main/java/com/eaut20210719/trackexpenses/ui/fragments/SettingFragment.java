package com.eaut20210719.trackexpenses.ui.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.eaut20210719.trackexpenses.R;
import com.eaut20210719.trackexpenses.databinding.SettingFragmentBinding;
import com.eaut20210719.trackexpenses.viewmodels.MonthlyLimitViewModel;

public class SettingFragment extends Fragment {

    private static final String PREFS_NAME = "SettingFragmentPrefs";
    private static final String KEY_EXPENSE_PROGRESS = "ExpenseProgress";

    private SettingFragmentBinding binding;
    private MonthlyLimitViewModel monthlyLimitViewModel;
    private EditText etInputMoney;
    private SeekBar sbExpenses;
    private TextView tvMoney;
    private Button btnSave;
    private SharedPreferences sharedPreferences;

    private View.OnClickListener addClickListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = SettingFragmentBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        // Initialize ViewModel
        monthlyLimitViewModel = new ViewModelProvider(this).get(MonthlyLimitViewModel.class);

        // Initialize UI components
        etInputMoney = binding.edtMonthlySpending;
        sbExpenses = binding.sbExpenses1;
        tvMoney = binding.money;
        btnSave = binding.btnSave4;

        // Initialize SharedPreferences
        sharedPreferences = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        // Cập nhật số tiền chi tiêu từ bản ghi cuối cùng
        monthlyLimitViewModel.getLastMonthlyLimitMoney().observe(getViewLifecycleOwner(), lastMoneyDay -> {
            if (lastMoneyDay != null) {
                int maxAmount = (int) lastMoneyDay.doubleValue();
                sbExpenses.setMax(maxAmount);

                // Đọc giá trị từ SharedPreferences và cập nhật SeekBar và TextView
                int savedProgress = sharedPreferences.getInt(KEY_EXPENSE_PROGRESS, 0);
                sbExpenses.setProgress(savedProgress);
                tvMoney.setText(String.format("%,d VND", savedProgress));
            }
        });

        // Đặt sự kiện thay đổi thanh cuộn
        sbExpenses.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvMoney.setText(String.format("%,d VND", progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Không cần xử lý gì ở đây
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Cập nhật giá trị trong ViewModel và SharedPreferences
                int progress = seekBar.getProgress();
                monthlyLimitViewModel.updateMoneyMonthSetting(progress);

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt(KEY_EXPENSE_PROGRESS, progress);
                editor.apply();
            }
        });

        // Xử lý sự kiện nhấn nút Save
        btnSave.setOnClickListener(v -> {
            String inputMoneyStr = etInputMoney.getText().toString();
            if (!inputMoneyStr.isEmpty()) {
                try {
                    double inputMoney = Double.parseDouble(inputMoneyStr);
                    monthlyLimitViewModel.insertOrUpdateMonthlyLimit(inputMoney);
                    etInputMoney.setText("");
                    Toast.makeText(getContext(), "Thiết lập thành công", Toast.LENGTH_SHORT).show();
                } catch (NumberFormatException e) {
                    Toast.makeText(getContext(), "Vui lòng nhập số tiền hợp lệ", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), "Vui lòng nhập số tiền", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
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
        binding = null; // tránh memory leak
    }
}
