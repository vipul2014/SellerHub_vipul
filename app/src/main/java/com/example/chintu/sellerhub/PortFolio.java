package com.example.chintu.sellerhub;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

/**
 * Created by Vipul Chauhan on 2/8/2017.
 */
public class PortFolio extends AppCompatActivity implements View.OnClickListener{

    ViewPager pager;
    ViewPagerAdapter adapter;
    SlidingTabLayout tabs;
    CharSequence Titles[] = {"Product 1","Product 2","Product 3"};
    int Numboftabs = 3;

    Button bg1,bg2,bg3,bc1,bc2,bc3,p1,p2,p3;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.port_folio_main);
        adapter = new ViewPagerAdapter(getSupportFragmentManager(), Titles, Numboftabs);

        //bg1=(Button)findViewById(R.id.galry_btn1);
        //bg2=(Button)findViewById(R.id.galry_btn2);
        //bg2=(Button)findViewById(R.id.galry_btn2);
        //p1=(Button)findViewById(R.id.btn_p1);
        //p2=(Button)findViewById(R.id.btn_p2);
        //p3=(Button)findViewById(R.id.btn_p3);
        // Assigning ViewPager View and setting the adapter
        pager = (ViewPager) findViewById(R.id.v_pager);
        pager.setAdapter(adapter);

        // Assiging the Sliding Tab Layout View
        tabs = (SlidingTabLayout) findViewById(R.id.tab);
        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width


        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(pager);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //p1.setOnClickListener(this);
        //p2.setOnClickListener(this);
       // p3.setOnClickListener(this);
        //bg1.setOnClickListener(this);
        //bg2.setOnClickListener(this);
        //bg3.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_p1:
                FragmentActivity tab1 = new FragmentActivity();
                break;
            case R.id.btn_p2:
                FragmentTwo tab2 = new FragmentTwo();
                break;

        }

    }
}
