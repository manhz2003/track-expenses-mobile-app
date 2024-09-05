package com.eaut20210719.trackexpenses.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.eaut20210719.trackexpenses.database.AppDatabase;
import com.eaut20210719.trackexpenses.database.dao.TypeDao;
import com.eaut20210719.trackexpenses.database.entities.Type;

import java.util.List;
import java.util.concurrent.ExecutorService;

public class TypeRepository {
    private final TypeDao typeDao;
    private final ExecutorService executorService;

    public TypeRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        typeDao = db.typeDao();
        executorService = AppDatabase.getDatabaseWriteExecutor();
    }

    public LiveData<List<Type>> getAllTypes() {
        LiveData<List<Type>> allTypes = typeDao.getAllTypes();
        if (allTypes == null) {
            System.err.println("TypeRepository: No types data available");
        }
        return allTypes;
    }

    public boolean isTypeExists(String typeName) {
        if (typeName != null && !typeName.trim().isEmpty()) {
            return typeDao.isTypeExists(typeName);
        } else {
            throw new IllegalArgumentException("Type name must not be null or empty");
        }
    }

    public void insert(Type type) {
        if (type != null) {
            executorService.execute(() -> typeDao.insertType(type));
        } else {
            throw new IllegalArgumentException("Type must not be null");
        }
    }
}
