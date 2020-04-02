package com.example.farmfresh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class BuyOrSellActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_or_sell);
    }

    public void back(View v) {
        startActivity(new Intent(BuyOrSellActivity.this, MainActivity.class));
    }
    public void buying(View v) {
        Intent intent = new Intent(BuyOrSellActivity.this, SignUp.class);
        intent.putExtra("mode", "buyer");
        startActivity(intent);
    }
    public void selling(View v) {
        Intent intent = new Intent(BuyOrSellActivity.this, SignUp.class);
        intent.putExtra("mode", "seller");
        startActivity(intent);
    }
}