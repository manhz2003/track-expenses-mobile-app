package com.eaut20210719.trackexpenses.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.eaut20210719.trackexpenses.database.entities.MonthlyLimit;

import java.util.List;

@Dao
public interface MonthlyLimitDao {

    @Insert
    void insertMonthlyLimit(MonthlyLimit monthlyLimit);

    @Update
    void updateMonthlyLimit(MonthlyLimit monthlyLimit);

    @Query("DELETE FROM monthly_limits")
    void deleteAllMonthlyLimits();

//    lấy tất cả dữ liệu bảng monthly_limits
    @Query("SELECT * FROM monthly_limits")
    LiveData<List<MonthlyLimit>> getAllMonthlyLimits();
}
