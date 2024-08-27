package com.eaut20210719.trackexpenses.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.eaut20210719.trackexpenses.database.dao.CategoryDao;
import com.eaut20210719.trackexpenses.database.dao.ColorDao;
import com.eaut20210719.trackexpenses.database.dao.DailyLimitDao;
import com.eaut20210719.trackexpenses.database.dao.MonthlyLimitDao;
import com.eaut20210719.trackexpenses.database.dao.SettingDao;
import com.eaut20210719.trackexpenses.database.dao.TransactionDao;
import com.eaut20210719.trackexpenses.database.dao.TypeDao;
import com.eaut20210719.trackexpenses.database.entities.Category;
import com.eaut20210719.trackexpenses.database.entities.Color;
import com.eaut20210719.trackexpenses.database.entities.DailyLimit;
import com.eaut20210719.trackexpenses.database.entities.MonthlyLimit;
import com.eaut20210719.trackexpenses.database.entities.Setting;
import com.eaut20210719.trackexpenses.database.entities.Transaction;
import com.eaut20210719.trackexpenses.database.entities.Type;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Category.class, Type.class, DailyLimit.class, MonthlyLimit.class, Color.class, Transaction.class, Setting.class}, version = 9)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;
    private static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(4);
    public abstract CategoryDao categoryDao();
    public abstract TypeDao typeDao();
    public abstract DailyLimitDao dailyLimitDao();
    public abstract MonthlyLimitDao monthlyLimitDao();
    public abstract ColorDao colorDao();
    public abstract TransactionDao transactionDao();
    public abstract SettingDao settingDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "app_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    public static ExecutorService getDatabaseWriteExecutor() {
        return databaseWriteExecutor;
    }
}
