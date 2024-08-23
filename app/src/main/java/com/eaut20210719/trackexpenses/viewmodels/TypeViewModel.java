package com.eaut20210719.trackexpenses.viewmodels;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import com.eaut20210719.trackexpenses.database.entities.Type;
import com.eaut20210719.trackexpenses.repository.TypeRepository;
import java.util.List;

public class TypeViewModel extends AndroidViewModel {
    private final TypeRepository repository;
    private final LiveData<List<Type>> allTypes;

    public TypeViewModel(@NonNull Application application) {
        super(application);
        repository = new TypeRepository(application);
        allTypes = repository.getAllTypes();

        // Chèn dữ liệu mẫu vào cơ sở dữ liệu nếu chưa tồn tại
        insertSampleData();
    }

    private void insertSampleData() {
        insertTypeIfNotExists(new Type("Tiền chi"));
        insertTypeIfNotExists(new Type("Thu nhập"));
        insertTypeIfNotExists(new Type("Cho vay"));
    }

    private void insertTypeIfNotExists(Type type) {
        new Thread(() -> {
            if (!repository.isTypeExists(type.getType_name())) {
                repository.insert(type);
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
                    typeIdLiveData.setValue(type.getId());
                    return;
                }
            }
            typeIdLiveData.setValue(null); // No match found
        });
        return typeIdLiveData;
    }
}
