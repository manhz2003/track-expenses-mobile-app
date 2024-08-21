package com.eaut20210719.trackexpenses.ui.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.eaut20210719.trackexpenses.database.entities.Category;
import com.eaut20210719.trackexpenses.databinding.AddFragmentBinding;
import com.eaut20210719.trackexpenses.viewmodels.CategoryViewModel;

import java.util.ArrayList;
import java.util.List;

public class AddFragment extends Fragment {

    private AddFragmentBinding binding;
    private CategoryViewModel categoryViewModel;
    private ArrayAdapter<String> spinnerAdapter;
    private List<String> categoriesList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = AddFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize ViewModel
        categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);

        spinnerAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, categoriesList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerCatalog.setAdapter(spinnerAdapter);

        // Observe the LiveData
        categoryViewModel.getAllCategories().observe(getViewLifecycleOwner(), categories -> {
            categoriesList.clear(); // Clear the old list
            for (Category category : categories) {
                categoriesList.add(category.getName());
            }
            spinnerAdapter.notifyDataSetChanged(); // Notify adapter about the changes
        });

        binding.btnSave2.setOnClickListener(v -> {
            // Log the value before checking
            String categoryName = binding.editTextCategoryName.getText().toString().trim();
            Log.d("AddFragment", "Category Name before check: [" + categoryName + "]");

            if (!categoryName.isEmpty()) {
                // Log the value when it's not empty
                Log.d("AddFragment", "Category Name: [" + categoryName + "]");
                Category category = new Category(categoryName);
                categoryViewModel.insert(category);

                // Clear the input field
                binding.editTextCategoryName.setText("");
                Toast.makeText(getContext(), "Danh mục đã được thêm", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Vui lòng nhập tên danh mục", Toast.LENGTH_SHORT).show();
            }
        });

    }



}
