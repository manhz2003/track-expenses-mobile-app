package com.eaut20210719.trackexpenses;

import android.app.Application;

import com.eaut20210719.trackexpenses.database.AppDatabase;

public class TrackExpensesApplication extends Application {
    private static AppDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        database = AppDatabase.getInstance(this);
    }

    public static AppDatabase getDatabase() {
        if (database == null) {
            throw new IllegalStateException("Database has not been initialized yet.");
        }
        return database;
    }
}
