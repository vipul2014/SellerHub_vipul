package com.example.chintu.sellerhub.orders_module;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.chintu.sellerhub.listing_module.Listing_Inactive_fragment;
import com.example.chintu.sellerhub.listing_module.Listing_Instock_fragment;
import com.example.chintu.sellerhub.listing_module.Listing_SoldOut_fragment;

/**
 * Created by hp lap on 28-03-2017.
 */

public class Orders_ViewPagerAdapter extends FragmentStatePagerAdapter {

    CharSequence Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
    int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created


    // Build a Constructor and assign the passed Values to appropriate values in the class
    public Orders_ViewPagerAdapter(FragmentManager fm, CharSequence mTitles[], int mNumbOfTabsumb) {
        super(fm);

        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;

    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {

        if(position == 0) // if the position is 0 we are returning the First tab
        {
            Orders_Pending_fragment tab1 = new Orders_Pending_fragment();
            return tab1;
        }

        else if (position==1){
            Orders_Ship_fragment tab2 = new Orders_Ship_fragment();
            return tab2;
        }
        else {
            Orders_Completed_fragment tab3 = new Orders_Completed_fragment();
            return tab3;
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


