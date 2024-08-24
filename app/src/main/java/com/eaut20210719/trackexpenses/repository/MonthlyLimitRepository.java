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

}
