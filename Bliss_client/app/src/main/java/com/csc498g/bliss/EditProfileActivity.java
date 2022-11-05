package com.csc498g.bliss;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Objects;

public class EditProfileActivity extends AppCompatActivity {


    Spinner genderEdit;
    EditText bioEdt;
    EditText birthDateEdt;
    User owner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_edit_profile);

        genderEdit = findViewById(R.id.genderSpinner);
        bioEdt = (EditText) findViewById(R.id.bioEdt);
        birthDateEdt = (EditText) findViewById(R.id.birthDateEdt);

        EditText genderMask = ((EditText) findViewById(R.id.gendertText));
        ArrayList<String> genderArray = new ArrayList<>(Arrays.asList("Male", "Female", "Other", "Rather not Say"));
        ArrayAdapter<String> genderAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, genderArray);
        genderAdapter.setDropDownViewResource(R.layout.spinner_layout);
        genderEdit.setAdapter(genderAdapter);
        genderEdit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                genderMask.setText(String.valueOf(genderArray.get(position)));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                genderMask.setText("");
            }

        });


        owner = Helper.extractUser(this);

        genderEdit.setSelection(owner.getGender());
        bioEdt.setText(owner.getBio());

        String[] birthday = owner.getBirthday().toString().split("-");
        birthDateEdt.setText(String.format("%s/%s/%s", birthday[2], birthday[1], birthday[0]));

        birthDateEdt.addTextChangedListener(new TextWatcher() {

            private final Calendar cal = Calendar.getInstance();
            private String current = "";

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(current)) {
                    String clean = s.toString().replaceAll("[^\\d.]|\\.", "");
                    String cleanC = current.replaceAll("[^\\d.]|\\.", "");

                    int cl = clean.length();
                    int sel = cl;
                    for (int i = 2; i <= cl && i < 6; i += 2) {
                        sel++;
                    }
                    //Fix for pressing delete next to a forward slash
                    if (clean.equals(cleanC)) sel--;

                    if (clean.length() < 8) {
                        String ddmmyyyy = "DDMMYYYY";
                        clean = clean + ddmmyyyy.substring(clean.length());
                    } else {
                        //This part makes sure that when we finish entering numbers
                        //the date is correct, fixing it otherwise
                        int day = Integer.parseInt(clean.substring(0, 2));
                        int mon = Integer.parseInt(clean.substring(2, 4));
                        int year = Integer.parseInt(clean.substring(4, 8));

                        mon = mon < 1 ? 1 : Math.min(mon, 12);
                        cal.set(Calendar.MONTH, mon - 1);
                        year = (year < 1900) ? 1900 : Math.min(year, 2100);
                        cal.set(Calendar.YEAR, year);
                        // ^ first set year for the line below to work correctly
                        //with leap years - otherwise, date e.g. 29/02/2012
                        //would be automatically corrected to 28/02/2012

                        day = Math.min(day, cal.getActualMaximum(Calendar.DATE));
                        clean = String.format("%02d%02d%02d", day, mon, year);
                    }

                    clean = String.format("%s/%s/%s", clean.substring(0, 2),
                            clean.substring(2, 4),
                            clean.substring(4, 8));

                    sel = Math.max(sel, 0);
                    current = clean;
                    birthDateEdt.setText(current);
                    birthDateEdt.setSelection(Math.min(sel, current.length()));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    public void saveChanges(View view) {

        String new_gender = genderEdit.getSelectedItem().toString();
        String new_bio = bioEdt.getText().toString();
        String new_birthDate = birthDateEdt.getText().toString();


        byte gender_input;
        switch (new_gender.toLowerCase()) {

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
                throw new IllegalStateException("Unexpected value: " + new_birthDate.toLowerCase());
        }


        String[] birthday_date_input_rev = new_birthDate.split("/");

        if (birthday_date_input_rev.length == 3) {

            String birthday_date_input = birthday_date_input_rev[2] + "-" + birthday_date_input_rev[1] + "-" + birthday_date_input_rev[0];

            if (birthday_date_input.matches(".*[A-Z].*")) {

                Toast.makeText(getApplicationContext(), "Missing Birthday.", Toast.LENGTH_LONG).show();

            } else {

                owner.setGender(gender_input);
                owner.setBio(new_bio);
                owner.setBirthday(LocalDate.parse(birthday_date_input, DateTimeFormatter.ISO_LOCAL_DATE));
                Link.updateUserInDatabase(this, this, owner);

            }

        } else {
            Toast.makeText(getApplicationContext(), "Invalid Birthday.", Toast.LENGTH_LONG).show();
        }


    }
}