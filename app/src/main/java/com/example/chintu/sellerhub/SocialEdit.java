package com.example.chintu.sellerhub;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import static com.example.chintu.sellerhub.NavigationActivity.artistid;


/**
 * Created by Vipul Chauhan on 3/18/2017.
 */

public class SocialEdit extends AppCompatActivity implements View.OnClickListener{

    EditText et_fb,et_insta,et_twitter;
    String e_fb,e_insta,e_twitter;
    Button btn_save;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.social_edit);
        et_fb=(EditText)findViewById(R.id.et_fb);
        et_insta=(EditText)findViewById(R.id.et_insta);
        et_twitter=(EditText)findViewById(R.id.et_twitter);
        btn_save=(Button) findViewById(R.id.btnse_save);
        loaddata();
        btn_save.setOnClickListener(this);
    }

    private void loaddata() {
        e_fb=getIntent().getStringExtra("fb");
        e_insta=getIntent().getStringExtra("insta");
        e_twitter=getIntent().getStringExtra("twitter");

        et_fb.setText(e_fb);
        et_insta.setText(e_insta);
        et_twitter.setText(e_twitter);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btnse_save){
            convertString();
        }
    }

    private void convertString() {
        e_fb=et_fb.getText().toString();
        e_insta=et_insta.getText().toString();
        e_twitter=et_twitter.getText().toString();
        if(validate()){
            Background background = new Background();
            background.execute(e_fb,e_insta,e_twitter,artistid);
        }
        else{
            Toast.makeText(getApplicationContext(), "Enter Data", Toast.LENGTH_LONG).show();
        }
    }
    private boolean validate() {
        boolean validation = true;
        if ("".equals(e_fb)) {
            et_fb.setError("Enter facebook link");
            validation = false;
        } else if ("".equals(e_insta)) {
            et_insta.setError("Enter insta link");
            validation = false;
        } else if ("".equals(e_twitter)) {
            et_twitter.setError("Enter twitter link");
            validation = false;
        }

        return validation;
    }

    private class Background extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            if (isConnection()) {
                progressDialog = new ProgressDialog(getApplication());
                progressDialog.setMessage("SUBMITTING..");
                progressDialog.setCancelable(false);
                progressDialog.show();
            } else {
                Toast.makeText(getApplicationContext(), "Make sure you are connected to Internet", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            e_fb= params[0];
            e_insta = params[1];
            e_twitter =params[2];
            artistid=params[3];


            try {
                URL url = new URL("update url required");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                String data = URLEncoder.encode("fb", "UTF-8") + "=" + URLEncoder.encode(e_fb, "UTF-8")
                        + "&" +

                        URLEncoder.encode("insta", "UTF-8") + "=" + URLEncoder.encode(e_insta, "UTF-8") + "&" +

                        URLEncoder.encode("twitter", "UTF-8") + "=" + URLEncoder.encode(e_twitter, "UTF-8") + "&" +

                        URLEncoder.encode("artist_id", "UTF-8") + "=" + URLEncoder.encode(artistid, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();
                InputStream IS = httpURLConnection.getInputStream();
                IS.close();
                //httpURLConnection.connect();
                httpURLConnection.disconnect();

                return "Updated";
            } catch (MalformedURLException e) {
                return "error";
            } catch (IOException e) {
                e.printStackTrace();

            }

            return null;

        }

        @Override
        protected void onPostExecute(String s) {


            progressDialog.dismiss();
            Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
            Intent i=new Intent(SocialEdit.this,NavigationActivity.class);
            startActivity(i);
        }
    }

    private boolean isConnection() {
        ConnectivityManager manage = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manage.getActiveNetworkInfo();
        if (info != null && info.isConnectedOrConnecting()) {

            return true;
        } else {
            return false;
        }
    }
}
