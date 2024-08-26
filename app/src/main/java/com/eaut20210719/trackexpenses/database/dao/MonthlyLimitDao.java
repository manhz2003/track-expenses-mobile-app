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
    void deleteAll();

    @Query("SELECT * FROM monthly_limits")
    LiveData<List<MonthlyLimit>> getAllMonthlyLimits();

    @Query("SELECT COUNT(*) FROM monthly_limits")
    int getMonthlyLimitCount();

    @Query("SELECT MAX(id) FROM monthly_limits")
    Integer getLastMonthlyLimitId();

    @Query("SELECT money_month FROM monthly_limits ORDER BY id DESC LIMIT 1")
    LiveData<Double> getLastMonthlyLimitMoney();

    // Cập nhật giá trị money_month_setting
    @Query("UPDATE monthly_limits SET money_month_setting = :moneyMonthSetting WHERE id = (SELECT MAX(id) FROM monthly_limits)")
    void updateMoneyMonthSetting(double moneyMonthSetting);

    //  lấy id cuối cùng
    @Query("SELECT MAX(id) FROM monthly_limits")
    LiveData<Integer> getLastMonthlyLimitID();
}