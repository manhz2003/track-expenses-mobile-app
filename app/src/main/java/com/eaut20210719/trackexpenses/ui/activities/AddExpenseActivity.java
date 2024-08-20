package com.eaut20210719.trackexpenses.ui.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.eaut20210719.trackexpenses.R;

public class AddExpenseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_fragment); // Đảm bảo layout này là layout bạn đã cung cấp
    }
}
