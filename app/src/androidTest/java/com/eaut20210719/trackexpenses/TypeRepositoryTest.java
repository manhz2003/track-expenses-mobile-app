package com.eaut20210719.trackexpenses;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import com.eaut20210719.trackexpenses.database.AppDatabase;
import com.eaut20210719.trackexpenses.database.entities.Type;
import com.eaut20210719.trackexpenses.repository.TypeRepository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.List;

import static org.junit.Assert.*;

public class TypeRepositoryTest {
    private TypeRepository typeRepository;
    private AppDatabase db;

    @Before
    public void setUp() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase.class)
                .allowMainThreadQueries()
                .build();
        typeRepository = new TypeRepository(context);
    }

    @After
    public void tearDown() {
        db.close();
    }

    @Test
    public void testInsertAndRetrieve() {
        Type type = new Type();
        type.type_name = "Expense";
        typeRepository.insert(type);

        List<Type> types = typeRepository.getAllTypes();
        assertNotNull(types);
        assertEquals(1, types.size());
        assertEquals("Expense", types.get(0).type_name);
    }

    @Test
    public void testUpdate() {
        Type type = new Type();
        type.type_name = "Income";
        typeRepository.insert(type);

        List<Type> types = typeRepository.getAllTypes();
        Type insertedType = types.get(0);
        insertedType.type_name = "Updated Income";
        typeRepository.update(insertedType);

        Type updatedType = typeRepository.getTypeById(insertedType.id);
        assertEquals("Updated Income", updatedType.type_name);
    }

    @Test
    public void testDelete() {
        Type type = new Type();
        type.type_name = "Income";
        typeRepository.insert(type);

        List<Type> types = typeRepository.getAllTypes();
        Type insertedType = types.get(0);
        typeRepository.delete(insertedType);

        Type deletedType = typeRepository.getTypeById(insertedType.id);
        assertNull(deletedType);
    }
}
