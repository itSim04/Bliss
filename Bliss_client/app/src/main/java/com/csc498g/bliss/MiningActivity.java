package com.csc498g.bliss;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import org.w3c.dom.Text;

import java.util.Objects;

public class MiningActivity extends AppCompatActivity {

    int flag = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_mine);
        View choiceOneLayout = (View) findViewById(R.id.choiceOneLayout);
        choiceOneLayout.setVisibility(View.GONE);
        View choiceTwoLayout = (View) findViewById(R.id.choiceTwoLayout);
        choiceTwoLayout.setVisibility(View.GONE);
        View choiceThreeLayout = (View) findViewById(R.id.choiceThreeLayout);
        choiceThreeLayout.setVisibility(View.GONE);
        View choiceFourLayout = (View) findViewById(R.id.choiceFourLayout);
        choiceFourLayout.setVisibility(View.GONE);
    }

    public void createPoll(View view){

        View choiceOneLayout = (View) findViewById(R.id.choiceOneLayout);
        choiceOneLayout.setVisibility(View.VISIBLE);
        View choiceTwoLayout = (View) findViewById(R.id.choiceTwoLayout);
        choiceTwoLayout.setVisibility(View.VISIBLE);
        View choiceThreeLayout = (View) findViewById(R.id.choiceThreeLayout);
        choiceThreeLayout.setVisibility(View.VISIBLE);
        View choiceFourLayout = (View) findViewById(R.id.choiceFourLayout);
        choiceFourLayout.setVisibility(View.VISIBLE);

        String choiceOne = ((EditText)findViewById(R.id.choiceOneEdt)).getText().toString();
        String choiceTwo = ((EditText)findViewById(R.id.choiceTwoEdt)).getText().toString();
        String choiceThree = ((EditText)findViewById(R.id.choiceThreeEdt)).getText().toString();
        String choiceFour = ((EditText)findViewById(R.id.choiceFourEdt)).getText().toString();
        if(choiceOne!="" && choiceTwo!="")
            flag = 1;

    }

    public void writeText(View view){
        View choiceOneLayout = (View) findViewById(R.id.choiceOneLayout);
        choiceOneLayout.setVisibility(View.GONE);
        View choiceTwoLayout = (View) findViewById(R.id.choiceTwoLayout);
        choiceTwoLayout.setVisibility(View.GONE);
        View choiceThreeLayout = (View) findViewById(R.id.choiceThreeLayout);
        choiceThreeLayout.setVisibility(View.GONE);
        View choiceFourLayout = (View) findViewById(R.id.choiceFourLayout);
        choiceFourLayout.setVisibility(View.GONE);
        flag = 0;
    }

    public void uploadImage(View view){
        flag = 2;
    }

    public void mine(View view) {

        if(flag == 0) {
            String text = ((EditText) findViewById(R.id.contentBox)).getText().toString();
            Link.addTextGem(MiningActivity.this, text, this);
        }else if(flag == 1){
            String text = ((EditText) findViewById(R.id.contentBox)).getText().toString();
        }

    }



    public void cancelMining(View view){
        this.finish();
    }
}