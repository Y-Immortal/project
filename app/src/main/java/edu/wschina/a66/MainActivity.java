package edu.wschina.a66;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowInsetsController;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

import edu.wschina.a66.Adapter.ImageAdapter;
import edu.wschina.a66.Bean.Image;
import edu.wschina.a66.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            WindowInsetsController windowInsetsController = getWindow().getInsetsController();
            if (windowInsetsController != null) {

                windowInsetsController.setSystemBarsAppearance(
                        WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                        WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS);

                getWindow().setStatusBarColor(Color.TRANSPARENT);
            }
        }

        //白色导航栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setNavigationBarColor(getResources().getColor(R.color.white));

            if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1)) {
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
            }
        }

        List<Image> images = new ArrayList<>();
        images.add(new Image("Grand Royal Hotel","2.0km to city","$180","80 Reviews",R.drawable.item2));
        images.add(new Image("Sidney Hotel","7.0km to city","$170","90 Reviews",R.drawable.item2));
        images.add(new Image("Waterloo Hotel","2.0km to city","$200","240 Reviews",R.drawable.item2));
        images.add(new Image("Grand Royal Hotel","2.0km to city","$180","80 Reviews",R.drawable.item2));
        images.add(new Image("Grand Royal Hotel","2.0km to city","$180","80 Reviews",R.drawable.item2));

        binding.recycler.setLayoutManager(new LinearLayoutManager(this));

        binding.recycler.setAdapter(new ImageAdapter(images));

        binding.filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, FilterActivity.class));
            }
        });

        binding.date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Dialog dialog = new Dialog(MainActivity.this);

                dialog.setContentView(R.layout.item_dialog);

                dialog.show();

            }
        });

    }
}