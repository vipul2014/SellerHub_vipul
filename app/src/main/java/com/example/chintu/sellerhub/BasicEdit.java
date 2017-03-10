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

    EditText et_name,et_email,et_phone,et_address,et_city,et_postalCode;
    RadioGroup rg_gender;
    Spinner s_state;
    Button btn_save;
    String name,gender,DOB,contact,email,address,description;

    @Override
    public void onCreate(Bundle savedInstanceState) {
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

        loadData();
    }

    private void loadData() {
        name=getIntent().getStringExtra("name");
        gender=getIntent().getStringExtra("gender");
        DOB=getIntent().getStringExtra("DOB");
        contact=getIntent().getStringExtra("contact");
        description=getIntent().getStringExtra("descriptiom");
        email=getIntent().getStringExtra("email");
        address=getIntent().getStringExtra("address");
        String AddressString = address;
        String[] subAddress = AddressString.split(":");
        et_address.setText(subAddress[0]); // this will contain "Fruit"
        et_city.setText(subAddress[1]);
        // this will contain " they taste good"
    }
}
