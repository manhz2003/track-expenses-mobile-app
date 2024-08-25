package com.eaut20210719.trackexpenses.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import com.eaut20210719.trackexpenses.database.AppDatabase;
import com.eaut20210719.trackexpenses.database.dao.TransactionDao;
import com.eaut20210719.trackexpenses.database.entities.Transaction;
import com.eaut20210719.trackexpenses.dto.History;

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

    // Phương thức lấy lịch sử
    // Cập nhật phương thức này để trả về LiveData
    public LiveData<List<History>> getHistory() {
        return mTransactionDao.getHistory(); // Phải cập nhật câu truy vấn trong DAO để trả về LiveData
    }

    // Tìm kiếm lịch sử
    public LiveData<List<History>> searchByDate(String datePattern) {
        Log.d("TransactionRepository", "Searching for date: " + datePattern);
        return mTransactionDao.searchByDate(datePattern);
    }

    // Thêm phương thức xóa giao dịch
    public void deleteTransactionById(int transactionId) {
        AppDatabase.getDatabaseWriteExecutor().execute(() -> {
            mTransactionDao.deleteTransactionById(transactionId);
        });
    }

    // Thêm phương thức cập nhật tổng tiền
    public void updateTotalAmount(double newTotalAmount) {
        AppDatabase.getDatabaseWriteExecutor().execute(() -> {
            mTransactionDao.updateLatestTotalBalance(newTotalAmount);
        });
    }

    // Method to get a transaction by ID
    public LiveData<History> getTransactionById(int transactionId) {
        LiveData<History> result = mTransactionDao.getTransactionById(transactionId);
        Log.d("result", result.toString());
        return  result;
    }

}
