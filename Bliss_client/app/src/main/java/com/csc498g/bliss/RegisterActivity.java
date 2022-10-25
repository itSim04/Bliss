package com.csc498g.bliss;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Objects.requireNonNull(getSupportActionBar()).hide();

        setContentView(R.layout.activity_register);
    }
    public void signUp(View v){
        EditText username = (EditText) findViewById(R.id.nameEdt);
        EditText password = (EditText) findViewById(R.id.passwordEdt);
        EditText confirm_password = (EditText) findViewById(R.id.confirmPasswordEdt);
        EditText birthday_date = (EditText) findViewById(R.id.dateEdt);
        EditText gender = (EditText) findViewById(R.id.genderEdt);

        String username_input = username.getText().toString();
        String password_input = password.getText().toString();
        String confirm_password_input = confirm_password.getText().toString();
        String birthday_date_input = birthday_date.getText().toString();
        String gender_input = gender.getText().toString();

        if(username_input.equals("") || password_input.equals("") || confirm_password_input.equals("") || birthday_date_input.equals("") || gender_input.equals(""))
        {
            Toast.makeText(this, "Missing entries.", Toast.LENGTH_LONG).show();
        }
        else if(!password_input.equals(confirm_password_input))
        {       //Making sure password are matching
            Toast.makeText(this, "Passwords do not match.", Toast.LENGTH_LONG).show();
        }else
        {
            Toast.makeText(this, "Welcome, " + username_input, Toast.LENGTH_SHORT).show();
            Intent i = new Intent(getApplicationContext(), FeedActivity.class);
            startActivity(i);
        }
    }

    public void signIn(View v) {
        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(i);
    }
}