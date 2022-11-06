package com.csc498g.bliss;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    // Activity for registering

    private TextView error_box; // The error box
    private EditText date_edit; // The edit box of the birthday
    private Spinner gender; // The spinner of the gender
    private EditText username; // The edit box for the username
    private EditText email; // The edit box for the email
    private EditText password; // The edit box for the password
    private EditText confirm_password; // The edit box for confirming the password

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Creates the activity
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Forces a clean Full Screen layout
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_register);

        // Initializes the error box
        error_box = findViewById(R.id.errorBox);

        // Initializes the edit boxes
        username = findViewById(R.id.nameEdt);
        email = findViewById(R.id.emailEdt);
        password = findViewById(R.id.birthDateEdt);
        confirm_password = findViewById(R.id.confirmPasswordEdt);

        // Initialized a Gender Mask to show the values
        EditText gender_mask = findViewById(R.id.genderMask);

        // Initializes the gender listener
        gender = findViewById(R.id.genderSpinner);

        // Populates the Gender spinner
        ArrayAdapter<String> gender_adapter = new ArrayAdapter<>(this, R.layout.spinner_item, GenderSelectionCheck.GENDER_ARRAY);
        gender_adapter.setDropDownViewResource(R.layout.spinner_layout);
        gender.setAdapter(gender_adapter);

        // Listens for a selection
        gender.setOnItemSelectedListener(new GenderSelectionCheck(gender_mask));

        // Retrieves the birthday edit
        date_edit = findViewById(R.id.dateEdt);

        // Adds a watcher for cleaner birthday input
        date_edit.addTextChangedListener(new DateWatcher(date_edit));

    }

    public void togglePassword(View v) {

        // Toggles whether the password is shown or not
        if (((TextView) v).getText().toString().equalsIgnoreCase("Show")) {
            if (v.getTag().equals("pass")) {
                ((TextView) v).setText(R.string.hide);
                ((EditText) findViewById(R.id.birthDateEdt)).setTransformationMethod(null);
            } else {
                ((TextView) v).setText(R.string.hide);
                ((EditText) findViewById(R.id.confirmPasswordEdt)).setTransformationMethod(null);
            }
        } else {
            if (v.getTag().equals("pass")) {
                ((TextView) v).setText(R.string.hide);
                ((EditText) findViewById(R.id.birthDateEdt)).setTransformationMethod(new PasswordTransformationMethod());
            } else {
                ((TextView) v).setText(R.string.hide);
                ((EditText) findViewById(R.id.confirmPasswordEdt)).setTransformationMethod(new PasswordTransformationMethod());
            }
        }

    }

    public void signUp(View v) {

        // Handles the sign up process

        // Retrieves the values from the boxes
        String username_input = username.getText().toString();
        String email_input = email.getText().toString();
        String password_input = password.getText().toString();
        String confirm_password_input = confirm_password.getText().toString();
        byte gender_input = Helper.genderFormatter(gender.getSelectedItem().toString());

        try {

            String birthday = Helper.birthdayDecode(this.date_edit.getText().toString());

            if (username_input.length() < 2) {

                // Checks if the username is too short
                error_box.setText(R.string.short_username);

            } else if (password_input.length() < 5) {

                // Checks if the password is too weak
                error_box.setText(R.string.weak_password);

            } else if (!email_input.contains("@") || !email_input.contains(".")) {

                // Checks if the email is valid
                error_box.setText(R.string.invalid_email);

            } else if (birthday.matches(".*[A-Z].*")) {

                // Checks if the birthday is complete
                error_box.setText(R.string.missing_birthday);

            } else if (!password_input.equals(confirm_password_input)) {

                // Checks if the passwords match
                error_box.setText(R.string.not_matching_passwords);

            } else {

                // Creates and posts the new user
                User user = new User(-1, username_input, password_input, email_input, "", birthday, LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE), gender_input, null, null, 0, 0);
                Link.checkAvailability(RegisterActivity.this, user, error_box);

            }

        } catch (IllegalArgumentException e) {

            error_box.setText(R.string.invalid_birthday);

        }
    }

    public void signIn(View v) {

        // Signs the user in
        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(i);

    }
}