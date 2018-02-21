/*
Homework 3
Trivia Application
Bhanu Teja Sriram
Tejaswini Naredla
*/

package com.example.cherr.triviaapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class URLAsync extends AsyncTask<String,Void,ArrayList<String>> {
    ArrayList<Question> mquestions=new ArrayList<>();
    ProgressDialog dialog;
    Context context;
    Button btn_start;
    ImageView iv;
    TextView start;
    iData idata;

    public URLAsync(Context con,Button bt,ImageView iv1,TextView tv,iData iDATA) {
        context = con;
        dialog = new ProgressDialog(con);
        btn_start=bt;
        iv=iv1;
        start = tv;
       // mquestions=questions;
        this.idata=iDATA;
    }
    public interface iData{
        void processFinish(ArrayList<Question> output);
    }

    @Override
    protected void onPreExecute() {
        this.dialog.setMessage("Loading Trivia");
        this.dialog.show();
    }
    HttpURLConnection con=null;
    ArrayList<String> input=new ArrayList<>();
    BufferedReader br=null;
    StringBuilder sb=null;
    String[] questions_list=null;
    Question question;



    String result=null;
    String[] input1=null;
    @Override
    protected ArrayList<String> doInBackground(String... strings) {
        Log.d("demo", "doInBackground: ");
        try {
            URL url = new URL(strings[0]);
            con = (HttpURLConnection) url.openConnection();
            con.connect();
            InputStream is = con.getInputStream();
            br = new BufferedReader(new InputStreamReader(is));
            String line = "";

            while((line=br.readLine())!=null){
                input.add(line);
                //Log.d("demo", line);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (con != null) {
                con.disconnect();
            }
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return input;
    }

    @Override
    protected void onPostExecute(ArrayList<String> strings) {
        Log.d("demo", "onPostExecute: ");
        dialog.dismiss();
        for(int i=0;i<strings.size();i++){
            Question question = new Question();
            questions_list=strings.get(i).split(";");
            question.setQuestion_id(Integer.parseInt(questions_list[0]));
            question.setQuestion(questions_list[1]);
            question.setImage_url(questions_list[2]);
            question.setAnswer_index(Integer.parseInt(questions_list[(questions_list.length-1)]));
           // Log.d("Demo", "onPostExecute: "+questions_list.length);
            ArrayList<String> option=new ArrayList<>();

            for(int j=3;j<(questions_list.length)-1;j++){
                option.add(questions_list[j]);

            }
            Log.d("size", "option size: "+option.size());
            question.setOptions(option);
            mquestions.add(question);

            Log.d("demo", "onPostExecute:"+question.getQuestion()+question.getImage_url()+"    "+question.getAnswer_index()+question.getOptions()+question.getQuestion_id());
            question=null;
            Log.d("demo", "onPostExecute: "+mquestions.size());

        }
//        Question que= new Question();
//        que.setQuestions(mquestions);
        btn_start.setEnabled(true);
        iv.setImageDrawable(context.getResources().getDrawable(R.drawable.trivia));
//        start.setText("Trivia Ready");
        start.setEnabled(true);
        idata.processFinish(mquestions);

    }
}
