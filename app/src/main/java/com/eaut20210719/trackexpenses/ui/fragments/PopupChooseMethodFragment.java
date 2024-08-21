package com.eaut20210719.trackexpenses.ui.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.eaut20210719.trackexpenses.R;

public class PopupChooseMethodFragment extends DialogFragment {

    @Nullable
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setContentView(R.layout.popup_choose_method);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        Button btnEdit = dialog.findViewById(R.id.btnEdit);
        Button btnDelete = dialog.findViewById(R.id.btnDeleteRecord);
        Button btnClose = dialog.findViewById(R.id.btnClose);

        btnEdit.setOnClickListener(v -> {
            // Handle Edit action
            dismiss(); // Close dialog after action
        });

        btnDelete.setOnClickListener(v -> {
            // Handle Delete action
            dismiss(); // Close dialog after action
        });

        btnClose.setOnClickListener(v -> dismiss()); // Close the popup

        return dialog;
    }
}
