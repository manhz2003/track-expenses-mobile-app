package com.eaut20210719.trackexpenses.ui.activities;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

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

        // Hiển thị HomeFragment mặc định
        HomeFragment homeFragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.flHomeContainer, homeFragment).commit();
        setUpFragmentListener(homeFragment);

        binding.icMenuBottom.tvHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeFragment homeFragment = new HomeFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.flHomeContainer, homeFragment).commit();
                setUpFragmentListener(homeFragment);
            }
        });

        binding.icMenuBottom.tvHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HistoryFragment historyFragment = new HistoryFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.flHomeContainer, historyFragment).commit();
                setUpFragmentListener(historyFragment);
            }
        });

        binding.icMenuBottom.tvUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.flHomeContainer, new AddFragment()).commit();
            }
        });

        binding.icMenuBottom.tvReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReportFragment reportFragment = new ReportFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.flHomeContainer, reportFragment).commit();
                setUpFragmentListener(reportFragment);
            }
        });

        binding.icMenuBottom.tvSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingFragment settingFragment = new SettingFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.flHomeContainer, settingFragment).commit();
                setUpFragmentListener(settingFragment);
            }
        });
    }

    // Phương thức để xử lý sự kiện nhấn vào TextView "Thêm mới"
    public void onAddNewClicked() {
        // Mở AddFragment khi nhấn vào "Thêm mới"
        getSupportFragmentManager().beginTransaction().replace(R.id.flHomeContainer, new AddFragment()).commit();
    }

    private void setUpFragmentListener(Fragment fragment) {
        if (fragment instanceof HomeFragment) {
            ((HomeFragment) fragment).setAddClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onAddNewClicked();
                }
            });
        } else if (fragment instanceof HistoryFragment) {
            ((HistoryFragment) fragment).setAddClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onAddNewClicked();
                }
            });
        } else if (fragment instanceof SettingFragment) {
            ((SettingFragment) fragment).setAddClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onAddNewClicked();
                }
            });
        } else if (fragment instanceof ReportFragment) {
            ((ReportFragment) fragment).setAddClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onAddNewClicked();
                }
            });
        }
    }


}
