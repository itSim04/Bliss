package com.csc498g.bliss;

import android.content.Intent;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
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

        EditText genderMask = ((EditText)findViewById(R.id.genderEdt));
        ArrayList genderArray = new ArrayList<>(Arrays.asList("Male", "Female", "Other", "Rather not Say"));
        ArrayAdapter<String> genderAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, genderArray);
        genderAdapter.setDropDownViewResource(R.layout.spinner_layout);
        Spinner genderEdit = ((Spinner)findViewById(R.id.genderSpinner));
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

        EditText dateEdit = ((EditText)findViewById(R.id.dateEdt));
        dateEdit.addTextChangedListener(new TextWatcher() {

            private String current = "";
            private final Calendar cal = Calendar.getInstance();

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

                    if (clean.length() < 8){
                        String ddmmyyyy = "DDMMYYYY";
                        clean = clean + ddmmyyyy.substring(clean.length());
                    }else{
                        //This part makes sure that when we finish entering numbers
                        //the date is correct, fixing it otherwise
                        int day  = Integer.parseInt(clean.substring(0,2));
                        int mon  = Integer.parseInt(clean.substring(2,4));
                        int year = Integer.parseInt(clean.substring(4,8));

                        mon = mon < 1 ? 1 : Math.min(mon, 12);
                        cal.set(Calendar.MONTH, mon-1);
                        year = (year<1900)?1900: Math.min(year, 2100);
                        cal.set(Calendar.YEAR, year);
                        // ^ first set year for the line below to work correctly
                        //with leap years - otherwise, date e.g. 29/02/2012
                        //would be automatically corrected to 28/02/2012

                        day = Math.min(day, cal.getActualMaximum(Calendar.DATE));
                        clean = String.format("%02d%02d%02d",day, mon, year);
                    }

                    clean = String.format("%s/%s/%s", clean.substring(0, 2),
                            clean.substring(2, 4),
                            clean.substring(4, 8));

                    sel = Math.max(sel, 0);
                    current = clean;
                    dateEdit.setText(current);
                    dateEdit.setSelection(Math.min(sel, current.length()));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

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

        String[] birthday_date_input_rev= birthday_date.getText().toString().split("/");
        String birthday_date_input = birthday_date_input_rev[2] + "-" + birthday_date_input_rev[1] + "-" + birthday_date_input_rev[0];

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