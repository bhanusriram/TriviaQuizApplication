/*
Homework 3
Trivia Application
Bhanu Teja Sriram
Tejaswini Naredla
*/

package com.example.cherr.triviaapplication;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;



public class ImageAsync extends AsyncTask<String,Void,Bitmap> {
    ProgressBar pb;
    ImageView iv;
    TextView load;
    Context con;
    int height;

    public ImageAsync(ImageView iv, ProgressBar ipb, TextView l,Context context) {
        this.iv = iv;
        pb=ipb;
        load=l;
        con = context;


    }

    @Override
    protected void onPreExecute() {
        iv.setImageBitmap(null);
        //this.pb.setTitle("Loading Image");
        this.pb.setVisibility(View.VISIBLE);
        load.setVisibility(View.VISIBLE);
    }

    @Override
    protected Bitmap doInBackground(String... urls) {
        HttpURLConnection con=null;
        Bitmap image = null;
        try {
            URL url=new URL(urls[0]);
            con= (HttpURLConnection) url.openConnection();
            con.connect();
            if(con.getResponseCode()==HttpURLConnection.HTTP_OK) {
                image = BitmapFactory.decodeStream(con.getInputStream());
            }

        }
            catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (con != null) {
                con.disconnect();
            }
        }
        return image;
    }


    @Override
    protected void onPostExecute(Bitmap bitmap) {
        pb.setVisibility(View.INVISIBLE);
        load.setVisibility(View.INVISIBLE);
        iv.setImageBitmap(bitmap);

    }
}
