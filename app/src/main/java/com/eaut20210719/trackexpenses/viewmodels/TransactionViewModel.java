package com.eaut20210719.trackexpenses.viewmodels;// TransactionViewModel.java
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
        mTotalBalance = mRepository.getLastTotalBalance(); // Giả sử phương thức này cũng có trong repository
    }

    public void insert(Transaction transaction) {
        mRepository.insert(transaction);
    }

    public LiveData<Double> getTotalBalance() {
        return mTotalBalance;
    }

    public LiveData<Transaction> getLastTransaction() {
        return mRepository.getLastTransaction(); // Giả sử phương thức này cũng có trong repository
    }

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
                mAllTransactions.removeObserver(this);
            }
        });
    }

    public LiveData<List<History>> searchByDate(String datePattern) {
        Log.d("TransactionViewModel", "Searching for date: " + datePattern);
        LiveData<List<History>> result = mRepository.searchByDate(datePattern);
        result.observeForever(histories -> {
            Log.d("TransactionViewModel", "Search results: " + histories);
        });
        return result;
    }

    public void deleteTransactionById(int transactionId) {
        mRepository.deleteTransactionById(transactionId);
    }

    public void updateTotalAmount(double newTotalAmount) {
        mRepository.updateTotalAmount(newTotalAmount);
    }

    public LiveData<History> getTransactionById(int transactionId) {
        return mRepository.getTransactionById(transactionId);
    }

    public LiveData<List<Transaction>> getTransactionsByMonth(String month) {
        return mRepository.getTransactionsByMonth(month);
    }

    public LiveData<List<Transaction>> getAllTransactions() {
        return mRepository.getAllTransactions();
    }
}
