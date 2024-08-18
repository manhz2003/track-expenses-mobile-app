package com.eaut20210719.trackexpenses.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.eaut20210719.trackexpenses.databinding.AddFragmentBinding;
import com.eaut20210719.trackexpenses.databinding.HomeFragmentBinding;

public class AddFragment extends Fragment {

    private AddFragmentBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = AddFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    // Optionally, override other lifecycle methods

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

}
