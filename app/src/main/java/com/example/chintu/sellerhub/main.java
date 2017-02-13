package com.example.chintu.sellerhub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


public class main extends AppCompatActivity implements View.OnClickListener
{
    private Button signUp;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        signUp= (Button)findViewById(R.id.signup_bttn);
        signUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.signup_bttn){
            Intent i= new Intent(main.this,SignUp.class);
            startActivity(i);
        }
    }
}
