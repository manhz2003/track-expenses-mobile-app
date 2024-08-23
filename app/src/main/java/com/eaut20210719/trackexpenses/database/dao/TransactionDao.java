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

    @Query("SELECT * FROM transactions ORDER BY id DESC LIMIT 1")
    LiveData<Transaction> getLastTransaction();

    @Query("SELECT * FROM transactions")
    LiveData<List<Transaction>> getAllTransactions();
}
