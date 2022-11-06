package com.csc498g.bliss;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.Objects;

public class MiningActivity extends AppCompatActivity {

    // Activity for mining

    ConstraintLayout section;
    int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Creates the activity
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Forces a clean Full Screen layout
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_mine);

        // Initializes the poll section
        section = findViewById(R.id.pollSections);

    }

    public void createPoll(View view) {

        // Switches to populating polls
        section.setVisibility(View.VISIBLE);
        flag = 1;

    }

    public void writeText(View view) {

        // Switches to writing texts
        section.setVisibility(View.INVISIBLE);
        flag = 0;
    }

    public void uploadImage(View view) {

        // Switches to uploading images (WIP)
        flag = 2;

    }

    public void mine(View view) {

        // Handles the mining process

        if (flag == 0) {

            // Text Section

            // Retrieves the text
            String text = ((EditText) findViewById(R.id.contentBox)).getText().toString();

            // Posts the gem
            Link.addTextGem(MiningActivity.this, text, this);

        } else if (flag == 1) {

            // Poll section

            // Retrieves the polls
            String choiceOne = ((EditText) findViewById(R.id.choiceOneEdt)).getText().toString();
            String choiceTwo = ((EditText) findViewById(R.id.choiceTwoEdt)).getText().toString();
            String choiceThree = ((EditText) findViewById(R.id.choiceThreeEdt)).getText().toString();
            String choiceFour = ((EditText) findViewById(R.id.choiceFourEdt)).getText().toString();

            // Retrieves the prompt
            String text = ((EditText) findViewById(R.id.contentBox)).getText().toString();

            // Posts the Gem
            Link.addPollGem(MiningActivity.this, text, choiceOne, choiceTwo, choiceThree, choiceFour, this);
        }

    }


    public void cancelMining(View view) {

        // Goes back to the previous activity
        this.finish();
    }
}