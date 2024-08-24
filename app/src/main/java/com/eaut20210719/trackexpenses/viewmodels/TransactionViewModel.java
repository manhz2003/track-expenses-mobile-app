package com.eaut20210719.trackexpenses.viewmodels;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import com.eaut20210719.trackexpenses.database.entities.Transaction;
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
}
