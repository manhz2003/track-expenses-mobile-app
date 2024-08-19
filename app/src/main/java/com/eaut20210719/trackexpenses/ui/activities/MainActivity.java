package com.eaut20210719.trackexpenses.ui.activities;

import android.os.Bundle;
import android.view.View;

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

        binding.icMenuBottom.tvHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().add(R.id.flHomeContainer, new HomeFragment()).commit();
            }
        });

        binding.icMenuBottom.tvHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().add(R.id.flHomeContainer, new HistoryFragment()).commit();
            }
        });

        binding.icMenuBottom.tvUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().add(R.id.flHomeContainer, new AddFragment()).commit();
            }
        });

        binding.icMenuBottom.tvReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().add(R.id.flHomeContainer, new ReportFragment()).commit();
            }
        });

        binding.icMenuBottom.tvSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().add(R.id.flHomeContainer, new SettingFragment()).commit();
            }
        });
    }
}