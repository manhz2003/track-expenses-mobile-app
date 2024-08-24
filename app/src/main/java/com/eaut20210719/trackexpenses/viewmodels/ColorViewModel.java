package com.eaut20210719.trackexpenses.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.eaut20210719.trackexpenses.database.entities.Color;
import com.eaut20210719.trackexpenses.database.entities.MonthlyLimit;
import com.eaut20210719.trackexpenses.repository.ColorRepository;

import java.util.List;

public class ColorViewModel extends AndroidViewModel {
    private final ColorRepository repository;
    private final LiveData<List<Color>> allColors;
    private static final String TAG = "ColorViewModel";

    public ColorViewModel(@NonNull Application application) {
        super(application);
        repository = new ColorRepository(application);
        allColors = repository.getAllColors();
        logAllColors();
    }

//    Phương thức mới để log toàn bộ dữ liệu bảng colors
    public void logAllColors() {
        allColors.observeForever(colors -> {
            if (colors == null || colors.isEmpty()) {
                System.out.println("Không có dữ liệu trong bảng colors");
            } else {
                for (Color color : colors) {
                    System.out.println("data bảng color: " + "Color ID: " + color.getId() + ", Color Name: " + color.getName_color());
                }
            }
        });
    }

    public LiveData<List<Color>> getAllColors() {
        return allColors;
    }

}
