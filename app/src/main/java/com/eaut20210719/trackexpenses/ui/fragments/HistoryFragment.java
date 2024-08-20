package com.eaut20210719.trackexpenses.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.eaut20210719.trackexpenses.R;
import com.eaut20210719.trackexpenses.databinding.HistoryFragmentBinding;
import com.eaut20210719.trackexpenses.ui.adapters.HistoryAdapter;


public class HistoryFragment extends Fragment {

    private HistoryFragmentBinding binding;
    private View.OnClickListener addClickListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = HistoryFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    // Optionally, override other lifecycle methods


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Gán sự kiện nhấn cho tvAdd
        binding.tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addClickListener != null) {
                    addClickListener.onClick(v);
                }
            }
        });
        initView();
    }

    private void initView() {
        String[] list = new String[50];
        for (int i = 0; i < 20; i++) {
            list[i] = String.valueOf(i);
        }
        binding.rvHistory.setLayoutManager(new LinearLayoutManager(requireActivity()));
        binding.rvHistory.setAdapter(new HistoryAdapter(list));

    }

    // Phương thức để MainActivity thiết lập sự kiện click cho tvAdd
    public void setAddClickListener(View.OnClickListener listener) {
        this.addClickListener = listener;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // tránh memory leak
    }




}