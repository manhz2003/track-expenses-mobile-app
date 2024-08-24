package com.eaut20210719.trackexpenses.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.eaut20210719.trackexpenses.database.AppDatabase;
import com.eaut20210719.trackexpenses.database.dao.ColorDao;
import com.eaut20210719.trackexpenses.database.entities.Color;
import com.eaut20210719.trackexpenses.database.entities.Setting;

import java.util.List;

public class ColorRepository {
    private final ColorDao colorDao;
    private final LiveData<List<Color>> allColors;

    public ColorRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application.getApplicationContext());
        colorDao = db.colorDao();
        allColors = colorDao.getAllColors();
    }

    public LiveData<List<Color>> getAllColors() {
        return allColors;
    }
}
