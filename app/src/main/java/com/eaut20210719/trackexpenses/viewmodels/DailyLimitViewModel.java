package com.eaut20210719.trackexpenses.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.eaut20210719.trackexpenses.database.entities.Category;
import com.eaut20210719.trackexpenses.database.entities.DailyLimit;
import com.eaut20210719.trackexpenses.database.entities.MonthlyLimit;
import com.eaut20210719.trackexpenses.repository.DailyLimitRepository;

import java.util.List;

public class DailyLimitViewModel extends AndroidViewModel {
    private final DailyLimitRepository repository;
    private static final String TAG = "DailyLimitViewModel";
    private final LiveData<List<DailyLimit>> allDailyLimits;

    public DailyLimitViewModel(@NonNull Application application) {
        super(application);
        repository = new DailyLimitRepository(application);
        allDailyLimits = repository.getAllDailyLimits();
        logAllDailyLimits();
    }

    // Phương thức mới để log toàn bộ dữ liệu bảng daily_limits
    public void logAllDailyLimits() {
        allDailyLimits.observeForever(monthlyLimits -> {
            if (monthlyLimits == null || monthlyLimits.isEmpty()) {
                System.out.println("Không có dữ liệu trong bảng daily_limits");
            } else {
                for (DailyLimit dailyLimit : monthlyLimits) {
                    System.out.println("Daily Limit ID: " + dailyLimit.getId() + ", Daily Limit Amount: " + dailyLimit.getMoney_day());
                }
            }
        });
    }
}
