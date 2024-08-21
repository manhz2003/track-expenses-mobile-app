package com.eaut20210719.trackexpenses.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

import com.eaut20210719.trackexpenses.database.entities.Transaction;

import java.util.List;

@Dao
public interface TransactionDao {

    // Thêm một giao dịch
    @Insert
    void insert(Transaction transaction);

    // Cập nhật một giao dịch
    @Update
    void update(Transaction transaction);

    // Xóa một giao dịch theo ID
    @Delete
    void delete(Transaction transaction);

    // Lấy tất cả các giao dịch
    @Query("SELECT * FROM transactions")
    List<Transaction> getAllTransactions();

    // Lấy một giao dịch theo ID
    @Query("SELECT * FROM transactions WHERE id = :id")
    Transaction getTransactionById(int id);

    // Lấy tất cả các giao dịch theo danh mục
    @Query("SELECT * FROM transactions WHERE id_category = :categoryId")
    List<Transaction> getTransactionsByCategoryId(int categoryId);

    // Lấy tất cả các giao dịch theo ngày
    @Query("SELECT * FROM transactions WHERE date = :date")
    List<Transaction> getTransactionsByDate(String date);

    // Xóa tất cả các giao dịch theo danh mục
    @Query("DELETE FROM transactions WHERE id_category = :categoryId")
    void deleteTransactionsByCategoryId(int categoryId);

    // Xóa tất cả các giao dịch theo ngày
    @Query("DELETE FROM transactions WHERE date = :date")
    void deleteTransactionsByDate(String date);
}
