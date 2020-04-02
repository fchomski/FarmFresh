package com.example.farmfresh;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.farmfresh.model.data.Connect;
import com.example.farmfresh.model.data.Key;
import com.example.farmfresh.model.data.User;
import com.example.farmfresh.model.data.UserType;

import org.json.JSONException;

public class SignUp extends AppCompatActivity {
    EditText etFullName;
    EditText etUName;
    EditText etPass;
    EditText etPass2;
    Spinner sLocation;
    Intent prevIntent;
    String mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        prevIntent = getIntent();
        mode = prevIntent.getStringExtra("mode");

        //Replace characters entered in password and re-enter password fields with asterisks
        etPass = findViewById(R.id.password);
        etPass2 = findViewById(R.id.passwordValidate);
        etPass.setTransformationMethod(new AsteriskPasswordTransformationMethod());
        etPass2.setTransformationMethod(new AsteriskPasswordTransformationMethod());

        Spinner spinner = findViewById(R.id.city);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.cityList, android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
    }

    public void back(View v) {
        startActivity(new Intent(SignUp.this, BuyOrSellActivity.class));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void signUpValidate(View v) {
        //Get all the data entered by the user in each of the fields
        etFullName = findViewById(R.id.fullName);
        String fullName = etFullName.getText().toString();
        etUName = findViewById(R.id.username);
        String username = etUName.getText().toString();
        etPass = findViewById(R.id.password);
        String password = etPass.getText().toString();
        etPass2 = findViewById(R.id.passwordValidate);
        String passValidate = etPass2.getText().toString();
        sLocation = findViewById(R.id.city);
        String location = sLocation.getSelectedItem().toString();

        String toastMessage = "";
        //Display a toast message prompting user to enter a field if they have missed one or re-enter if the value is invalid
        if(fullName.matches("")||username.matches("")||password.matches("")||passValidate.matches("")||location.matches("")) {
            if(fullName.matches("")) {
                toastMessage += "\n- Please enter your full name\n";
            }if(username.matches("")) {
                toastMessage += "\n- Please enter a username\n";
            }if(password.matches("")) {
                toastMessage += "\n- Please enter a password\n";
            }if(passValidate.matches("")) {
                toastMessage += "\n- Please re-enter your password\n";
            }if(!password.matches(passValidate)) {
                //If the two passwords entered do not match, tell user
                toastMessage += "\n- The passwords entered do not match. Please try again\n";
            }if(location.matches("Select a city")) {
                toastMessage += "\n- Please select a location\n";
            }
        } else {
            //Valid inputs entered, continue to homepage

            User newuser = new User();
            newuser.put(Key.User.USER_NAME, username);
            newuser.put(Key.User.USER_PASSWORD, password);
            newuser.put(Key.User.USER_TYPE, mode.equals("seller") ? UserType.SELLER: UserType.BUYER);
            try {
                // add new user.
                Connect c = new Connect(getApplicationContext());
                c.add(newuser, User.class);
                c.sync();
            } catch (JSONException e) {
                startActivity(new Intent(SignUp.this, MainActivity.class));
                e.printStackTrace();
            }

            startActivity(new Intent(SignUp.this, LoginActivity.class));
        }
        //Make toast with appropriate message
        Toast toast = Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT);
        toast.setMargin(50, 50);
        toast.show();
    }
}