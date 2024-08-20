package com.eaut20210719.trackexpenses.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.eaut20210719.trackexpenses.databinding.SettingFragmentBinding;

public class SettingFragment extends Fragment {

    private SettingFragmentBinding binding;
    private View.OnClickListener addClickListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = SettingFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    // Optionally, override other lifecycle methods


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