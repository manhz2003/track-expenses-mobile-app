package com.eaut20210719.trackexpenses.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.eaut20210719.trackexpenses.databinding.ItemHistoryBinding;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private final String[] items;
    private final OnThreeDotsClickListener threeDotsClickListener;

    // Constructor accepting the OnThreeDotsClickListener
    public HistoryAdapter(String[] items, OnThreeDotsClickListener listener) {
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
        String item = items[position];
        holder.bind(item, position, threeDotsClickListener);
    }

    @Override
    public int getItemCount() {
        return items.length;
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

        public void bind(String item, int position, OnThreeDotsClickListener listener) {
            // Bind data to views
            binding.tvHistoryBuy1.setText(item);

            // Set the click listener for tvThreedots
            binding.tvThreedots.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onThreeDotsClick(position);
                }
            });
        }
    }
}
