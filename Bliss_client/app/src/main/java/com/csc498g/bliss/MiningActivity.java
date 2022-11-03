package com.csc498g.bliss;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.Objects;

public class MiningActivity extends AppCompatActivity {

    ConstraintLayout section;
    int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_mine);

        section = findViewById(R.id.pollSections);

    }

    public void createPoll(View view) {

        section.setVisibility(View.VISIBLE);
        flag = 1;

    }

    public void writeText(View view) {

        section.setVisibility(View.INVISIBLE);

        flag = 0;
    }

    public void uploadImage(View view) {
        flag = 2;
    }

    public void mine(View view) {

        if (flag == 0) {

            String text = ((EditText) findViewById(R.id.contentBox)).getText().toString();
            Link.addTextGem(MiningActivity.this, text, this);

        } else if (flag == 1) {

            String choiceOne = ((EditText) findViewById(R.id.choiceOneEdt)).getText().toString();
            String choiceTwo = ((EditText) findViewById(R.id.choiceTwoEdt)).getText().toString();
            String choiceThree = ((EditText) findViewById(R.id.choiceThreeEdt)).getText().toString();
            String choiceFour = ((EditText) findViewById(R.id.choiceFourEdt)).getText().toString();

            String text = ((EditText) findViewById(R.id.contentBox)).getText().toString();

            Link.addPollGem(MiningActivity.this, text, choiceOne, choiceTwo, choiceThree, choiceFour, this);
        }

    }


    public void cancelMining(View view) {
        this.finish();
    }
}