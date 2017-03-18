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
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
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


/**
 * Created by Vipul Chauhan on 2/23/2017.
 */

public class PickupEdit extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemSelectedListener {

    EditText flat,road,area,town,postalCode;
    Spinner state;
    Button save;
    String pickuplocation;
    String e_flat,e_road,e_area,e_town,e_postalCode,e_state;
    ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pickup_edit);
        flat=(EditText)findViewById(R.id.etpe_flat);
        road=(EditText)findViewById(R.id.etpe_road);
        area=(EditText)findViewById(R.id.etpe_area);
        town=(EditText)findViewById(R.id.etpe_town);
        postalCode=(EditText)findViewById(R.id.etpe_postal_code);
        state=(Spinner) findViewById(R.id.etpe_state);
        save=(Button) findViewById(R.id.etpe_save);
        pickuplocation=getIntent().getStringExtra("pickuplocation");
        String AddressString = pickuplocation;
        String[] subAddress = AddressString.split(":");
        flat.setText(subAddress[0]);
        road.setText(subAddress[1]);
        area.setText(subAddress[2]);
        town.setText(subAddress[3]);
        String compareValue = subAddress[4];
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.state_arrays, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        state.setAdapter(adapter);
        if (!compareValue.equals(null)) {
            int spinnerPosition = adapter.getPosition(compareValue);
            state.setSelection(spinnerPosition);
        }
        postalCode.setText(subAddress[5]);
        save.setOnClickListener(this);
        state.setOnItemSelectedListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.etpe_save){
            convertString();
        }
    }

    private void convertString() {
        e_flat=flat.getText().toString();
        e_road=road.getText().toString();
        e_area=area.getText().toString();
        e_town=town.getText().toString();
        e_postalCode=postalCode.getText().toString();

        StringBuilder builder=new StringBuilder();
        String delimiter=":";

        builder.append(e_flat);
        builder.append(delimiter);
        builder.append(e_road);
        builder.append(delimiter);
        builder.append(e_area);
        builder.append(delimiter);
        builder.append(e_town);
        builder.append(delimiter);
        builder.append(e_state);
        builder.append(delimiter);
        builder.append(e_postalCode);
        pickuplocation=builder.toString();
        if(validate()){
            Background background = new Background();
            background.execute(pickuplocation,artistid);
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
            pickuplocation=params[0];
            artistid=params[1];


            try {
                URL url = new URL("update url required");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                String data = URLEncoder.encode("pickuplocation", "UTF-8") + "=" + URLEncoder.encode(pickuplocation, "UTF-8")
                        + "&" +

                        URLEncoder.encode("artistid", "UTF-8") + "=" + URLEncoder.encode(artistid, "UTF-8");
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
            Intent i=new Intent(PickupEdit.this,NavigationActivity.class);
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
        if ("".equals(e_flat)) {
            flat.setError("Enter flat/room/door/block");
            validation = false;
        } else if ("".equals(e_road)) {
            road.setError("Enter road/street/lane ");
            validation = false;

        } else if ("".equals(e_area)) {
            area.setError("Enter area/locality");
            validation = false;
        }  else if ("".equals(e_town)) {
            town.setError("Enter town/city/district");
            validation = false;
        }else if ("State".equals(e_state)) {
            TextView errorText = (TextView) state.getSelectedView();
            errorText.setError("");
            errorText.setTextColor(Color.RED);
            errorText.setText("State not selected");
            validation = false;
        } else if ("".equals(e_postalCode)) {
            postalCode.setError("Enter postal code");
            validation = false;
        }

        return validation;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        e_state=parent.getSelectedItem().toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
