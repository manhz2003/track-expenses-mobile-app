package com.eaut20210719.trackexpenses.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.eaut20210719.trackexpenses.database.entities.Color;

import java.util.List;

@Dao
public interface ColorDao {
    @Insert
    void insert(Color color);

    @Query("SELECT * FROM colors")
    List<Color> getAllColors();

    // Thêm các phương thức truy vấn khác nếu cần
}
