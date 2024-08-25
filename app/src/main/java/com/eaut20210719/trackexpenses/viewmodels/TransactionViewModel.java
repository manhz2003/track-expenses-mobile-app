package com.eaut20210719.trackexpenses.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import com.eaut20210719.trackexpenses.database.entities.Transaction;
import com.eaut20210719.trackexpenses.dto.History;
import com.eaut20210719.trackexpenses.repository.TransactionRepository;
import java.util.List;

public class TransactionViewModel extends AndroidViewModel {
    private TransactionRepository mRepository;
    private LiveData<List<Transaction>> mAllTransactions;
    private LiveData<Double> mTotalBalance;

    public TransactionViewModel(@NonNull Application application) {
        super(application);
        mRepository = new TransactionRepository(application);
        mAllTransactions = mRepository.getAllTransactions();
        mTotalBalance = mRepository.getLastTotalBalance();
    }

    public void insert(Transaction transaction) {
        mRepository.insert(transaction);
    }

    public LiveData<Double> getTotalBalance() {
        return mTotalBalance;
    }

    public LiveData<Transaction> getLastTransaction() {
        return mRepository.getLastTransaction();
    }

    // Phương thức lấy lịch sử
    private LiveData<List<History>> historyList;
    public LiveData<List<History>> getHistoryList() {
        if (historyList == null) {
            historyList = mRepository.getHistory(); // Gọi từ repository
        }
        return historyList;
    }

    public void logAllTransactionsOnce() {
        mAllTransactions.observeForever(new Observer<List<Transaction>>() {
            @Override
            public void onChanged(List<Transaction> transactions) {
                if (transactions != null) {
                    for (Transaction transaction : transactions) {
                        Log.d("kết quả bảng Transaction: ", transaction.toString());
                    }
                } else {
                    Log.d("TransactionLog", "không có dữ liệu");
                }
                // Hủy quan sát sau khi log xong
                mAllTransactions.removeObserver(this);
            }
        });
    }

    // Tìm kiếm lịch sử
    public LiveData<List<History>> searchByDate(String datePattern) {
        Log.d("TransactionViewModel", "Searching for date: " + datePattern);
        LiveData<List<History>> result = mRepository.searchByDate(datePattern);
        // Observe the result to log the fetched data
        result.observeForever(histories -> {
            Log.d("TransactionViewModel", "Search results: " + histories);
        });
        return result;
    }

    // Thêm phương thức để xóa giao dịch
    public void deleteTransactionById(int transactionId) {
        mRepository.deleteTransactionById(transactionId);
    }

    // Thêm phương thức để cập nhật tổng tiền
    public void updateTotalAmount(double newTotalAmount) {
        mRepository.updateTotalAmount(newTotalAmount);
    }

    // Method to get a transaction by ID
    public LiveData<History> getTransactionById(int transactionId) {
        LiveData<History> result = mRepository.getTransactionById(transactionId);
        Log.d("result", result.toString());
        return  result;
    }
}
