package com.example.chintu.sellerhub;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
/**
 * Created by Vipul Chauhan on 1/30/2017.
 */
public class SignUp extends AppCompatActivity implements View.OnClickListener {


    public static final int RESULT_LOAD_IMG = 2;


    ProgressDialog progressDialog;

    String imgDecodableString;

    AutoCompleteTextView name;
    AutoCompleteTextView email;
    AutoCompleteTextView phone;
    AutoCompleteTextView address;
    RadioGroup gender;
    RadioButton rg_gender;
    ImageView userPic;
    Button getImage, next;

    TextView t;




    String uri,artist_id;

    String e_name, e_email, e_phone, e_address, e_gender, e_userPic;
    boolean dataAdd=false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal);

        getImage = (Button) findViewById(R.id.get_img);
        next = (Button) findViewById(R.id.btn_pers_next);

        name = (AutoCompleteTextView) findViewById(R.id.tv_name);
        email = (AutoCompleteTextView) findViewById(R.id.tv_email);
        phone = (AutoCompleteTextView) findViewById(R.id.tv_phone);
        address = (AutoCompleteTextView) findViewById(R.id.tv_adress);
        gender = (RadioGroup) findViewById(R.id.rg_gender);
        userPic = (ImageView) findViewById(R.id.i_userpic);

        t = (TextView) findViewById(R.id.sample);



        getImage.setOnClickListener(this);
        next.setOnClickListener(this);

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onRestart() {
        super.onRestart();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.get_img:
                loadImage();

                break;
            case R.id.btn_pers_next:
                upload();

        }
    }

    private void loadImage() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
    }

    private void upload() {
        e_name = name.getText().toString();
        e_email = email.getText().toString();
        e_address = address.getText().toString();
        e_phone = phone.getText().toString();
        int selectId = gender.getCheckedRadioButtonId();
        rg_gender = (RadioButton) findViewById(selectId);
        e_gender = rg_gender.getText().toString();
        Bitmap bitmap = ((BitmapDrawable) userPic.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        e_userPic = Base64.encodeToString(imageBytes, Base64.DEFAULT);

        boolean validation = uservalidation();
        if (validation) {
            Background background = new Background();
            background.execute(e_name, e_gender, e_address, e_phone, e_userPic, e_email);

        } else {
            Toast.makeText(getApplicationContext(), "Enter Data", Toast.LENGTH_LONG).show();


        }


    }


    private boolean uservalidation() {
        boolean validation = true;
        if ("".equals(e_name)) {
            name.setError("Enter name");
            validation = false;
        } else if ("".equals(e_address)) {
            address.setError("Enter the address");
            validation = false;
        } else if ("".equals(e_email)||validemail(e_email)) {
            email.setError("Enter valid email");
            validation = false;
        } else if ("".equals(e_phone)) {
            phone.setError("Enter the mobile number");
            validation = false;
        }


        return validation;

    }

    private boolean validemail(String e_email) {
        boolean sucess=true;
        for(int i=0;i<e_email.length();i++){
            if(e_email.charAt(i)=='@'){
                sucess=false;
            }
        }
        return sucess;
    }


    private class Background extends AsyncTask<String, String, String> {

        protected String doInBackground(String... params) {
            e_name = params[0];
            e_gender = params[1];
            e_address = params[2];
            e_phone = params[3];
            e_userPic = params[4];
            e_email = params[5];

            try {
                URL url = new URL("http://artbirdz.hol.es/sellerApp/registration/reg_artist.php");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                String data = URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(e_name, "UTF-8")
                        + "&" +

                        URLEncoder.encode("gender", "UTF-8") + "=" + URLEncoder.encode(e_gender, "UTF-8") + "&" +

                        URLEncoder.encode("address", "UTF-8") + "=" + URLEncoder.encode(e_address, "UTF-8") + "&" +

                        URLEncoder.encode("contact_no", "UTF-8") + "=" + URLEncoder.encode(e_phone, "UTF-8") + "&" +

                        URLEncoder.encode("artist_img", "UTF-8") + "=" + URLEncoder.encode(e_userPic, "UTF-8") + "&" +

                        URLEncoder.encode("email_id", "UTF-8") + "=" + URLEncoder.encode(e_email, "UTF-8");
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
                Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG);
            } catch (IOException e) {
                e.printStackTrace();

            }

            return null;
        }


        protected void onPreExecute() {
            if (isConnection()) {
                progressDialog = new ProgressDialog(SignUp.this);
                progressDialog.setMessage("SUBMITTING..");
                progressDialog.setCancelable(false);
                progressDialog.show();
            } else {
                Toast.makeText(getApplicationContext(), "Make sure you are connected to Internet", Toast.LENGTH_LONG).show();
            }

        }


        protected void onPostExecute(String s) {
            progressDialog.dismiss();
            Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
            fetchID();
        }

    }

    private void fetchID() {
        if(dataAdd) {
            uri = "http://artbirdz.hol.es/sellerApp/registration/fetch_artist_id.php?email=" + e_email + "";
            new JsonTask().execute(uri);


        }
    }

    private boolean isConnection() {

        ConnectivityManager manage = (ConnectivityManager) SignUp.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manage.getActiveNetworkInfo();
        if (info != null && info.isConnectedOrConnecting()) {

            return true;
        } else {
            return false;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RESULT_LOAD_IMG) {
            if (resultCode == RESULT_OK) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgDecodableString = cursor.getString(columnIndex);
                cursor.close();
                userPic.setImageBitmap(BitmapFactory
                        .decodeFile(imgDecodableString));


            } else {
                Toast.makeText(getApplicationContext(), "Request Cancelled", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class JsonTask extends AsyncTask<String,String,String> {
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
            protected void onPostExecute(String result){
                super.onPostExecute(result);
                artist_id=result.substring(27,(result.length()-5));
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(SignUp.this);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("artist_id", artist_id);
                editor.commit();
                Intent i= new Intent(SignUp.this,BankReg.class);
                startActivity(i);
        }

    }
}



