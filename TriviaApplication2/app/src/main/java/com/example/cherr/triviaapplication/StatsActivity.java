/*
Homework 3
Trivia Application
Bhanu Teja Sriram
Tejaswini Naredla
*/

package com.example.cherr.triviaapplication;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class StatsActivity extends AppCompatActivity {
    TextView correct_answers;
    Button quit,button_try;
    ProgressBar per;
    static String stats_key=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        correct_answers=(TextView)findViewById(R.id.textViewPercentage);
        quit=(Button)findViewById(R.id.buttonQuit);
        button_try=(Button)findViewById(R.id.buttonTryAgain);
        per=(ProgressBar)findViewById(R.id.progressBarPercentage);

        final ArrayList<Question> trivia_qsn=(ArrayList)getIntent().getExtras().get(TriviaActivity.qsn_key);
       int c= (int) Math.round(getIntent().getExtras().getDouble(TriviaActivity.per_key));
        Log.d("stats",Integer.toString(c));
        correct_answers.setText(Integer.toString(c)+" %");
        correct_answers.setTextColor(Color.GREEN);
        per.setProgress(c);
       per.getProgressDrawable().setColorFilter(0xFF0000FF ,android.graphics.PorterDuff.Mode.MULTIPLY);
        //per.getProgressDrawable().setColorFilter(this.getResources().getColor(R.color.sp), android.graphics.PorterDuff.Mode.SRC_IN);
        button_try.setBackgroundColor(0xFF00FFFF);
        button_try.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent stats_intent=new Intent(StatsActivity.this,TriviaActivity.class);
                stats_key="200";
                stats_intent.putExtra(stats_key,trivia_qsn);
                startActivity(stats_intent);
            }
        });


        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(StatsActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

}
