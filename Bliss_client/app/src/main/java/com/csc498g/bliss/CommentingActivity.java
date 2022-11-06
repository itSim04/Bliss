package com.csc498g.bliss;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.Objects;

public class CommentingActivity extends AppCompatActivity {

    // Activity for adding a comment


    ConstraintLayout section; // The layout holding the polls
    int comment_type = 0; // 0: Text | 1: Image | 2: Polls

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Creates the activity
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Forces a clean Full Screen layout
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_comment);

        // Initializes the layout holding the polls
        section = findViewById(R.id.pollSections);
    }

    public void comment(View view) {

        if (comment_type == 0) {

            // Text Section

            // Retrieves and posts the text content
            String text = ((EditText)findViewById(R.id.contentBox)).getText().toString();
            Link.addTextComment(CommentingActivity.this, text, getIntent().getIntExtra(Constants.Gems.ROOT, -1), this);

        } else if (comment_type == 1) {

            // Retrieves the polls content
            String choiceOne = ((EditText) findViewById(R.id.choiceOneEdt)).getText().toString();
            String choiceTwo = ((EditText) findViewById(R.id.choiceTwoEdt)).getText().toString();
            String choiceThree = ((EditText) findViewById(R.id.choiceThreeEdt)).getText().toString();
            String choiceFour = ((EditText) findViewById(R.id.choiceFourEdt)).getText().toString();

            // Retrieves the prompt
            String text = ((EditText) findViewById(R.id.contentBox)).getText().toString();

            // Posts the content

            Link.addPollComment(CommentingActivity.this, text, choiceOne, choiceTwo, choiceThree, choiceFour, getIntent().getIntExtra(Constants.Gems.ROOT, -1), this);
        }
    }

    public void createPoll(View view) {

        // Switches to populating polls
        section.setVisibility(View.VISIBLE);
        comment_type = 1;

    }

    public void writeText(View view) {

        // Switches to writing text
        section.setVisibility(View.INVISIBLE);
        comment_type = 0;

    }

    public void cancelCommenting(View view) {

        // Goes back to the comment
        this.finish();
    }
}