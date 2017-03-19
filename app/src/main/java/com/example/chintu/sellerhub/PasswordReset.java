package com.example.chintu.sellerhub;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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

public class PasswordReset extends AppCompatActivity implements View.OnClickListener{

    EditText et_curpss,et_nwpss,et_confpss;
    Button btn_save;
    String e1_curpss,e2_curpss,e_nwpss,e_confpss;
    String uri;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.psswrd_reset);
        et_confpss=(EditText)findViewById(R.id.confpss);
        et_curpss=(EditText)findViewById(R.id.curpss);
        et_nwpss=(EditText)findViewById(R.id.newpss);
        btn_save=(Button)findViewById(R.id.btnpr_save);
        btn_save.setOnClickListener(this);
        fetchCurrpass();
    }

    private void fetchCurrpass() {
       // uri = "http://artbirdz.hol.es/sellerApp/registration/fetch_artist_id.php?id=" + e_email + "";
        new JsonTask().execute(uri);
    }

    private class JsonTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer = new StringBuffer();
                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\t");
                }
                return buffer.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            e1_curpss = result.trim();
        }
    }
    @Override
    public void onClick(View v) {
      if(v.getId()==R.id.btnpr_save){
          convertString();
      }
    }

    private void convertString() {
        e_confpss=et_confpss.getText().toString();
        e_nwpss=et_nwpss.getText().toString();
        e2_curpss=et_curpss.getText().toString();
        if(validate()){
            Background background = new Background();
            background.execute(e_nwpss,artistid);
        }
        else{
            Toast.makeText(getApplicationContext(), "Enter Data", Toast.LENGTH_LONG).show();
        }
    }

    private boolean validate() {
        boolean validation = true;
        if (!(e1_curpss.equals(e2_curpss))) {
            et_curpss.setError("Incorrect current password");
            validation = false;
        } else if ("".equals(e_confpss)) {
            et_confpss.setError("Enter confirm password");
            validation = false;
        } else if (!(e_nwpss.equals(e_confpss))) {
            et_nwpss.setError("Password didnt matched");
            et_nwpss.setText("");
            et_confpss.setText("");
            validation = false;
        } else if ("".equals(e_nwpss)) {
            et_nwpss.setError("Enter new password");
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
            e_nwpss= params[0];
            artistid=params[1];


            try {
                URL url = new URL("update url required");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                String data = URLEncoder.encode("new password", "UTF-8") + "=" + URLEncoder.encode(e_nwpss, "UTF-8")
                         + "&" +

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
            Intent i=new Intent(PasswordReset.this,NavigationActivity.class);
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
