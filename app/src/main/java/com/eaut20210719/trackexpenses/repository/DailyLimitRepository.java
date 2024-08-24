package com.eaut20210719.trackexpenses.repository;

import android.app.Application;

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
}
