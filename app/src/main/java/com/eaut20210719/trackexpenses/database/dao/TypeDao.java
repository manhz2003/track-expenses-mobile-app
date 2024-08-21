package com.eaut20210719.trackexpenses.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.eaut20210719.trackexpenses.database.entities.Type;

import java.util.List;

@Dao
public interface TypeDao {
    @Insert
    void insert(Type type);

    @Update
    void update(Type type);

    @Delete
    void delete(Type type);

    @Query("SELECT * FROM types")
    List<Type> getAllTypes();

    @Query("SELECT * FROM types WHERE id = :id LIMIT 1")
    Type getTypeById(int id);
}
