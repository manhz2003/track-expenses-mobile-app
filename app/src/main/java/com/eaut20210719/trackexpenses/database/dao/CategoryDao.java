package com.eaut20210719.trackexpenses.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.lifecycle.LiveData;
import androidx.room.Query;

import com.eaut20210719.trackexpenses.database.entities.Category;
import java.util.List;

@Dao
public interface CategoryDao {
    @Insert
    void insertCategory(Category category);

    @Query("SELECT * FROM categories")
    LiveData<List<Category>> getAllCategories();

    @Query("DELETE FROM categories WHERE name = :categoryName")
    void deleteCategoryByName(String categoryName);

    @Query("SELECT id FROM categories WHERE name = :categoryName")
    LiveData<Integer> getCategoryIdByName(String categoryName);


}
