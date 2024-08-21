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
    private View.OnClickListener searchClickListener;

    private int originalHistoryTopMargin;
    private int originalRvTopMargin;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = HistoryFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Save the original margin values
        ViewGroup.MarginLayoutParams historyParams = (ViewGroup.MarginLayoutParams) binding.history.getLayoutParams();
        originalHistoryTopMargin = historyParams.topMargin;

        ViewGroup.MarginLayoutParams rvParams = (ViewGroup.MarginLayoutParams) binding.rvHistory.getLayoutParams();
        originalRvTopMargin = rvParams.topMargin;

        // Set the click listener for tvAdd
        binding.tvAdd.setOnClickListener(v -> {
            if (addClickListener != null) {
                addClickListener.onClick(v);
            }
        });

        // Set the click listener for imSearch
        binding.imSearch.setOnClickListener(v -> togglePopupSearchLayout());

        // Initialize the RecyclerView and adapter
        initView();
    }

    private void initView() {
        String[] list = new String[50];
        for (int i = 0; i < 20; i++) {
            list[i] = String.valueOf(i);
        }

        // Set up the RecyclerView with the adapter
        binding.rvHistory.setLayoutManager(new LinearLayoutManager(requireActivity()));
        binding.rvHistory.setAdapter(new HistoryAdapter(list, position -> showPopupMethod()));
    }

    private void showPopupMethod() {
        PopupChooseMethodFragment dialogFragment = new PopupChooseMethodFragment();
        dialogFragment.setCancelable(true); // Make sure the dialog can be canceled
        dialogFragment.show(getChildFragmentManager(), "PopupChooseMethodFragment");
    }

    // Method for MainActivity to set a click listener for tvAdd
    public void setAddClickListener(View.OnClickListener listener) {
        this.addClickListener = listener;
    }

    // Method for MainActivity to set a click listener for imSearch
    public void setSearchClickListener(View.OnClickListener listener) {
        this.searchClickListener = listener;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Avoid memory leaks
    }

    // Method to toggle the visibility of popupSearchLayout
    private void togglePopupSearchLayout() {
        if (binding.popupSearchLayout.getVisibility() == View.GONE) {
            // Show popup and adjust other views
            binding.popupSearchLayout.setVisibility(View.VISIBLE);

            // Push FrameLayout and RecyclerView down
            ViewGroup.MarginLayoutParams historyParams = (ViewGroup.MarginLayoutParams) binding.history.getLayoutParams();
            historyParams.topMargin = getResources().getDimensionPixelSize(R.dimen.popup_search_layout_height); // Adjust as required
            binding.history.setLayoutParams(historyParams);

            ViewGroup.MarginLayoutParams rvParams = (ViewGroup.MarginLayoutParams) binding.rvHistory.getLayoutParams();
            rvParams.topMargin = getResources().getDimensionPixelSize(R.dimen.popup_search_layout_height); // Adjust as required
            binding.rvHistory.setLayoutParams(rvParams);
        } else {
            // Hide popup and restore other views
            binding.popupSearchLayout.setVisibility(View.GONE);

            // Restore original position of FrameLayout and RecyclerView
            ViewGroup.MarginLayoutParams historyParams = (ViewGroup.MarginLayoutParams) binding.history.getLayoutParams();
            historyParams.topMargin = originalHistoryTopMargin; // Restore original value
            binding.history.setLayoutParams(historyParams);

            ViewGroup.MarginLayoutParams rvParams = (ViewGroup.MarginLayoutParams) binding.rvHistory.getLayoutParams();
            rvParams.topMargin = originalRvTopMargin; // Restore original value
            binding.rvHistory.setLayoutParams(rvParams);
        }
    }
}
