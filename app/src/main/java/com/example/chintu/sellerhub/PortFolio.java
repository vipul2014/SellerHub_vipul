package com.example.chintu.sellerhub;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import de.greenrobot.event.EventBus;

/**
 * Created by Vipul Chauhan on 2/8/2017.
 */
public class PortFolio extends AppCompatActivity implements View.OnClickListener{

    ViewPager pager;
    ViewPagerAdapter adapter;
    SlidingTabLayout tabs;
    CharSequence Titles[] = {"Product 1","Product 2","Product 3"};
    int Numboftabs = 3;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.port_folio_main);
        adapter = new ViewPagerAdapter(getSupportFragmentManager(), Titles, Numboftabs);


        pager = (ViewPager) findViewById(R.id.v_pager);
        pager.setAdapter(adapter);

        // Assiging the Sliding Tab Layout View
        tabs = (SlidingTabLayout) findViewById(R.id.tab);
        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width


        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(pager);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



    }


    @Override
    public void onClick(View v) {

    }
}
