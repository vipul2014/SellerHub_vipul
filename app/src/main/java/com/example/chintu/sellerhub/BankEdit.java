package com.example.chintu.sellerhub;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

/**
 * Created by Vipul Chauhan on 2/23/2017.
 */

public class BankEdit extends AppCompatActivity implements View.OnClickListener{
    EditText et_accNo,et_ifsc,et_accName;
    Spinner s_bank;
    Button btn_save;
    String accountNumber,bankname,ifsc,accountname;
    ProgressDialog progressDialog;
    String e_artist_id;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bank_edit);
        et_accNo=(EditText)findViewById(R.id.etbe_accNo);
        s_bank=(Spinner) findViewById(R.id.etbe_bank);
        et_ifsc=(EditText)findViewById(R.id.etbe_ifsc);
        et_accName=(EditText)findViewById(R.id.etbe_accName);
        btn_save=(Button)findViewById(R.id.btnbe_save);

        loadData();

        btn_save.setOnClickListener(this);


    }

    private void loadData() {
        accountname=getIntent().getStringExtra("accountholdername");
        bankname=getIntent().getStringExtra("bankname");
        ifsc=getIntent().getStringExtra("ifsc");
        accountNumber=getIntent().getStringExtra("accountnumber");
        et_accName.setText(accountname);
        et_accNo.setText(accountNumber);
        et_ifsc.setText(ifsc);
        String compareValue = bankname;
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.bank_arrays, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s_bank.setAdapter(adapter);
        if (!compareValue.equals(null)) {
            int spinnerPosition = adapter.getPosition(compareValue);
            s_bank.setSelection(spinnerPosition);
        }

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btnbe_save){
            upload();

        }
    }
    private void upload() {
        accountNumber=et_accNo.getText().toString();
        ifsc=et_ifsc.getText().toString();
        accountname=et_accName.getText().toString();
        //e_artist_id=sharedPreferences.getString("artist_id","null");

        final SharedPreferences mSharedPreference= PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        e_artist_id=(mSharedPreference.getString("artist_id", "Default_Value"));

        boolean validation = uservalidation();
        if (validation) {
            Background background = new Background();
            background.execute(accountNumber,bankname,ifsc,accountname,e_artist_id);
        }
        else{
            Toast.makeText(getApplicationContext(), "Enter Data", Toast.LENGTH_LONG).show();
        }

        //sample.setText(e_artist_id);

    }

    private boolean uservalidation() {
        boolean validation = true;
        if ("".equals(et_accName)) {
            et_accName.setError("Enter Account Holder Name");
            validation = false;
        } else if ("".equals(et_accNo)) {
            et_accNo.setError("Enter Account Number");
            validation = false;
        } else if ("".equals(et_ifsc)) {
            et_ifsc.setError("Enter IFSC Code");
            validation = false;
        } else if ("Select Bank".equals(s_bank)) {
            TextView errorText = (TextView)s_bank.getSelectedView();
            errorText.setError("");
            errorText.setTextColor(Color.RED);
            errorText.setText("Bank not Selected");
            validation = false;
        }



        return validation;
    }

    public boolean isConnection() {

        ConnectivityManager manage = (ConnectivityManager) BankEdit.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manage.getActiveNetworkInfo();
        if (info != null && info.isConnectedOrConnecting()) {

            return true;
        } else {
            return false;
        }

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
            accountNumber = params[0];
            bankname = params[1];
            ifsc= params[2];
            accountname = params[3];
            e_artist_id=params[4];


            try {
                URL url = new URL("update url required");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                String data = URLEncoder.encode("ac_name", "UTF-8") + "=" + URLEncoder.encode(accountname, "UTF-8")
                        + "&" +

                        URLEncoder.encode("ac_number", "UTF-8") + "=" + URLEncoder.encode(accountNumber, "UTF-8") + "&" +

                        URLEncoder.encode("ifsc", "UTF-8") + "=" + URLEncoder.encode(ifsc, "UTF-8") + "&" +

                        URLEncoder.encode("bank_name", "UTF-8") + "=" + URLEncoder.encode(bankname, "UTF-8") + "&" +

                        URLEncoder.encode("artist_id", "UTF-8") + "=" + URLEncoder.encode(e_artist_id, "UTF-8");
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
            Intent i=new Intent(BankEdit.this,NavigationActivity.class);
            startActivity(i);
        }
    }

   }
