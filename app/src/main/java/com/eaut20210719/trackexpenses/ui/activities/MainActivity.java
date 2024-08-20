package com.eaut20210719.trackexpenses.ui.activities;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
                updateSelectedItem(binding.icMenuBottom.tvHome);
            }
        });

        binding.icMenuBottom.tvHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HistoryFragment historyFragment = new HistoryFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.flHomeContainer, historyFragment).commit();
                setUpFragmentListener(historyFragment);
                updateSelectedItem(binding.icMenuBottom.tvHistory);
            }
        });

        binding.icMenuBottom.tvUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.flHomeContainer, new AddFragment()).commit();
                updateSelectedItem(binding.icMenuBottom.tvUpdate);
            }

        });

        binding.icMenuBottom.tvReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReportFragment reportFragment = new ReportFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.flHomeContainer, reportFragment).commit();
                setUpFragmentListener(reportFragment);
                updateSelectedItem(binding.icMenuBottom.tvReport);
            }
        });

        binding.icMenuBottom.tvSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingFragment settingFragment = new SettingFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.flHomeContainer, settingFragment).commit();
                setUpFragmentListener(settingFragment);
                updateSelectedItem(binding.icMenuBottom.tvSetting);
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