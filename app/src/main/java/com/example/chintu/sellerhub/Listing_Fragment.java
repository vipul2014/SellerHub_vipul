package com.example.chintu.sellerhub;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by hp lap on 10-03-2017.
 */

public class Listing_Fragment  extends Fragment implements View.OnClickListener {

    ViewPager pager;
    ViewPagerAdapter adapter;
    SlidingTabLayout tabs;
    CharSequence Titles[] = {"Product 1","Product 2","Product 3"};
    int Numboftabs = 3;

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.port_folio_main, container, false);
        adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager(), Titles, Numboftabs);



        pager = (ViewPager)view.findViewById(R.id.v_pager);
        pager.setAdapter(adapter);

        // Assiging the Sliding Tab Layout View
        tabs = (SlidingTabLayout)view.findViewById(R.id.tab);
        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width


        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(pager);
return view;
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
