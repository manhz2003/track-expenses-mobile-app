package com.eaut20210719.trackexpenses.ui.activities;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.eaut20210719.trackexpenses.R;
import com.eaut20210719.trackexpenses.database.entities.MonthlyLimit;
import com.eaut20210719.trackexpenses.databinding.ActivityMainBinding;
import com.eaut20210719.trackexpenses.ui.fragments.AddFragment;
import com.eaut20210719.trackexpenses.ui.fragments.HistoryFragment;
import com.eaut20210719.trackexpenses.ui.fragments.HomeFragment;
import com.eaut20210719.trackexpenses.ui.fragments.ReportFragment;
import com.eaut20210719.trackexpenses.ui.fragments.SettingFragment;
import com.eaut20210719.trackexpenses.viewmodels.CategoryViewModel;
import com.eaut20210719.trackexpenses.viewmodels.ColorViewModel;
import com.eaut20210719.trackexpenses.viewmodels.DailyLimitViewModel;
import com.eaut20210719.trackexpenses.viewmodels.MonthlyLimitViewModel;
import com.eaut20210719.trackexpenses.viewmodels.SettingViewModel;
import com.eaut20210719.trackexpenses.viewmodels.TransactionViewModel;
import com.eaut20210719.trackexpenses.viewmodels.TypeViewModel;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private TransactionViewModel mTransactionViewModel;
    private TypeViewModel mTypeViewModel;
    private CategoryViewModel mCategoryViewModel;
    private DailyLimitViewModel mDailyLimitViewModel;
    private MonthlyLimitViewModel mMonthlyLimit;
    private SettingViewModel mSettingViewModel;
    private ColorViewModel mColorViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        logcat data bảng transactions
        mTransactionViewModel = new ViewModelProvider(this).get(TransactionViewModel.class);
        mTransactionViewModel.logAllTransactionsOnce();

//        logcat data bảng types
        mTypeViewModel = new ViewModelProvider(this).get(TypeViewModel.class);
        mTypeViewModel.logAllTypes();

//        logcat data bảng categories
        mCategoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        mCategoryViewModel.logAllCategories();

//        logcat data bảng daily_limits
        mDailyLimitViewModel = new ViewModelProvider(this).get(DailyLimitViewModel.class);
        mDailyLimitViewModel.logAllDailyLimits();

//        logcat data bảng monthly_limits
        mMonthlyLimit = new ViewModelProvider(this).get(MonthlyLimitViewModel.class);
        mMonthlyLimit.logAllMonthlyLimits();

//        logcat data bảng settings
        mSettingViewModel = new ViewModelProvider(this).get(SettingViewModel.class);
        mSettingViewModel.logAllSettings();

//        logcat data bảng colors
        mColorViewModel = new ViewModelProvider(this).get(ColorViewModel.class);
        mColorViewModel.logAllColors();

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
                updateSelectedItem(binding.icMenuBottom.tvUpdate); // Cập nhật mục đã chọn
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
        int defaultColor = getResources().getColor(R.color.black);
        if (fragment instanceof HomeFragment) {
            ((HomeFragment) fragment).setAddClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onAddNewClicked();
                    binding.icMenuBottom.tvHome.setTextColor(defaultColor);
                    binding.icMenuBottom.tvHome.setTypeface(null, Typeface.NORMAL); // Reset bold

                }
            });
        } else if (fragment instanceof HistoryFragment) {
            ((HistoryFragment) fragment).setAddClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onAddNewClicked();
                    binding.icMenuBottom.tvHistory.setTextColor(defaultColor);
                    binding.icMenuBottom.tvHistory.setTypeface(null, Typeface.NORMAL); // Reset bold
                }
            });
        } else if (fragment instanceof SettingFragment) {
            ((SettingFragment) fragment).setAddClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onAddNewClicked();
                    binding.icMenuBottom.tvSetting.setTextColor(defaultColor);
                    binding.icMenuBottom.tvSetting.setTypeface(null, Typeface.NORMAL);
                }
            });
        } else if (fragment instanceof ReportFragment) {
            ((ReportFragment) fragment).setAddClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onAddNewClicked();
                    binding.icMenuBottom.tvReport.setTextColor(defaultColor);
                    binding.icMenuBottom.tvReport.setTypeface(null, Typeface.NORMAL);
                }
            });
        }
    }

    private void updateSelectedItem(View selectedView) {
        // Define colors
        int defaultColor = getResources().getColor(R.color.black); // Màu chữ mặc định
        int selectedColor = getResources().getColor(R.color.color_fd3665); // Màu chữ khi chọn
        int selectedBackgroundColor = getResources().getColor(R.color.white); // Màu nền khi chọn

        // Reset all items to default color and font weight
        binding.icMenuBottom.tvHome.setTextColor(defaultColor);
        binding.icMenuBottom.tvHome.setTypeface(null, Typeface.NORMAL); // Reset bold
        binding.icMenuBottom.tvHistory.setTextColor(defaultColor);
        binding.icMenuBottom.tvHistory.setTypeface(null, Typeface.NORMAL); // Reset bold
        binding.icMenuBottom.tvUpdate.setBackgroundColor(defaultColor);
        binding.icMenuBottom.tvReport.setTextColor(defaultColor);
        binding.icMenuBottom.tvReport.setTypeface(null, Typeface.NORMAL); // Reset bold
        binding.icMenuBottom.tvSetting.setTextColor(defaultColor);
        binding.icMenuBottom.tvSetting.setTypeface(null, Typeface.NORMAL); // Reset bold

        // Reset background color of all items
        binding.icMenuBottom.tvHome.setBackgroundColor(Color.TRANSPARENT);
        binding.icMenuBottom.tvHistory.setBackgroundColor(Color.TRANSPARENT);
        binding.icMenuBottom.tvUpdate.setBackgroundColor(Color.TRANSPARENT);
        binding.icMenuBottom.tvReport.setBackgroundColor(Color.TRANSPARENT);
        binding.icMenuBottom.tvSetting.setBackgroundColor(Color.TRANSPARENT);

        if (selectedView instanceof TextView) {
            TextView selectedTextView = (TextView) selectedView;
            selectedTextView.setTextColor(selectedColor);
            selectedTextView.setTypeface(null, Typeface.BOLD); // Set bold
        } else if (selectedView instanceof ImageView) {
            selectedView.setBackgroundColor(selectedBackgroundColor);
        }
    }

}