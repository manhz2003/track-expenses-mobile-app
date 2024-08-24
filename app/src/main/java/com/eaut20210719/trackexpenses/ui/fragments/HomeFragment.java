package com.eaut20210719.trackexpenses.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.eaut20210719.trackexpenses.R;
import com.eaut20210719.trackexpenses.databinding.HomeFragmentBinding;
import com.eaut20210719.trackexpenses.viewmodels.TransactionViewModel;

public class HomeFragment extends Fragment {

    private HomeFragmentBinding binding;
    private TransactionViewModel transactionViewModel;
    private View.OnClickListener addClickListener; // Khai báo biến addClickListener
    private boolean isBalanceVisible = false; // Ẩn số dư khi khởi tạo

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = HomeFragmentBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        // Khởi tạo ViewModel
        transactionViewModel = new ViewModelProvider(this).get(TransactionViewModel.class);

        // Chức năng ẩn hiện số dư
        ImageView imEye = binding.imEye;
        TextView tvBalance = binding.tvBalance;

        imEye.setOnClickListener(v -> {
            if (isBalanceVisible) {
                tvBalance.setText("*** *** *** VND");
                imEye.setImageResource(R.drawable.eye);
            } else {
                transactionViewModel.getTotalBalance().observe(getViewLifecycleOwner(), new Observer<Double>() {
                    @Override
                    public void onChanged(@Nullable Double totalBalance) {
                        if (totalBalance != null) {
                            tvBalance.setText(String.format("%,.0f VND", totalBalance));
                        }
                    }
                });
                imEye.setImageResource(R.drawable.icon_eye_24);
            }
            isBalanceVisible = !isBalanceVisible;
        });

        // Cập nhật số dư khi fragment được tạo
        if (isBalanceVisible) {
            transactionViewModel.getTotalBalance().observe(getViewLifecycleOwner(), new Observer<Double>() {
                @Override
                public void onChanged(@Nullable Double totalBalance) {
                    if (totalBalance != null) {
                        tvBalance.setText(String.format("%,.0f VND", totalBalance));
                    }
                }
            });
        } else {
            tvBalance.setText("*** *** *** VND");
        }

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addClickListener != null) {
                    addClickListener.onClick(v);
                }
            }
        });
    }

    // Phương thức để MainActivity thiết lập sự kiện click cho tvAdd
    public void setAddClickListener(View.OnClickListener listener) {
        this.addClickListener = listener;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
