package com.eaut20210719.trackexpenses.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.eaut20210719.trackexpenses.database.entities.Transaction;
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
}
