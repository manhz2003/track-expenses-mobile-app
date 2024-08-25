package com.eaut20210719.trackexpenses.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.eaut20210719.trackexpenses.databinding.ItemHistoryBinding;
import com.eaut20210719.trackexpenses.dto.History;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private List<History> items;
    private final OnThreeDotsClickListener threeDotsClickListener;

    // Constructor accepting the OnThreeDotsClickListener
    public HistoryAdapter(List<History> items, OnThreeDotsClickListener listener) {
        this.items = items;
        this.threeDotsClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemHistoryBinding binding = ItemHistoryBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        History item = items.get(position);
        holder.bind(item, position, threeDotsClickListener);
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0; // Handle case when items is null
    }

    public void setItems(List<History> items) {
        this.items = items;
        notifyDataSetChanged(); // Thông báo adapter để làm mới giao diện
    }

    // Define the interface for the click listener
    public interface OnThreeDotsClickListener {
        void onThreeDotsClick(int position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemHistoryBinding binding;

        public ViewHolder(ItemHistoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(History item, int position, OnThreeDotsClickListener listener) {
            // Bind data to views
            binding.tvHistoryBuyContent.setText(item.getNameCategory()); // Bind nameCategory
            binding.tvHistoryBuy1.setText(item.getContent()); // Bind content
            binding.tvTime.setText(item.getDate()); // Bind date
            binding.tvspend.setText(item.getTypeName()); // Bind type_name
            binding.tvAmount.setText(String.valueOf(item.getAmount())); // Bind amount

            // Set the click listener for tvThreedots
            binding.tvThreedots.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onThreeDotsClick(item.getId()); // Pass the transaction ID
                }
            });
        }
    }
}
