package com.eaut20210719.trackexpenses.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.eaut20210719.trackexpenses.database.entities.DailyLimit;

import java.util.List;

@Dao
public interface DailyLimitDao {

    @Insert
    void insertDailyLimit(DailyLimit dailyLimit);

    @Update
    void updateDailyLimit(DailyLimit dailyLimit);

    @Query("DELETE FROM daily_limits")
    void deleteAllDailyLimits();

//    lấy tất cả dữ liệu bảng daily_limits
    @Query("SELECT * FROM daily_limits")
    LiveData<List<DailyLimit>> getAllDailyLimits();

}
