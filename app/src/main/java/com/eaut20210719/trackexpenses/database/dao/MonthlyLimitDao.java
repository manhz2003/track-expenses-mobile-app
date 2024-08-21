package com.eaut20210719.trackexpenses.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.eaut20210719.trackexpenses.database.entities.MonthlyLimit;

import java.util.List;

@Dao
public interface MonthlyLimitDao {

    // Thêm một mục giới hạn hàng tháng
    @Insert
    void insert(MonthlyLimit monthlyLimit);

    // Cập nhật một mục giới hạn hàng tháng
    @Update
    void update(MonthlyLimit monthlyLimit);

    // Xóa một mục giới hạn hàng tháng theo ID
    @Query("DELETE FROM monthly_limits WHERE id = :id")
    void deleteById(int id);

    // Lấy tất cả các mục giới hạn hàng tháng
    @Query("SELECT * FROM monthly_limits")
    List<MonthlyLimit> getAllMonthlyLimits();

    // Lấy một mục giới hạn hàng tháng theo ID
    @Query("SELECT * FROM monthly_limits WHERE id = :id")
    MonthlyLimit getMonthlyLimitById(int id);
}
