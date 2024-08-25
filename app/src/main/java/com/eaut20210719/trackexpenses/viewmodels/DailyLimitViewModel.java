package com.eaut20210719.trackexpenses.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.eaut20210719.trackexpenses.database.entities.DailyLimit;
import com.eaut20210719.trackexpenses.repository.DailyLimitRepository;

import java.util.List;

public class DailyLimitViewModel extends AndroidViewModel {
    private final DailyLimitRepository repository;
    private final LiveData<List<DailyLimit>> allDailyLimits;

    public DailyLimitViewModel(@NonNull Application application) {
        super(application);
        repository = new DailyLimitRepository(application);

        allDailyLimits = repository.getAllDailyLimits();
        checkAndInsertInitialDailyLimit();
    }

    // Phương thức để log toàn bộ dữ liệu trong bảng daily_limits
    public void logAllDailyLimits() {
        allDailyLimits.observeForever(dailyLimits -> {
            if (dailyLimits == null || dailyLimits.isEmpty()) {
                Log.d("DailyLimitViewModel", "Không có dữ liệu trong bảng daily_limits");
            } else {
                for (DailyLimit dailyLimit : dailyLimits) {
                    Log.d("DailyLimitViewModel", "Daily Limit ID: " + dailyLimit.getId() + ", Daily Limit Amount: " + dailyLimit.getMoney_day() + ", Daily Limit Setting: " + dailyLimit.getMoney_day_setting());
                }
            }
        });
    }

    // Phương thức để chèn hoặc cập nhật daily_limit
    public void insertOrUpdateDailyLimit(double moneyDay) {
        repository.insertOrUpdateDailyLimit(moneyDay);
    }

    // Phương thức lấy ID mới nhất
    public Integer getLastDailyLimitId() {
        return repository.getLastDailyLimitId();
    }

    // Phương thức lấy tất cả dữ liệu daily_limits
    public LiveData<List<DailyLimit>> getAllDailyLimits() {
        return allDailyLimits;
    }

    // Phương thức lấy số tiền của bản ghi cuối cùng
    public LiveData<Double> getLastDailyLimitMoney() {
        return repository.getLastDailyLimitMoney();
    }

    // Kiểm tra và chèn giá trị 0 nếu bảng daily_limits trống
    private void checkAndInsertInitialDailyLimit() {
        allDailyLimits.observeForever(dailyLimits -> {
            if (dailyLimits == null || dailyLimits.isEmpty()) {
                insertOrUpdateDailyLimit(0);
                Log.d("DailyLimitViewModel", "Bảng daily_limits trống, đã chèn giá trị 0");
            } else {
                Log.d("DailyLimitViewModel", "Bảng daily_limits đã có dữ liệu");
            }
        });
    }

    public void updateMoneyDaySetting(double moneyDaySetting) {
        repository.updateMoneyDaySetting(moneyDaySetting);
    }
}
