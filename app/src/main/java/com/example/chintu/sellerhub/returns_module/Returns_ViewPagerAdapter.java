package com.example.chintu.sellerhub.returns_module;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.chintu.sellerhub.orders_module.Orders_Completed_fragment;
import com.example.chintu.sellerhub.orders_module.Orders_Pending_fragment;
import com.example.chintu.sellerhub.orders_module.Orders_Ship_fragment;

/**
 * Created by hp lap on 30-03-2017.
 */

public class Returns_ViewPagerAdapter extends FragmentStatePagerAdapter {

    CharSequence Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
    int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created


    // Build a Constructor and assign the passed Values to appropriate values in the class
    public Returns_ViewPagerAdapter(FragmentManager fm, CharSequence mTitles[], int mNumbOfTabsumb) {
        super(fm);

        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;

    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {

        if(position == 0) // if the position is 0 we are returning the First tab
        {
            Returns_Inprogress_fragment tab1 = new Returns_Inprogress_fragment();
            return tab1;
        }

        else
        {
            Returns_Completed_fragment tab2 = new Returns_Completed_fragment();
            return tab2;
        }


    }

    // This method return the titles for the Tabs in the Tab Strip

    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }

    // This method return the Number of tabs for the tabs Strip

    @Override
    public int getCount() {
        return NumbOfTabs;
    }}
