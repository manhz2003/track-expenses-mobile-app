package com.eaut20210719.trackexpenses.ui.activities;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import com.eaut20210719.trackexpenses.R;
import com.eaut20210719.trackexpenses.databinding.ActivityMainBinding;
import com.eaut20210719.trackexpenses.ui.fragments.AddFragment;
import com.eaut20210719.trackexpenses.ui.fragments.HistoryFragment;
import com.eaut20210719.trackexpenses.ui.fragments.HomeFragment;
import com.eaut20210719.trackexpenses.ui.fragments.ReportFragment;
import com.eaut20210719.trackexpenses.ui.fragments.SettingFragment;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportFragmentManager().beginTransaction().add(R.id.flHomeContainer, new HomeFragment()).commit();

        setupMenuItemClickListeners();
    }

    private void setupMenuItemClickListeners() {
        binding.icMenuBottom.tvHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateSelectedItem(binding.icMenuBottom.tvHome);
                getSupportFragmentManager().beginTransaction().replace(R.id.flHomeContainer, new HomeFragment()).commit();
            }
        });

        binding.icMenuBottom.tvHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateSelectedItem(binding.icMenuBottom.tvHistory);
                getSupportFragmentManager().beginTransaction().replace(R.id.flHomeContainer, new HistoryFragment()).commit();
            }
        });

        binding.icMenuBottom.tvUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateSelectedItem(binding.icMenuBottom.tvUpdate);
                getSupportFragmentManager().beginTransaction().replace(R.id.flHomeContainer, new AddFragment()).commit();
            }
        });

        binding.icMenuBottom.tvReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateSelectedItem(binding.icMenuBottom.tvReport);
                getSupportFragmentManager().beginTransaction().replace(R.id.flHomeContainer, new ReportFragment()).commit();
            }
        });

        binding.icMenuBottom.tvSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateSelectedItem(binding.icMenuBottom.tvSetting);
                getSupportFragmentManager().beginTransaction().replace(R.id.flHomeContainer, new SettingFragment()).commit();
            }
        });
    }

    private void updateSelectedItem(View selectedView) {
        // Define colors
        int defaultColor = getResources().getColor(R.color.black); // Màu chữ mặc định
        int selectedColor = getResources().getColor(R.color.color_fd3665); // Màu chữ khi chọn
        int setWhile = getResources().getColor(R.color.white); // Màu nền khi chọn

        // Reset all items to default color
        binding.icMenuBottom.tvHome.setTextColor(defaultColor);
        binding.icMenuBottom.tvHome.setTypeface(null, Typeface.NORMAL); // Reset bold
        binding.icMenuBottom.tvHistory.setTextColor(defaultColor);
        binding.icMenuBottom.tvHistory.setTypeface(null, Typeface.NORMAL); // Reset bold
        binding.icMenuBottom.tvUpdate.setBackgroundColor(setWhile);
        binding.icMenuBottom.tvReport.setTextColor(defaultColor);
        binding.icMenuBottom.tvReport.setTypeface(null, Typeface.NORMAL); // Reset bold
        binding.icMenuBottom.tvSetting.setTextColor(defaultColor);
        binding.icMenuBottom.tvSetting.setTypeface(null, Typeface.NORMAL); // Reset bold

        if (selectedView instanceof TextView) {
            TextView selectedTextView = (TextView) selectedView;
            selectedTextView.setTextColor(selectedColor);
            selectedTextView.setTypeface(null, Typeface.BOLD); // Set bold
        } else if (selectedView instanceof ImageView) {
            selectedView.setBackgroundColor(setWhile);
        }
    }

}
