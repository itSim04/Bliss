package com.csc498g.bliss;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Objects.requireNonNull(getSupportActionBar()).hide();

        setContentView(R.layout.activity_login);
    }

    public void signIn(View v) {
        EditText username = (EditText) findViewById(R.id.newName);
        EditText password = (EditText) findViewById(R.id.newBirthDate);

        String username_input = username.getText().toString();
        String password_input = password.getText().toString();
        if(username_input.equals("") || password_input.equals(""))
        {    //Making sure the user inputs both
            Toast.makeText(getApplicationContext(), "Missing entries.", Toast.LENGTH_LONG).show();
        }
        else
        {
            Link.authenticateUser(this, LoginActivity.this, username_input, password_input);

        }
    }

    public void signUp(View v) {
        Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(i);
    }
}
