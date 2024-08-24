package com.eaut20210719.trackexpenses.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.eaut20210719.trackexpenses.database.entities.Color;

import java.util.List;

@Dao
public interface ColorDao {
    @Insert
    void insert(Color color);

//    lấy tất cả dữ liệu bảng colors
    @Query("SELECT * FROM colors")
    LiveData<List<Color>> getAllColors();

}
