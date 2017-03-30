package com.example.chintu.sellerhub.returns_module;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chintu.sellerhub.R;
import com.example.chintu.sellerhub.SlidingTabLayout;
import com.example.chintu.sellerhub.orders_module.Orders_ViewPagerAdapter;

/**
 * Created by hp lap on 30-03-2017.
 */

public class returns_fragment  extends Fragment implements View.OnClickListener {

    ViewPager pager;
    Returns_ViewPagerAdapter adapter;
    SlidingTabLayout tabs;
    CharSequence Titles[] = {"IN-PROGRESS","COMPLETED"};
    int Numboftabs = 2;

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.returns_activity, container, false);
        getActivity().setTitle("Returns");
        adapter = new Returns_ViewPagerAdapter(getActivity().getSupportFragmentManager(), Titles, Numboftabs);
        tabs = (SlidingTabLayout)view.findViewById(R.id.returns_tab);


        tabs.setSelectedIndicatorColors(getResources().getColor(R.color.white));

        pager = (ViewPager)view.findViewById(R.id.returns_v_pager);
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

