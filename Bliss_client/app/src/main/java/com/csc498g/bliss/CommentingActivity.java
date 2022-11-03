package com.csc498g.bliss;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class CommentingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_comment);
    }

    public void comment(View view) {

        String text = ((EditText)findViewById(R.id.contentBox)).getText().toString();
        Link.addTextComment(CommentingActivity.this, text, getIntent().getIntExtra(Constants.Gems.ROOT, -1), this);

    }

    public void cancelCommenting(View view){
        this.finish();
    }
}