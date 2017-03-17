package com.example.chintu.sellerhub;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

/**
 * Created by Vipul Chauhan on 2/23/2017.
 */

public class BasicEdit extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemSelectedListener{

    EditText et_name,et_email,et_phone,et_address,et_city,et_postalCode;
    RadioGroup rg_gender;
    Spinner s_state;
    Button btn_save;
    String name,gender,DOB,contact,email,address,description,state,postal_code;

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

        btn_save.setOnClickListener(this);
        s_state.setOnItemSelectedListener(this);
    }

    private void loadData() {
        name=getIntent().getStringExtra("name");
        et_name.setText(name);
        gender=getIntent().getStringExtra("gender");

        DOB=getIntent().getStringExtra("DOB");
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
        String city=et_city.getText().toString();

        builder.append(address,)


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        state=parent.getSelectedItem().toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
