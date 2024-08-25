package com.eaut20210719.trackexpenses.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.eaut20210719.trackexpenses.database.entities.Category;
import com.eaut20210719.trackexpenses.database.entities.MonthlyLimit;
import com.eaut20210719.trackexpenses.repository.MonthlyLimitRepository;

import java.util.List;

public class MonthlyLimitViewModel extends AndroidViewModel {
    private final MonthlyLimitRepository repository;
    private final LiveData<List<MonthlyLimit>> allMonthlyLimits;
    private static final String TAG = "MonthlyLimitViewModel";

    public MonthlyLimitViewModel(@NonNull Application application) {
        super(application);
        repository = new MonthlyLimitRepository(application);
        allMonthlyLimits = repository.getAllMonthlyLimits();
        logAllMonthlyLimits();
    }

    // Phương thức mới để log toàn bộ dữ liệu bảng monthly_limits
    public void logAllMonthlyLimits() {
        allMonthlyLimits.observeForever(monthlyLimits -> {
            if (monthlyLimits == null || monthlyLimits.isEmpty()) {
                System.out.println("Không có dữ liệu trong bảng monthly_limits");
            } else {
                for (MonthlyLimit monthlyLimit : monthlyLimits) {
                    System.out.println("data bảng monthly_limit: " + "Monthly Limit ID: " + monthlyLimit.getId() + ", Monthly Limit Amount: " + monthlyLimit.getMoney_month() + ", Monthly Limit Setting: " + monthlyLimit.getMoney_month_setting());
                }
            }
        });
    }

}
