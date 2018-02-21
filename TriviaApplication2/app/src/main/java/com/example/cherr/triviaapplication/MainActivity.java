/*
Homework 3
Trivia Application
Bhanu Teja Sriram
Tejaswini Naredla
*/
package com.example.cherr.triviaapplication;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements URLAsync.iData{
    TextView welcome,start_msg;
    Button exit,start;
    ImageView trivia;
    ArrayList<Question> questions;
    static String qsn_key=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        welcome = (TextView) findViewById(R.id.textViewWelcome);
        start_msg = (TextView) findViewById(R.id.textViewReady);
        exit = (Button) findViewById(R.id.buttonExit);
        start = (Button) findViewById(R.id.buttonStart);
        trivia = (ImageView) findViewById(R.id.imageViewTrivia);
        start.setEnabled(false);
        start_msg.setEnabled(false);
        if (isConnected()) {
            new URLAsync(MainActivity.this,start,trivia,start_msg,MainActivity.this).execute("http://dev.theappsdr.com/apis/trivia_json/trivia_text.php");
            //Log.d("demo", "qsn"+questions.get(0));
        }
        else{
            Log.d("internet", "Entering else loop");
            Toast.makeText(MainActivity.this,"No Internet Connection",Toast.LENGTH_SHORT).show();
        }
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,TriviaActivity.class);
                qsn_key="100";
                Log.d("demo", "onClick: "+questions.size());
                i.putExtra(qsn_key,questions);
                startActivity(i);
            }
        });


        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finishAffinity();

            }
        });
    }
    private boolean isConnected(){


        ConnectivityManager cm= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo nf=cm.getActiveNetworkInfo();
        if(nf==null || !nf.isConnected()){
            return false;
        }
        return true;
    }

    @Override
    public void processFinish(ArrayList<Question> output) {
    questions=output;

    }
}
