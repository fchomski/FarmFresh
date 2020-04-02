package com.example.farmfresh;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.arch.core.util.Function;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.farmfresh.model.data.Connect;
import com.example.farmfresh.model.data.Key;
import com.example.farmfresh.model.data.User;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    EditText etUsername;
    EditText etPassword;
    String toasterMsg;
    Toast toast;

    @SuppressLint("ShowToast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Replace characters entered in password and re-enter password fields with asterisks
        etPassword = findViewById(R.id.passwordLogin);
        etPassword.setTransformationMethod(new AsteriskPasswordTransformationMethod());

        etUsername = findViewById(R.id.usernameLogin);

        toasterMsg = "Username or password error";
        toast = Toast.makeText(this, toasterMsg, Toast.LENGTH_SHORT);
        toast.setMargin(50, 50);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void loginValidate(View v) {
        //TODO: Validate inputs
        //Go to homepage
        try {
            Connect c = new Connect(getApplicationContext());
            ArrayList<User> res = c.filter(Key.User.USER_NAME, new Function<Object, Boolean>() {
                @Override
                public Boolean apply(Object e) {
                    return e.toString().equals(etUsername.getText().toString());
                }
            }, User.class);
            System.out.println(res.toString());

            if (!res.isEmpty()) {
                int i;
                for (i = 0; i < res.size(); ++i) {
                    String username = (String) res.get(i).get(Key.User.USER_NAME);
                    String upw = (String) res.get(i).get(Key.User.USER_PASSWORD);
                    if (Objects.equals(username, etUsername.getText().toString()) &&
                            Objects.equals(upw, etPassword.getText().toString())) {
                        startActivity(new Intent(LoginActivity.this, HomePage.class));
                        break;
                    }
                }
                if (i == res.size()) toast.show();
            } else {
                toast.show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }
    public void back(View v) {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
    }
}