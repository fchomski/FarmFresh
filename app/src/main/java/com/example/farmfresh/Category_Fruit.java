package com.example.farmfresh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.farmfresh.ui.search.SearchFragment;

public class Category_Fruit extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category__fruit);
    }

    public void back(View v) {
        startActivity(new Intent(Category_Fruit.this, SearchFragment.class));
    }
}
