//package com.eaut20210719.trackexpenses.ui.activities;
//
//import android.os.Bundle;
//import android.view.View;
//import android.widget.TextView;
//
//import androidx.activity.EdgeToEdge;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.eaut20210719.trackexpenses.R;
//import com.eaut20210719.trackexpenses.databinding.HistoryFragmentBinding;
//import com.eaut20210719.trackexpenses.ui.fragments.AddFragment;
//import com.eaut20210719.trackexpenses.ui.fragments.HistoryFragment;
//
//public class HistoryActivity extends AppCompatActivity {
//    private HistoryFragmentBinding binding; // Thay đổi thành ActivityHistoryBinding
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        binding = HistoryFragmentBinding.inflate(getLayoutInflater()); // Sử dụng đúng binding
//
//        setContentView(binding.getRoot());
//
//        // Hiển thị HistoryFragment mặc định
//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.frHistory, new HistoryFragment()) // Thay đổi ID thành frHistory
//                .commit();
//
//        // Tìm tvAdd trong HistoryActivity
//        TextView tvAdd = findViewById(R.id.tvAdd);
//
//        // Gán sự kiện nhấn cho tvAdd trong HistoryActivity
//        tvAdd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Chuyển đến AddFragment
//                getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.frHistory, new AddFragment()) // Thay đổi ID thành frHistory
//                        .addToBackStack(null) // Thêm vào back stack nếu cần
//                        .commit();
//            }
//        });
//    }
//}
