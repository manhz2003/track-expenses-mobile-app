package com.eaut20210719.trackexpenses.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.eaut20210719.trackexpenses.database.AppDatabase;
import com.eaut20210719.trackexpenses.database.dao.MonthlyLimitDao;
import com.eaut20210719.trackexpenses.database.dao.SettingDao;
import com.eaut20210719.trackexpenses.database.entities.MonthlyLimit;
import com.eaut20210719.trackexpenses.database.entities.Setting;

import java.util.List;

public class SettingRepository {
    private final SettingDao settingDao;
    private final LiveData<List<Setting>> allSettings;

    public SettingRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application.getApplicationContext());
        settingDao = db.settingDao();
        allSettings = settingDao.getAllSettings();
    }

    public LiveData<List<Setting>> getAllSettings() {
        return allSettings;
    }
}
