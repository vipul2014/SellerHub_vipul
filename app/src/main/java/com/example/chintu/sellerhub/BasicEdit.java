package com.example.chintu.sellerhub;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

/**
 * Created by Vipul Chauhan on 2/23/2017.
 */

public class BasicEdit extends AppCompatActivity {

    EditText name,email,phone,address,city,postalCode;
    RadioGroup gender;
    Spinner state;
    Button save;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basic_edit);

        name=(EditText)findViewById(R.id.etbae_name);
        email=(EditText)findViewById(R.id.etbae_email);
        phone=(EditText)findViewById(R.id.etbae_phone);
        address=(EditText)findViewById(R.id.etbae_address);
        city=(EditText)findViewById(R.id.etbae_city);
        postalCode=(EditText)findViewById(R.id.etbae_postal_code);
        gender=(RadioGroup) findViewById(R.id.rg_gender);
        state=(Spinner)findViewById(R.id.etbae_state);
        save=(Button)findViewById(R.id.etbae_save);
    }
}
