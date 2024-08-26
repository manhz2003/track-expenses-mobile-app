package com.eaut20210719.trackexpenses.viewmodels;

import android.app.Application;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import com.eaut20210719.trackexpenses.database.entities.Type;
import com.eaut20210719.trackexpenses.repository.TypeRepository;
import java.util.List;

public class TypeViewModel extends AndroidViewModel {
    private final TypeRepository repository;
    private final LiveData<List<Type>> allTypes;
    private static final String TAG = "TypeViewModel";

    public TypeViewModel(@NonNull Application application) {
        super(application);
        repository = new TypeRepository(application);
        allTypes = repository.getAllTypes();
        insertSampleData();
        logAllTypes();
    }

    private void insertSampleData() {
        insertTypeIfNotExists(new Type(1, "Tiền chi"));
        insertTypeIfNotExists(new Type(2, "Cho vay"));
        insertTypeIfNotExists(new Type(3, "Thu nhập"));
    }

    private void insertTypeIfNotExists(Type type) {
        new Thread(() -> {
            if (!repository.isTypeExists(type.getType_name())) {
                repository.insert(type);
            } else {
                Log.d(TAG, "insertTypeIfNotExists: Type already exists - " + type.getType_name());
            }
        }).start();
    }

    public LiveData<List<Type>> getAllTypes() {
        return allTypes;
    }

    public LiveData<Integer> getTypeIdByName(String typeName) {
        MediatorLiveData<Integer> typeIdLiveData = new MediatorLiveData<>();
        typeIdLiveData.addSource(allTypes, types -> {
            for (Type type : types) {
                if (type.getType_name().equals(typeName)) {
                    Log.d(TAG, "getTypeIdByName: Found type ID - " + type.getId() + " for type name - " + typeName);
                    typeIdLiveData.setValue(type.getId());
                    return;
                }
            }
            Log.d(TAG, "getTypeIdByName: No match found for type name - " + typeName);
            typeIdLiveData.setValue(null);
        });
        return typeIdLiveData;
    }

    // Phương thức mới để log toàn bộ dữ liệu bảng types
    public void logAllTypes() {
        allTypes.observeForever(new Observer<List<Type>>() {
            @Override
            public void onChanged(List<Type> types) {
                for (Type type : types) {
                    Log.d("Data bảng type: ", "Type ID: " + type.getId() + ", Type Name: " + type.getType_name());
                }
            }
        });
    }
}
