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
        startActivity(new Intent(BuyOrSellActivity.this, SignUpBuyers.class));
    }
    public void selling(View v) {

    }
}
