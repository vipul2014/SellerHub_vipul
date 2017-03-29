package com.example.chintu.sellerhub.orders_module;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.chintu.sellerhub.R;
import com.example.chintu.sellerhub.SlidingTabLayout;
import com.example.chintu.sellerhub.listing_module.AddProduct;
import com.example.chintu.sellerhub.listing_module.Listing_Instock_fragment;
import com.example.chintu.sellerhub.listing_module.Listing_ViewPagerAdapter;
import com.example.chintu.sellerhub.registration_module.FragmentTwo;

/**
 * Created by hp lap on 28-03-2017.
 */

public class orders_fragment extends Fragment implements View.OnClickListener {

    ViewPager pager;
    Orders_ViewPagerAdapter adapter;
    SlidingTabLayout tabs;
    CharSequence Titles[] = {"PENDING","SHIP","COMPLETED"};
    int Numboftabs = 3;

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.orders_activity, container, false);
        getActivity().setTitle("Orders");
        adapter = new Orders_ViewPagerAdapter(getActivity().getSupportFragmentManager(), Titles, Numboftabs);
        tabs = (SlidingTabLayout)view.findViewById(R.id.orders_tab);


        tabs.setSelectedIndicatorColors(getResources().getColor(R.color.white));

        pager = (ViewPager)view.findViewById(R.id.orders_v_pager);
        pager.setAdapter(adapter);

        // Assiging the Sliding Tab Layout View

        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width


        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(pager);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){




        }

    }
}
