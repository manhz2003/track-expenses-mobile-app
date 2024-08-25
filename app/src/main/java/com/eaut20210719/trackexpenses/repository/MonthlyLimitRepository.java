package com.eaut20210719.trackexpenses.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.eaut20210719.trackexpenses.database.AppDatabase;
import com.eaut20210719.trackexpenses.database.dao.MonthlyLimitDao;
import com.eaut20210719.trackexpenses.database.entities.DailyLimit;
import com.eaut20210719.trackexpenses.database.entities.MonthlyLimit;

import java.util.List;

public class MonthlyLimitRepository {
    private final MonthlyLimitDao monthlyLimitDao;
    private final LiveData<List<MonthlyLimit>> allMonthlyLimits;

    public MonthlyLimitRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application.getApplicationContext());
        monthlyLimitDao = db.monthlyLimitDao();
        allMonthlyLimits = monthlyLimitDao.getAllMonthlyLimits();
    }

    public LiveData<List<MonthlyLimit>> getAllMonthlyLimits() {
        return allMonthlyLimits;
    }

    public void insertOrUpdateMonthlyLimit(double moneyMonth) {
        AppDatabase.getDatabaseWriteExecutor().execute(() -> {
            int count = monthlyLimitDao.getMonthlyLimitCount();
            MonthlyLimit monthlyLimit = new MonthlyLimit(moneyMonth);

            if (count == 0) {
                monthlyLimitDao.insertMonthlyLimit(monthlyLimit);
            } else {
                Integer lastId = monthlyLimitDao.getLastMonthlyLimitId();
                if (lastId != null) {
                    monthlyLimit.setId(lastId);
                    monthlyLimitDao.updateMonthlyLimit(monthlyLimit);
                }
            }
        });
    }

    public void updateMoneyMonthSetting(double moneyMonthSetting) {
        AppDatabase.getDatabaseWriteExecutor().execute(() -> {
            Integer lastId = monthlyLimitDao.getLastMonthlyLimitId();
            if (lastId != null) {
                monthlyLimitDao.updateMoneyMonthSetting(moneyMonthSetting);
            }
        });
    }

    public Integer getLastMonthlyLimitId() {
        return monthlyLimitDao.getLastMonthlyLimitId();
    }

    public LiveData<Double> getLastMonthlyLimitMoney() {
        return monthlyLimitDao.getLastMonthlyLimitMoney();
    }
}
