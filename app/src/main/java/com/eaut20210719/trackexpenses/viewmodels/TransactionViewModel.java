package com.eaut20210719.trackexpenses.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.eaut20210719.trackexpenses.database.entities.Transaction;
import com.eaut20210719.trackexpenses.repository.TransactionRepository;
import java.util.List;

// Trong TransactionViewModel.java
public class TransactionViewModel extends AndroidViewModel {
    private TransactionRepository mRepository;
    private LiveData<List<Transaction>> mAllTransactions;

    public TransactionViewModel(@NonNull Application application) {
        super(application);
        mRepository = new TransactionRepository(application);
        mAllTransactions = mRepository.getAllTransactions();
        logAllTransactionsOnce();
    }

    public void insert(Transaction transaction) {
        mRepository.insert(transaction);
    }

    // Phương thức lấy giao dịch cuối cùng
    public LiveData<Transaction> getLastTransaction() {
        return mRepository.getLastTransaction();
    }

    public void logAllTransactionsOnce() {
        if (mAllTransactions != null) {
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
        } else {
            Log.d("TransactionViewModel", "mAllTransactions là null");
        }
    }

}
