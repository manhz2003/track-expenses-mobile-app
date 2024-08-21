package com.eaut20210719.trackexpenses.repository;

import android.content.Context;

import androidx.room.Room;

import com.eaut20210719.trackexpenses.database.AppDatabase;
import com.eaut20210719.trackexpenses.database.dao.TypeDao;
import com.eaut20210719.trackexpenses.database.entities.Type;

import java.util.List;

public class TypeRepository {
    private final TypeDao typeDao;

    public TypeRepository(Context context, TypeDao typeDao) {
        this.typeDao = typeDao;
        AppDatabase database = Room.databaseBuilder(context, AppDatabase.class, "trackexpenses").build();
    }

    public void insert(Type type) {
        typeDao.insert(type);
    }

    public void update(Type type) {
        typeDao.update(type);
    }

    public void delete(Type type) {
        typeDao.delete(type);
    }

    public List<Type> getAllTypes() {
        return typeDao.getAllTypes();
    }

    public Type getTypeById(int id) {
        return typeDao.getTypeById(id);
    }
}
