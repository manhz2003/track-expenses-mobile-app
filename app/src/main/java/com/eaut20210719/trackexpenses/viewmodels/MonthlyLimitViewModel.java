package com.eaut20210719.trackexpenses.viewmodels;

import android.app.Application;
import android.util.Log;

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
        checkAndInsertInitialMonthlyLimit();
    }

    public void logAllMonthlyLimits() {
        allMonthlyLimits.observeForever(monthlyLimits -> {
            if (monthlyLimits == null || monthlyLimits.isEmpty()) {
                Log.d(TAG, "Không có dữ liệu trong bảng monthly_limits");
            } else {
                for (MonthlyLimit monthlyLimit : monthlyLimits) {
                    Log.d(TAG, "Monthly Limit ID: " + monthlyLimit.getId() + ", Monthly Limit Amount: " + monthlyLimit.getMoney_month() + ", Monthly Limit Setting: " + monthlyLimit.getMoney_month_setting());
                }
            }
        });
    }

    public void insertOrUpdateMonthlyLimit(double moneyMonth) {
        repository.insertOrUpdateMonthlyLimit(moneyMonth);
    }

    public LiveData<Integer> getLastMonthlyLimitId() {
        return repository.getLastMonthlyLimitId();
    }

    public LiveData<List<MonthlyLimit>> getAllMonthlyLimits() {
        return allMonthlyLimits;
    }

    public LiveData<Double> getLastMonthlyLimitMoney() {
        return repository.getLastMonthlyLimitMoney();
    }

    private void checkAndInsertInitialMonthlyLimit() {
        allMonthlyLimits.observeForever(monthlyLimits -> {
            if (monthlyLimits == null || monthlyLimits.isEmpty()) {
                insertOrUpdateMonthlyLimit(0);
                Log.d(TAG, "Bảng monthly_limits trống, đã chèn giá trị 0");
            } else {
                Log.d(TAG, "Bảng monthly_limits đã có dữ liệu");
            }
        });
    }

    public void updateMoneyMonthSetting(double moneyMonthSetting) {
        repository.updateMoneyMonthSetting(moneyMonthSetting);
    }

    public LiveData<Integer> getLastMonthlyLimitId() {
        return repository.getLastMonthlyLimitID();
    }

}
