package com.eaut20210719.trackexpenses.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "types")
public class Type {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "type_name")
    public String type_name;
    public Type(String type_name) {
        this.type_name = type_name;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getType_name() {
        return type_name;
    }
    public void setType_name(String type_name) {
        this.type_name = type_name;
    }
    @Override
    public String toString() {
        return type_name;
    }
}
