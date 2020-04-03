package com.example.farmfresh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class SignUpBuyers extends AppCompatActivity {
    EditText etFullName;
    EditText etUName;
    EditText etPass;
    EditText etPass2;
    Spinner sLocation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_buyers);

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
        startActivity(new Intent(SignUpBuyers.this, BuyOrSellActivity.class));
    }

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
            toastMessage += "Please enter a password";
            }if(passValidate.matches("")) {
            toastMessage += "Please re-enter your password";
            }if(!password.matches(passValidate)) {
            //If the two passwords entered do not match, tell user
            toastMessage += "The passwords entered do not match. Please try again";
            }if(location.matches("")) {
                toastMessage += "Please select a location";
            }
        } else {
            //Valid inputs entered, continue to homepage
            startActivity(new Intent(SignUpBuyers.this, HomePage.class));
        }
        //Make toast with appropriate message
        Toast toast = Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT);
        toast.setMargin(50, 50);
        toast.show();
    }
}