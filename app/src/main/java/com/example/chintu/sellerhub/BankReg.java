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
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
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
 * Created by Vipul Chauhan on 2/5/2017.
 */
public class  BankReg extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemSelectedListener{

    AutoCompleteTextView acc_no;
    EditText ifsc;
    EditText name;
    TextView sample;
    Spinner bank;

    ProgressDialog progressDialog;
    boolean dataAdd=false;

    Button submit;

    String e_ifsc,e_acc_no,e_name,e_bank,e_artist_id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bank_register);

        acc_no=(AutoCompleteTextView)findViewById(R.id.t_acno);
        ifsc=(EditText)findViewById(R.id.t_ifsc);
        name=(EditText)findViewById(R.id.t_holdername);
        sample=(TextView)findViewById(R.id.sample);
        bank=(Spinner)findViewById(R.id.spin_bank);
        submit=(Button)findViewById(R.id.btn_login) ;

        //sharedPreferences=this.getSharedPreferences(myPreferences, Context.MODE_APPEND);


        submit.setOnClickListener(this);

        bank.setOnItemSelectedListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn_login){
            upload();
        }

    }

    private void upload() {
        e_acc_no=acc_no.getText().toString();
        e_ifsc=ifsc.getText().toString();
        e_name=name.getText().toString();
        //e_artist_id=sharedPreferences.getString("artist_id","null");
        final SharedPreferences mSharedPreference= PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        e_artist_id=(mSharedPreference.getString("artist_id", "Default_Value"));
        boolean validation = uservalidation();
        if (validation) {
            BankReg.Background background = new BankReg.Background();
            background.execute(e_name, e_acc_no, e_ifsc, e_bank, e_artist_id);
        }
        else{
            Toast.makeText(getApplicationContext(), "Enter Data", Toast.LENGTH_LONG).show();
        }

            //sample.setText(e_artist_id);

    }

    private boolean uservalidation() {
        boolean validation = true;
        if ("".equals(e_name)) {
            name.setError("Enter Account Holder Name");
            validation = false;
        } else if ("".equals(e_acc_no)) {
            acc_no.setError("Enter Account Number");
            validation = false;
        } else if ("".equals(e_ifsc)) {
            ifsc.setError("Enter IFSC Code");
            validation = false;
        } else if ("Select Bank".equals(e_bank)) {
            TextView errorText = (TextView)bank.getSelectedView();
            errorText.setError("");
            errorText.setTextColor(Color.RED);
            errorText.setText("Bank not Selected");
            validation = false;
        }
        else if ("Default_Value".equals(e_artist_id)) {
            sample.setText("Error in artist id");
            validation = false;
        }


        return validation;
    }

    public boolean isConnection() {

        ConnectivityManager manage = (ConnectivityManager) BankReg.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manage.getActiveNetworkInfo();
        if (info != null && info.isConnectedOrConnecting()) {

            return true;
        } else {
            return false;
        }

    }

    private class Background extends AsyncTask<String, String, String>{

        @Override
        protected void onPreExecute() {
            if (isConnection()) {
                progressDialog = new ProgressDialog(BankReg.this);
                progressDialog.setMessage("SUBMITTING..");
                progressDialog.setCancelable(false);
                progressDialog.show();
            } else {
                Toast.makeText(getApplicationContext(), "Make sure you are connected to Internet", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            e_name = params[0];
            e_acc_no = params[1];
            e_ifsc = params[2];
            e_bank = params[3];
            e_artist_id = params[4];

            try {
                URL url = new URL("http://artbirdz.hol.es/sellerApp/registration/bank_reg.php");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                String data = URLEncoder.encode("ac_name", "UTF-8") + "=" + URLEncoder.encode(e_name, "UTF-8")
                        + "&" +

                        URLEncoder.encode("ac_number", "UTF-8") + "=" + URLEncoder.encode(e_acc_no, "UTF-8") + "&" +

                        URLEncoder.encode("ifsc", "UTF-8") + "=" + URLEncoder.encode(e_ifsc, "UTF-8") + "&" +

                        URLEncoder.encode("bank_name", "UTF-8") + "=" + URLEncoder.encode(e_bank, "UTF-8") + "&" +

                        URLEncoder.encode("artist_id", "UTF-8") + "=" + URLEncoder.encode(e_artist_id, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();
                InputStream IS = httpURLConnection.getInputStream();
                IS.close();
                //httpURLConnection.connect();
                httpURLConnection.disconnect();
                dataAdd=true;
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
            Intent i=new Intent(BankReg.this,PortFolio.class);
            startActivity(i);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        e_bank= parent.getSelectedItem().toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
