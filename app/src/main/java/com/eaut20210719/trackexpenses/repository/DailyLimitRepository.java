package com.eaut20210719.trackexpenses.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.eaut20210719.trackexpenses.database.AppDatabase;
import com.eaut20210719.trackexpenses.database.dao.DailyLimitDao;
import com.eaut20210719.trackexpenses.database.entities.DailyLimit;

import java.util.List;

public class DailyLimitRepository {
    private final DailyLimitDao dailyLimitDao;
    private final LiveData<List<DailyLimit>> allDailyLimits;

    public DailyLimitRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application.getApplicationContext());
        dailyLimitDao = db.dailyLimitDao();
        allDailyLimits = dailyLimitDao.getAllDailyLimits();
    }

    public LiveData<List<DailyLimit>> getAllDailyLimits() {
        return allDailyLimits;
    }

    // Kiểm tra và chèn hoặc cập nhật daily_limit
    public void insertOrUpdateDailyLimit(double moneyDay) {
        AppDatabase.getDatabaseWriteExecutor().execute(() -> {
            int count = dailyLimitDao.getDailyLimitCount();
            Log.d("DailyLimitRepository", "Count in daily_limits: " + count);

            if (count == 0) {
                DailyLimit dailyLimit = new DailyLimit(moneyDay);
                dailyLimitDao.insertDailyLimit(dailyLimit);
                Log.d("DailyLimitRepository", "Inserted new daily limit with money_day: " + moneyDay);
            } else {
                Integer lastId = dailyLimitDao.getLastDailyLimitId().getValue();
                if (lastId != null) {
                    DailyLimit dailyLimit = new DailyLimit(moneyDay);
                    dailyLimit.setId(lastId);
                    dailyLimitDao.updateDailyLimit(dailyLimit);
                    Log.d("DailyLimitRepository", "Updated daily limit with ID: " + lastId + " to money_day: " + moneyDay);
                }
            }
        });
    }

//    lấy id của bản ghi cuối cùng
    public Integer getLastDailyLimitId() {
//    // Phương thức để lấy ID mới nhất
    public LiveData<Integer> getLastDailyLimitId() {
        return dailyLimitDao.getLastDailyLimitId();
    }

    // Phương thức để lấy số tiền của bản ghi cuối cùng
    public LiveData<Double> getLastDailyLimitMoney() {
        return dailyLimitDao.getLastDailyLimitMoney();
    }

    public void updateMoneyDaySetting(double moneyDaySetting) {
        AppDatabase.getDatabaseWriteExecutor().execute(() -> {
            dailyLimitDao.updateMoneyDaySetting(moneyDaySetting);
        });
    }

    // Phương thức để lấy ID mới nhất
    public LiveData<Integer> getLastDailyLimitID() {
        return dailyLimitDao.getLastDailyLimitID();
    }

}
