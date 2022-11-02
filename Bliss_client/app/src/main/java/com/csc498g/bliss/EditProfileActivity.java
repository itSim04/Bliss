package com.csc498g.bliss;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import java.util.Objects;

public class EditProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_edit_profile);
    }

    public void saveChanges(View view){
        EditText usernameEdt = (EditText) findViewById(R.id.nameEdt);
        EditText bioEdt = (EditText) findViewById(R.id.bioEdt);
        EditText birthDateEdt = (EditText) findViewById(R.id.birthDateEdt);

        String new_username = usernameEdt.getText().toString();
        String new_bio = bioEdt.getText().toString();
        String new_birthDate = birthDateEdt.getText().toString();
    }
}