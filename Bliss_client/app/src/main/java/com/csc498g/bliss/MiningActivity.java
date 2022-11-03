package com.csc498g.bliss;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import org.w3c.dom.Text;

import java.util.Objects;

public class MiningActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_mine);
    }

    public void mine(View view) {

        String text = ((EditText)findViewById(R.id.contentBox)).getText().toString();
        Link.addTextGem(MiningActivity.this, text, this);

    }

    public void cancelMining(View view){
        this.finish();
    }
}