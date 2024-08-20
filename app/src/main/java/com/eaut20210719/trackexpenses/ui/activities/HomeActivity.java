package com.eaut20210719.trackexpenses.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.eaut20210719.trackexpenses.R;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_fragment); // Đảm bảo rằng layout này có TextView mà bạn muốn xử lý

        // Tìm TextView theo ID
        TextView tvAdd = findViewById(R.id.tvAdd);

        // Gán sự kiện OnClickListener cho TextView
        tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển sang AddActivity khi nhấn vào TextView
                Intent intent = new Intent(HomeActivity.this, AddExpenseActivity.class);
                startActivity(intent);
            }
        });
    }
}
