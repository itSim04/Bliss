package com.csc498g.bliss;

import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class GenderSelectionCheck implements AdapterView.OnItemSelectedListener {

    // Class to handle the gender spinner

    static final ArrayList<String> GENDER_ARRAY = new ArrayList<>(Arrays.asList("Male", "Female", "Other", "Rather not Say"));
    private final TextView gender_mask;

    public GenderSelectionCheck(TextView gender_mask) {

        this.gender_mask = gender_mask;

    }

    // Handles Gender selections
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        gender_mask.setText(String.valueOf(GENDER_ARRAY.get(position)));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        gender_mask.setText("");
    }

}
