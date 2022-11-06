package com.csc498g.bliss;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class EditProfileActivity extends AppCompatActivity {

    // Activity used to Edit the Profile of a user

    Spinner genderSpinner; // The spinner holding the gender options
    EditText bioEdt; // The bio edit box
    EditText birthDateEdt; // The birthday edit box
    User owner; // The owner of this profile
    TextView errorBox; // The box that will display errors

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Creates the activity
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Initializes the Error box
        errorBox = findViewById(R.id.editProfileErrorBox);

        // Forces a clean Full Screen layout
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_edit_profile);

        // Initializes the Gender chooser
        genderSpinner = findViewById(R.id.genderSpinner);

        // Initializes the Bio Edit box
        bioEdt = findViewById(R.id.bioEdt);

        // Initializes the Birthday box
        birthDateEdt = findViewById(R.id.birthDateEdt);

        // Initialized a Gender Mask to show the values
        EditText genderMask = findViewById(R.id.gendertText);

        // Populates the Gender spinner
        ArrayList<String> genderArray = new ArrayList<>(Arrays.asList("Male", "Female", "Other", "Rather not Say"));
        ArrayAdapter<String> genderAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, genderArray);
        genderAdapter.setDropDownViewResource(R.layout.spinner_layout);
        genderSpinner.setAdapter(genderAdapter);

        // Listens for a selection
        genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                genderMask.setText(String.valueOf(genderArray.get(position)));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                genderMask.setText("");
            }

        });

        // Extracts the saved user from the Shared preferences
        owner = Helper.extractUser(this);

        // Sets the values in the layout according to the user
        genderSpinner.setSelection(owner.getGender());
        bioEdt.setText(owner.getBio());

        // Converts the Birthday to a readable format and updates the layout
        birthDateEdt.setText(Helper.birthdayEncode(owner.getBirthday().toString()));

        // Binds a Watcher for cleaner input
        birthDateEdt.addTextChangedListener(new DateWatcher(birthDateEdt));

    }

    public void cancelEditing(View view){

        // Goes back to the previous activity
        this.finish();

    }

    public void saveChanges(View view) {

        // Applies the changes

        // Retrieves the new values
        String new_gender = genderSpinner.getSelectedItem().toString();
        String new_bio = bioEdt.getText().toString();
        String new_birthDate = birthDateEdt.getText().toString();

        // Converts the Gender to a computable format
        byte gender_input = Helper.genderFormatter(new_gender);


        try {

            // Converts the birthday to a computable format
            String birthday_date_input = Helper.birthdayDecode(new_birthDate);

            if (birthday_date_input.matches(".*[A-Z].*")) {

                // Checks if the birthday is incomplete
                Toast.makeText(getApplicationContext(), "Missing Birthday.", Toast.LENGTH_LONG).show();

            } else {

                // Updates the shared preferences with the new information
                owner.setGender(gender_input);
                owner.setBio(new_bio);
                owner.setBirthday(LocalDate.parse(birthday_date_input, DateTimeFormatter.ISO_LOCAL_DATE));
                Link.updateUserInDatabase(EditProfileActivity.this, this, owner);

            }

        } catch(IllegalArgumentException e) {

            errorBox.setText( "Invalid Birthday");

        }
    }
}