package com.example.farmfresh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.NumberPicker;

public class Apples extends AppCompatActivity {

    private NumberPicker picker;
    private String[] pickerVals;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apples);

        //Initialize NumberPicker
        picker = findViewById(R.id.quantitySelector);
        picker.setMinValue(0);
        picker.setMaxValue(20);
        pickerVals = new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20"};
        picker.setDisplayedValues(pickerVals);
    }

    public void back(View v) {    startActivity(new Intent(Apples.this, Category_Fruit.class));    }
}
