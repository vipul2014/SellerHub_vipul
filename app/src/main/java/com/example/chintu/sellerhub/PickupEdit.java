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

public class PickupEdit extends AppCompatActivity {

    EditText flat,road,area,town,postalCode;
    Spinner state;
    Button save;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.pickup_edit);
        flat=(EditText)findViewById(R.id.etpe_flat);
        road=(EditText)findViewById(R.id.etpe_road);
        area=(EditText)findViewById(R.id.etpe_area);
        town=(EditText)findViewById(R.id.etpe_town);
        postalCode=(EditText)findViewById(R.id.etpe_postal_code);
        state=(Spinner) findViewById(R.id.etpe_state);
        save=(Button) findViewById(R.id.etpe_save);
    }
}
