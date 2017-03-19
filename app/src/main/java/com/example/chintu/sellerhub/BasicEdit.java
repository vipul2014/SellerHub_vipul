package com.example.chintu.sellerhub;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import static com.example.chintu.sellerhub.NavigationActivity.artistid;

import static com.example.chintu.sellerhub.R.id.bankname;
import static com.example.chintu.sellerhub.R.id.ifsc;

/**
 * Created by Vipul Chauhan on 2/23/2017.
 */

public class BasicEdit extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemSelectedListener
{

    EditText et_name,et_email,et_phone,et_address,et_city,et_postalCode,et_dob;
    RadioGroup rg_gender;
    RadioButton gend;
    Spinner s_state;
    Button btn_save;
    String name,gender,contact,email,address,description,state,postal_code,DOB,city;
    ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.basic_edit);

        et_name=(EditText)findViewById(R.id.etbae_name);
        et_email=(EditText)findViewById(R.id.etbae_email);
        et_phone=(EditText)findViewById(R.id.etbae_phone);
        et_address=(EditText)findViewById(R.id.etbae_address);
        et_city=(EditText)findViewById(R.id.etbae_city);
        et_postalCode=(EditText)findViewById(R.id.etbae_postal_code);
        rg_gender=(RadioGroup) findViewById(R.id.rg_gender);
        s_state=(Spinner)findViewById(R.id.etbae_state);
        btn_save=(Button)findViewById(R.id.etbae_save);
        et_dob=(EditText)findViewById(R.id.etbae_dob);

        loadData();

        btn_save.setOnClickListener(this);
        s_state.setOnItemSelectedListener(this);
    }

    private void loadData() {
        name=getIntent().getStringExtra("name");
        et_name.setText(name);
        gender=getIntent().getStringExtra("gender");

        DOB=getIntent().getStringExtra("DOB");
        et_dob.setText(DOB);
        contact=getIntent().getStringExtra("contact");
        description=getIntent().getStringExtra("descriptiom");
        email=getIntent().getStringExtra("email");
        address=getIntent().getStringExtra("address");

        String AddressString = address;
        String[] subAddress = AddressString.split(":");
        et_address.setText(subAddress[0]);
        et_city.setText(subAddress[1]);
        String compareValue = subAddress[2];
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.state_arrays, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s_state.setAdapter(adapter);
        if (!compareValue.equals(null)) {
            int spinnerPosition = adapter.getPosition(compareValue);
            s_state.setSelection(spinnerPosition);
        }
        et_postalCode.setText(subAddress[3]);


    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.etbae_save){
            convertString();
        }
    }

    private void convertString() {
        name=et_name.getText().toString();
        email=et_email.getText().toString();
        contact=et_phone.getText().toString();
        StringBuilder builder=new StringBuilder();
        String delimiter=":";
        address=et_address.getText().toString();
        city=et_city.getText().toString();
        postal_code=et_postalCode.getText().toString();
        int selectId = rg_gender.getCheckedRadioButtonId();
        gend = (RadioButton) findViewById(selectId);
        gender = gend.getText().toString();
        DOB=et_dob.getText().toString();

        builder.append(address);
        builder.append(delimiter);
        builder.append(city);
        builder.append(delimiter);
        builder.append(state);
        builder.append(delimiter);
        builder.append(postal_code);
        address=builder.toString();
        if(validate()){
            Background background = new Background();
            background.execute(name,gender,email,DOB,contact,address,artistid);
        }
        else{
            Toast.makeText(getApplicationContext(), "Enter Data", Toast.LENGTH_LONG).show();
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
            name= params[0];
            gender = params[1];
            email =params[2];
            DOB = params[3];
            contact=params[4];
            address=params[5];
            artistid=params[6];


            try {
                URL url = new URL("update url required");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                String data = URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8")
                        + "&" +

                        URLEncoder.encode("gender", "UTF-8") + "=" + URLEncoder.encode(gender, "UTF-8") + "&" +

                        URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8") + "&" +

                        URLEncoder.encode("DOB", "UTF-8") + "=" + URLEncoder.encode(DOB, "UTF-8") + "&" +

                        URLEncoder.encode("contact", "UTF-8") + "=" + URLEncoder.encode(contact, "UTF-8") + "&" +

                        URLEncoder.encode("address", "UTF-8") + "=" + URLEncoder.encode(address, "UTF-8") + "&" +

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
            Intent i=new Intent(BasicEdit.this,NavigationActivity.class);
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


    private boolean validate() {
        boolean validation = true;
        if ("".equals(name)) {
            et_name.setError("Enter name");
            validation = false;
        } else if ("".equals(address)) {
            et_address.setError("Enter the address");
            validation = false;
        } else if ("".equals(email) || validemail(email)) {
            et_email.setError("Enter valid email");
            validation = false;
        } else if ("".equals(contact)) {
            et_phone.setError("Enter the mobile number");
            validation = false;
        } else if ("State".equals(state)) {
            TextView errorText = (TextView) s_state.getSelectedView();
            errorText.setError("");
            errorText.setTextColor(Color.RED);
            errorText.setText("State not selected");
            validation = false;
        } else if ("".equals(DOB)) {
            et_dob.setError("Enter DOB");
            validation = false;
        } else if ("".equals(city)) {
            et_city.setError("Enter city");
            validation = false;
        } else if ("".equals(postal_code)) {
            et_postalCode.setError("Enter postal code");
            validation = false;
        }

            return validation;
        }




        @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        state=parent.getSelectedItem().toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    private boolean validemail(String email) {
        boolean sucess = true;
        for (int i = 0; i < email.length(); i++) {
            if (email.charAt(i) == '@') {
                sucess = false;
            }
        }
        return sucess;
    }
    }
