package com.example.chintu.sellerhub;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import static android.content.DialogInterface.BUTTON_NEGATIVE;
import static android.content.DialogInterface.BUTTON_NEUTRAL;
import static android.content.DialogInterface.BUTTON_POSITIVE;

/**
 * Created by Vipul Chauhan on 3/12/2017.
 */

public class AddProduct extends AppCompatActivity implements View.OnClickListener{
    AutoCompleteTextView et_title,et_surface,et_size,et_createdon,et_description,et_sellingprice;
    Spinner s_category,s_style;
    RadioGroup rg_packing;
    ImageView imgbig,img1,img2,img3,img4,img5;
    Button getpic,save;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_product);

        et_title=(AutoCompleteTextView) findViewById(R.id.act_title);
        et_surface=(AutoCompleteTextView) findViewById(R.id.act_surface);
        et_size=(AutoCompleteTextView) findViewById(R.id.act_size);
        et_createdon=(AutoCompleteTextView) findViewById(R.id.act_createdon);
        et_description=(AutoCompleteTextView) findViewById(R.id.act_description);
        et_sellingprice=(AutoCompleteTextView) findViewById(R.id.act_sellingprice);
        s_style=(Spinner)findViewById(R.id.s_style);
        s_category=(Spinner)findViewById(R.id.s_category);
        rg_packing=(RadioGroup)findViewById(R.id.rg_packing);
        imgbig=(ImageView)findViewById(R.id.big_1);
        img1=(ImageView)findViewById(R.id.img1);
        img2=(ImageView)findViewById(R.id.img2);
        img3=(ImageView)findViewById(R.id.img3);
        img4=(ImageView)findViewById(R.id.img4);
        img5=(ImageView)findViewById(R.id.img5);
        getpic=(Button)findViewById(R.id.btn_getpic);
        save=(Button)findViewById(R.id.btn_save);
        getpic.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_getpic: AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setMessage("Are you sure You wanted to make decision");
                        alertDialogBuilder.setPositiveButton("yes",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        Toast.makeText(AddProduct.this,"You clicked yes button",Toast.LENGTH_LONG).show();
                                    }
                                });

                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }
                );

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

        }
    }

}


