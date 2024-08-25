package com.eaut20210719.trackexpenses.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.eaut20210719.trackexpenses.database.entities.Transaction;
import com.eaut20210719.trackexpenses.dto.History;

import java.util.List;

// Trong TransactionDao.java
@Dao
public interface TransactionDao {
    @Insert
    void insert(Transaction transaction);

//    lấy giao dịch cuối cùng
    @Query("SELECT * FROM transactions ORDER BY id DESC LIMIT 1")
    LiveData<Transaction> getLastTransaction();

//    lấy số dư cuối cùng
    @Query("SELECT totalBalance FROM transactions ORDER BY id DESC LIMIT 1")
    LiveData<Double> getLastTotalBalance();

    @Query("SELECT * FROM transactions")
    LiveData<List<Transaction>> getAllTransactions();

    // Lấy tất cả lịch sử
    @Query("SELECT t.id, c.name AS nameCategory, t.content, t.date, ty.type_name AS typeName, t.amount " +
            "FROM transactions t " +
            "JOIN categories c ON t.categoryId = c.id " +
            "JOIN types ty ON t.typeId = ty.id " +
            "ORDER BY t.id DESC")
    LiveData<List<History>> getHistory(); // Sử dụng LiveData

    // Thêm phương thức tìm kiếm theo ngày gần đúng
    @Query("SELECT t.id, c.name AS nameCategory, t.content, t.date, ty.type_name AS typeName, t.amount " +
            "FROM transactions t " +
            "JOIN categories c ON t.categoryId = c.id " +
            "JOIN types ty ON t.typeId = ty.id " +
            "WHERE t.date LIKE '%' || :datePattern || '%' " +
            "ORDER BY t.id DESC")
    LiveData<List<History>> searchByDate(String datePattern);

    // Xóa bản ghi theo id
    @Query("DELETE FROM transactions WHERE id = :transactionId")
    void deleteTransactionById(int transactionId);

    // Cập nhật tổng tiền vào bản ghi mới nhất
    @Query("UPDATE transactions SET totalBalance = :newTotalBalance WHERE id = (SELECT id FROM transactions ORDER BY id DESC LIMIT 1)")
    void updateLatestTotalBalance(double newTotalBalance);

    // Query to get transaction by ID
    @Query("SELECT t.id, c.name AS nameCategory, t.content, t.date, ty.type_name AS typeName, t.amount " +
            "FROM transactions t " +
            "JOIN categories c ON t.categoryId = c.id " +
            "JOIN types ty ON t.typeId = ty.id " +
            "WHERE t.id = :transactionId")

    LiveData<History> getTransactionById(int transactionId);

    // Thêm phương thức lấy giao dịch theo tháng
    @Query("SELECT * FROM transactions WHERE strftime('%m', date) = :month")
    LiveData<List<Transaction>> getTransactionsByMonth(String month);

}
