package com.example.farmfresh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button button;
    private Button ca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button=findViewById(R.id.search);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(MainActivity.this,search.class);
                startActivity(it);

            }
        });
        ca=findViewById(R.id.category);
        ca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(MainActivity.this,categorypage.class);
                startActivity(it);
            }
        });
    }

    public void loginBtnClick(View v) {
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
    }

    public void signUpBtnClick(View v) {
        startActivity(new Intent(MainActivity.this, BuyOrSellActivity.class));
    }
}
