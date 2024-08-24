package com.eaut20210719.trackexpenses.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.eaut20210719.trackexpenses.database.entities.MonthlyLimit;
import com.eaut20210719.trackexpenses.database.entities.Setting;
import com.eaut20210719.trackexpenses.repository.MonthlyLimitRepository;
import com.eaut20210719.trackexpenses.repository.SettingRepository;

import java.util.List;

public class SettingViewModel extends AndroidViewModel {
    private final SettingRepository repository;
    private final String TAG = "SettingViewModel";
    private final LiveData<List<Setting>> allSettings;

    public SettingViewModel(@NonNull Application application) {
        super(application);
        repository = new SettingRepository(application);
        allSettings = repository.getAllSettings();
        logAllSettings();
    }

//  Phương thức mới để log toàn bộ dữ liệu bảng settings
    public void logAllSettings() {
        allSettings.observeForever(settings -> {
            if (settings == null || settings.isEmpty()) {
                System.out.println("Không có dữ liệu trong bảng settings");
            } else {
                for (Setting setting : settings) {
                    System.out.println("data bảng setting: " + "Setting ID: " + setting.getId() + ", Cỡ chữ: " + setting.getZise_text() + ", id color: " + setting.getId_color());
                }
            }
        });
    }


}
