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
        return typeDao.getAllTypes();
    }

    public boolean isTypeExists(String typeName) {
        return typeDao.isTypeExists(typeName);
    }

    public void insert(Type type) {
        executorService.execute(() -> typeDao.insertType(type));
    }

    // Phương thức để tìm ID của loại theo tên
    public LiveData<Integer> getTypeIdByName(String typeName) {
        return typeDao.getTypeIdByName(typeName);
    }

}
