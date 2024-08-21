package com.eaut20210719.trackexpenses.database.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "transactions",
        foreignKeys = {
                @ForeignKey(
                        entity = Type.class,
                        parentColumns = "id",
                        childColumns = "id_type",
                        onDelete = ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity = Category.class,
                        parentColumns = "id",
                        childColumns = "id_category",
                        onDelete = ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity = DailyLimit.class,
                        parentColumns = "id",
                        childColumns = "id_daily_limit",
                        onDelete = ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity = MonthlyLimit.class,
                        parentColumns = "id",
                        childColumns = "id_monthly_limit",
                        onDelete = ForeignKey.CASCADE
                )
        }
)
public class Transaction {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public double amount;
    public int id_type;           // Tham chiếu đến bảng Type
    public String content;
    public String date;
    public int id_category;       // Tham chiếu đến bảng Category
    public double total_balance;
    public int id_daily_limit;    // Tham chiếu đến bảng DailyLimit
    public int id_monthly_limit;  // Tham chiếu đến bảng MonthlyLimit

    public Transaction(double amount, int id_type, String content, String date, int id_category, double total_balance, int id_daily_limit, int id_monthly_limit) {
        this.amount = amount;
        this.id_type = id_type;
        this.content = content;
        this.date = date;
        this.id_category = id_category;
        this.total_balance = total_balance;
        this.id_daily_limit = id_daily_limit;
        this.id_monthly_limit = id_monthly_limit;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getIdType() {
        return id_type;
    }

    public void setIdType(int id_type) {
        this.id_type = id_type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getIdCategory() {
        return id_category;
    }

    public void setIdCategory(int id_category) {
        this.id_category = id_category;
    }

    public double getTotalBalance() {
        return total_balance;
    }

    public void setTotalBalance(double total_balance) {
        this.total_balance = total_balance;
    }

    public int getIdDailyLimit() {
        return id_daily_limit;
    }

    public void setIdDailyLimit(int id_daily_limit) {
        this.id_daily_limit = id_daily_limit;
    }

    public int getIdMonthlyLimit() {
        return id_monthly_limit;
    }

    public void setIdMonthlyLimit(int id_monthly_limit) {
        this.id_monthly_limit = id_monthly_limit;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", amount=" + amount +
                ", id_type=" + id_type +
                ", content='" + content + '\'' +
                ", date='" + date + '\'' +
                ", id_category=" + id_category +
                ", total_balance=" + total_balance +
                ", id_daily_limit=" + id_daily_limit +
                ", id_monthly_limit=" + id_monthly_limit +
                '}';
    }
}
