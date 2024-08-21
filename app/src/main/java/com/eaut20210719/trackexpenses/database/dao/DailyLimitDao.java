package com.eaut20210719.trackexpenses.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.eaut20210719.trackexpenses.database.entities.DailyLimit;

import java.util.List;

@Dao
public interface DailyLimitDao {

    // Thêm một mục giới hạn hàng ngày
    @Insert
    void insert(DailyLimit dailyLimit);

    // Cập nhật một mục giới hạn hàng ngày
    @Update
    void update(DailyLimit dailyLimit);

    // Xóa một mục giới hạn hàng ngày
    @Query("DELETE FROM daily_limits WHERE id = :id")
    void deleteById(int id);

    // Lấy tất cả các mục giới hạn hàng ngày
    @Query("SELECT * FROM daily_limits")
    List<DailyLimit> getAllDailyLimits();

    // Lấy một mục giới hạn hàng ngày theo ID
    @Query("SELECT * FROM daily_limits WHERE id = :id")
    DailyLimit getDailyLimitById(int id);
}
