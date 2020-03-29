package com.example.farmfresh;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    EditText etPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Replace characters entered in password and re-enter password fields with asterisks
        etPassword = findViewById(R.id.passwordLogin);
        etPassword.setTransformationMethod(new AsteriskPasswordTransformationMethod());
    }

    public void loginValidate(View v) {

    }
    public void back(View v) {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
    }
}