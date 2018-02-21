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
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class TriviaActivity extends AppCompatActivity {
    TextView qsn_no,time,qsn_name;
    ImageView image;
    Button quit,next;
    RadioGroup rg;
    int i=0;
    int selectedIndex=-1,correct_index;
    double counter=0;
    double percentage;
    ArrayList trivia_qsns,trivia_qsns1,trivia_qsns2;
    static String per_key=null;
    static String qsn_key=null;
    ProgressBar progressBar;
    TextView li;
    String TAG = "Demo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia);
        qsn_no=(TextView)findViewById(R.id.textViewQuestionNumber);
        qsn_name=(TextView)findViewById(R.id.textViewQuestionText);
        time=(TextView)findViewById(R.id.textViewTime);
        image=(ImageView)findViewById(R.id.imageViewQsn);
        quit=(Button)findViewById(R.id.buttonTQuit);
        next=(Button)findViewById(R.id.buttonNext);
        rg=(RadioGroup)findViewById(R.id.radioGroup);
        progressBar=findViewById(R.id.progressBarImage);
        progressBar.setVisibility(View.INVISIBLE);
        li=findViewById(R.id.textViewLI);


        Question question = new Question();
        trivia_qsns1= (ArrayList) getIntent().getSerializableExtra(MainActivity.qsn_key);
        trivia_qsns2=  (ArrayList) getIntent().getSerializableExtra(StatsActivity.stats_key);
        if(trivia_qsns1==null){
            trivia_qsns=trivia_qsns2;
            i=0;
            counter=0;
            percentage=0.0;
        }
        else{
            trivia_qsns=trivia_qsns1;
        }
        question = (Question) trivia_qsns.get(i);
        String url = question.getImage_url();
        Log.d("Demo", "onCreate: " + trivia_qsns.size());
        if(isConnected()){
        if(url.length()!=0) {
            new ImageAsync(image,progressBar,li, TriviaActivity.this).execute(url);
        }
        else{
            image.setImageBitmap(null);
        }}
        else{
            image.setImageBitmap(null);
           Toast toast= Toast.makeText(TriviaActivity.this,"No Internet Connection",Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP|Gravity.LEFT, 250, 250);
            toast.show();
        }
        qsn_no.setText("Q " + Integer.toString(question.getQuestion_id() + 1));
        qsn_no.setBackgroundColor(0xFF00FFFF);
        qsn_name.setText(question.getQuestion());

        final ArrayList<String> questionOptions = question.getOptions();
        Log.d("trivia", "trivia options"+question.getOptions().size());
        correct_index=question.getAnswer_index();

        for (int row = 0; row < questionOptions.size(); row++)

        {
            RadioButton rdbtn = new RadioButton(TriviaActivity.this);
            rdbtn.setId(row + 1);
            rdbtn.setText("" + questionOptions.get(row));
            rdbtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
            rg.addView(rdbtn);
        }


        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                int radioButtonID = rg.getCheckedRadioButtonId();
//                View radioButton = rg.findViewById(radioButtonID);
//                selectedIndex = rg.indexOfChild(radioButton);
                selectedIndex=checkedId-1;
                //Log.d("radio", "onCheckedChanged: "+checkedId);
                Log.d("radio", "selected first"+Integer.toString(selectedIndex)+"    "+Integer.toString(correct_index));

            }
        });


        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedIndex==correct_index){
                    Log.d("counter", "entering the loop ");
                    counter+=1;
                    Log.d("counter", "first qsn "+counter);
                }
                selectedIndex=-1;
                rg.clearCheck();
                Intent i=new Intent(TriviaActivity.this,StatsActivity.class);
                per_key="100";
                qsn_key="101";
                percentage=counter/(double)(trivia_qsns.size());
                percentage=percentage*100;
                i.putExtra(qsn_key,trivia_qsns);
                i.putExtra(per_key,percentage);
                startActivity(i);
            }
        });

        new CountDownTimer(120000, 1000) {

            public void onTick(long millisUntilFinished) {
                time.setText("Time Left: " + millisUntilFinished / 1000+" seconds");
                time.setBackgroundColor(0xFF00FFFF);
            }

            public void onFinish() {
                if(selectedIndex==correct_index){
                    Log.d("counter", "entering the loop ");
                    counter+=1;
                    Log.d("counter", "first qsn "+counter);
                }
                selectedIndex=-1;
                Intent i=new Intent(TriviaActivity.this,StatsActivity.class);
                Log.d("per", "onFinish: "+counter+trivia_qsns.size());
                percentage=counter/(double)(trivia_qsns.size());
                Log.d("per", "onFinish1: "+percentage);
                percentage=percentage*100;
                Log.d("per", "onFinish2: "+percentage);
                per_key="100";
                qsn_key="101";
                i.putExtra(qsn_key,trivia_qsns);
                i.putExtra(per_key,percentage);
                startActivity(i);
            }
        }.start();

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(selectedIndex==correct_index){
                    Log.d("counter", "entering the loop ");
                    counter+=1;
                    Log.d("counter", "first qsn "+counter);
                }
                selectedIndex=-1;
                rg.removeAllViews();
                rg.clearCheck();
                if(i<trivia_qsns.size()-1){
                i+=1;
                Question question = new Question();
                //ArrayList trivia_qsns = (ArrayList) getIntent().getSerializableExtra(MainActivity.qsn_key);
                question = (Question) trivia_qsns.get(i);
                String url = question.getImage_url();
                Log.d("Demo", "onCreate: " + trivia_qsns.size());
                if(isConnected()) {
                    if (url.length() != 0) {
                        new ImageAsync(image,progressBar,li, TriviaActivity.this).execute(url);
                    } else {
                        image.setImageBitmap(null);
                    }
                }
                else{
                    image.setImageBitmap(null);
                    Toast toast= Toast.makeText(TriviaActivity.this,"No Internet Connection",Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP|Gravity.LEFT, 250, 250);
                    toast.show();
                }
                qsn_no.setText("Q " + Integer.toString(question.getQuestion_id() + 1));
                qsn_name.setText(question.getQuestion());

                ArrayList<String> questionOptions2 = question.getOptions();
                Log.d("trivia", "trivia options"+question.getOptions().size());
                correct_index=question.getAnswer_index();
                //int flag=1;
                for (int row = 0; row < questionOptions2.size(); row++)

                {
                    RadioButton rdbtn = new RadioButton(TriviaActivity.this);
                    rdbtn.setId(row + 1);
                    rdbtn.setText("" + questionOptions2.get(row));
                    rdbtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
                    rg.addView(rdbtn);
                   // flag++;
                }}
                else{
                    Log.d("else", "ENtering else loop");
                    Intent i=new Intent(TriviaActivity.this,StatsActivity.class);
                    percentage=counter/(double)(trivia_qsns.size());
                    percentage=percentage*100;
                    per_key="100";
                    qsn_key="101";
                    i.putExtra(qsn_key,trivia_qsns);
                    i.putExtra(per_key,percentage);
                    startActivity(i);

                }


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





}
