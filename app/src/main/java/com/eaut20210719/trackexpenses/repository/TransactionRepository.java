package com.eaut20210719.trackexpenses.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.eaut20210719.trackexpenses.database.AppDatabase;
import com.eaut20210719.trackexpenses.database.dao.TransactionDao;
import com.eaut20210719.trackexpenses.database.entities.Transaction;
import java.util.List;

public class TransactionRepository {
    private TransactionDao mTransactionDao;
    private LiveData<List<Transaction>> mAllTransactions;
    private LiveData<Double> mLastTotalBalance;

    public TransactionRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        mTransactionDao = db.transactionDao();
        mAllTransactions = mTransactionDao.getAllTransactions();
        mLastTotalBalance = mTransactionDao.getLastTotalBalance();
    }

    public LiveData<List<Transaction>> getAllTransactions() {
        return mAllTransactions;
    }

    public LiveData<Transaction> getLastTransaction() {
        return mTransactionDao.getLastTransaction();
    }

    public void insert(Transaction transaction) {
        AppDatabase.getDatabaseWriteExecutor().execute(() -> {
            mTransactionDao.insert(transaction);
        });
    }

//    lấy tổng số dư cuối cùng
    public LiveData<Double> getLastTotalBalance() {
        return mLastTotalBalance;
    }

}
