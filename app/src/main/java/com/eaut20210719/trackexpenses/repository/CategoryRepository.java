package com.eaut20210719.trackexpenses.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.eaut20210719.trackexpenses.database.AppDatabase;
import com.eaut20210719.trackexpenses.database.dao.CategoryDao;
import com.eaut20210719.trackexpenses.database.entities.Category;
import java.util.List;

public class CategoryRepository {
    private final CategoryDao categoryDao;
    private final LiveData<List<Category>> allCategories;

    public CategoryRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application.getApplicationContext());
        categoryDao = db.categoryDao();
        allCategories = categoryDao.getAllCategories();
    }

    public LiveData<List<Category>> getAllCategories() {
        return allCategories;
    }

    public void insert(Category category) {
        if (category == null) {
            throw new IllegalArgumentException("Category must not be null");
        }
        AppDatabase.getDatabaseWriteExecutor().execute(() -> categoryDao.insertCategory(category));
    }

    public void deleteCategoryByName(String categoryName) {
        if (categoryName == null || categoryName.isEmpty()) {
            throw new IllegalArgumentException("Category name must not be null or empty");
        }
        AppDatabase.getDatabaseWriteExecutor().execute(() -> categoryDao.deleteCategoryByName(categoryName));
    }
}
