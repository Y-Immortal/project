package edu.wschina.a03;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import edu.wschina.a03.Adapter.ItemAdapter;
import edu.wschina.a03.Bean.Item;
import edu.wschina.a03.databinding.ActivityMainBinding;

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

        binding.filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, FilterActivity.class));
            }
        });

        List<Item> items = new ArrayList<>();
        items.add(new Item("Grand Royal Hotel", "2.0", "100"));
        items.add(new Item("Grand Royal Hotel", "2.0", "80"));
        items.add(new Item("Grand Royal Hotel", "2.0", "170"));
        items.add(new Item("Grand Royal Hotel", "2.0", "200"));
        items.add(new Item("Grand Royal Hotel", "2.0", "100"));

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.recycler.setLayoutManager(layoutManager);

        binding.recycler.setAdapter(new ItemAdapter(items));

        binding.recycler.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    binding.stickyHeader.setVisibility(View.GONE);
                }else if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    binding.stickyHeader.setVisibility(View.VISIBLE);
                }

            }

        });
        binding.data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    new DateDialog().show(getSupportFragmentManager(), "DateDialog");
                }

            }
        });

    }

}