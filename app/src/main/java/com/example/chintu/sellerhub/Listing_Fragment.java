package com.example.chintu.sellerhub;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.chintu.sellerhub.registration_module.FragmentTwo;

/**
 * Created by hp lap on 10-03-2017.
 */

public class Listing_Fragment  extends Fragment implements View.OnClickListener {

    ViewPager pager;
    Button add_product_btn;
    Listing_ViewPagerAdapter adapter;
    SlidingTabLayout tabs;
    CharSequence Titles[] = {"In Stock","Sold Out","Inactive"};
    int Numboftabs = 3;

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.listing_activity, container, false);
        getActivity().setTitle("Listing");
        adapter = new Listing_ViewPagerAdapter(getActivity().getSupportFragmentManager(), Titles, Numboftabs);
        tabs = (SlidingTabLayout)view.findViewById(R.id.listing_tab);
        FloatingActionButton myFab = (FloatingActionButton) view.findViewById(R.id.fab_addproduct);
        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), AddProduct.class);
                getActivity().startActivity(i);
            }
        });


        tabs.setSelectedIndicatorColors(getResources().getColor(R.color.white));

        pager = (ViewPager)view.findViewById(R.id.listing_v_pager);
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


            case R.id.btn_p1:
                Listing_Instock_fragment tab1 = new Listing_Instock_fragment();
                break;
            case R.id.btn_p2:
                FragmentTwo tab2 = new FragmentTwo();
                break;

        }

    }
}
