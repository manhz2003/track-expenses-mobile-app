package com.eaut20210719.trackexpenses.viewmodels;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import com.eaut20210719.trackexpenses.database.entities.Category;
import com.eaut20210719.trackexpenses.repository.CategoryRepository;
import java.util.List;

public class CategoryViewModel extends AndroidViewModel {
    private final CategoryRepository repository;
    private final LiveData<List<Category>> allCategories;

    public CategoryViewModel(@NonNull Application application) {
        super(application);
        repository = new CategoryRepository(application);
        allCategories = repository.getAllCategories();
    }

    public LiveData<List<Category>> getAllCategories() {
        return allCategories;
    }

    public void insert(Category category) {
        repository.insert(category);
    }

    public void deleteCategoryByName(String categoryName) {
        repository.deleteCategoryByName(categoryName);
    }

    public LiveData<Integer> getCategoryIdByName(String categoryName) {
        MediatorLiveData<Integer> categoryIdLiveData = new MediatorLiveData<>();
        categoryIdLiveData.addSource(allCategories, categories -> {
            for (Category category : categories) {
                if (category.getName().equals(categoryName)) {
                    categoryIdLiveData.setValue(category.getId());
                    return;
                }
            }
            categoryIdLiveData.setValue(null); // No match found
        });
        return categoryIdLiveData;
    }
}
