package com.csc498g.bliss;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    int email_flag = 0, username_flag = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Objects.requireNonNull(getSupportActionBar()).hide();

        setContentView(R.layout.activity_register);

        ArrayAdapter<String> mArrayAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, new ArrayList<>(Arrays.asList("Male", "Female", "Other", "Rather not Say")));
        mArrayAdapter.setDropDownViewResource(R.layout.spinner_layout);
        ((Spinner)findViewById(R.id.genderSpinner)).setAdapter(mArrayAdapter);

    }
    public void signUp(View v){

        email_flag = 0;
        username_flag = 0;

        EditText username = findViewById(R.id.nameEdt);
        EditText email = findViewById(R.id.emailEdt);
        EditText password = findViewById(R.id.passwordEdt);
        EditText confirm_password = findViewById(R.id.confirmPasswordEdt);
        EditText birthday_date = findViewById(R.id.dateEdt);
        Spinner gender = findViewById(R.id.genderSpinner);

        String username_input = username.getText().toString();
        String email_input = email.getText().toString();
        String password_input = password.getText().toString();
        String confirm_password_input = confirm_password.getText().toString();
        String birthday_date_input = birthday_date.getText().toString();
        String gender_string = gender.getSelectedItem().toString();
        byte gender_input;
        switch (gender_string.toLowerCase()) {

            case "male":
                gender_input = 0;
                break;

            case "female":
                gender_input = 1;
                break;

            case "other":
                gender_input = 2;
                break;

            case "rather not say":
                gender_input = 3;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + gender_string.toLowerCase());
        }

        if(username_input.equals("") || password_input.equals("") || confirm_password_input.equals("") || birthday_date_input.equals("") || email_input.equals("")) {

            Toast.makeText(getApplicationContext(), "Missing entries.", Toast.LENGTH_LONG).show();

        } else if(!password_input.equals(confirm_password_input)) {

            //Making sure password are matching
            Toast.makeText(getApplicationContext(), "Passwords do not match.", Toast.LENGTH_LONG).show();

        } else {

            User user = new User(-1, username_input, password_input, email_input, birthday_date_input, gender_input, null, null, 0, 0);
            Link.checkAvailability(RegisterActivity.this, this, user);

        }
    }

    public void proceed() {




    }

    public void signIn(View v) {
        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(i);
    }
}