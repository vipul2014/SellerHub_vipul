package com.example.chintu.sellerhub;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * Created by Vipul Chauhan on 2/23/2017.
 */

public class BankEdit extends AppCompatActivity {
    EditText accNo,ifsc,accName;
    Spinner bank;
    Button save;
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.bank_edit);
        accNo=(EditText)findViewById(R.id.etbe_accNo);
        bank=(Spinner) findViewById(R.id.etbe_bank);
        ifsc=(EditText)findViewById(R.id.etbe_ifsc);
        accName=(EditText)findViewById(R.id.etbe_accName);
        save=(Button)findViewById(R.id.btnbe_save);


    }
}
