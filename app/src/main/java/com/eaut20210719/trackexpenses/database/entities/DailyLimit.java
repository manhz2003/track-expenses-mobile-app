package com.eaut20210719.trackexpenses.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "daily_limits")
public class DailyLimit {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public double money_day;

    public DailyLimit(double money_day) {
        this.money_day = money_day;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getMoney_day() {
        return money_day;
    }

    public void setMoney_day(double money_day) {
        this.money_day = money_day;
    }

    @Override
    public String toString() {
        return "DailyLimit{" +
                "id=" + id +
                ", money_day=" + money_day +
                '}';
    }

}
