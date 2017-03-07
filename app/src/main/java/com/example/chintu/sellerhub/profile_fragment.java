package com.example.chintu.sellerhub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;

/**
 * Created by hp lap on 21-02-2017.
 */


public class profile_fragment extends Fragment implements View.OnClickListener {
    Button Basic_detail,Social_detail,Bank_detail,Pickup_location;
    private ToggleButton togglebutton,togglebutton_social,togglebutton_bank,togglebutton_pickup,togglebutton_login;
    ExpandableRelativeLayout expandableLayout,expandablelayout_social,expandablelayout_bank,expandablelayout_pickup,expandablelayout_login;
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle("Profile");
        View view = inflater.inflate(R.layout.profile_activity, container, false);
        Basic_detail = (Button) view.findViewById(R.id.basic);
        Social_detail = (Button) view.findViewById(R.id.social);
        Bank_detail = (Button) view.findViewById(R.id.bank);
        Pickup_location = (Button) view.findViewById(R.id.pickup);




        expandableLayout = (ExpandableRelativeLayout)view.findViewById(R.id.expandableLayout);
        expandablelayout_social = (ExpandableRelativeLayout)view.findViewById(R.id.expandableLayout_social);
        expandablelayout_bank = (ExpandableRelativeLayout)view.findViewById(R.id.expandableLayout_bank);
        expandablelayout_pickup = (ExpandableRelativeLayout)view.findViewById(R.id.expandableLayout_pickup);
        expandablelayout_login = (ExpandableRelativeLayout)view.findViewById(R.id.expandableLayout_login);
        togglebutton = (ToggleButton)view.findViewById(R.id.toggle);
        togglebutton_social = (ToggleButton)view.findViewById(R.id.toggle_social);
        togglebutton_bank = (ToggleButton)view.findViewById(R.id.toggle_bank);
        togglebutton_pickup = (ToggleButton)view.findViewById(R.id.toggle_pickup);
        togglebutton_login = (ToggleButton)view.findViewById(R.id.toggle_login);
        Basic_detail.setOnClickListener(this);
        Social_detail.setOnClickListener(this);
        Bank_detail.setOnClickListener(this);
        Pickup_location.setOnClickListener(this);

        expandableLayout.collapse();
        expandablelayout_social.collapse();
        expandablelayout_bank.collapse();
        expandablelayout_pickup.collapse();
        expandablelayout_login.collapse();
        togglebutton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if(isChecked)
                  expandableLayout.expand();

                else
                  expandableLayout.collapse();
            }
        });
        togglebutton_social.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if(isChecked)
                    expandablelayout_social.expand();

                else
                    expandablelayout_social.collapse();
            }
        });
        togglebutton_bank.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if(isChecked)
                    expandablelayout_bank.expand();

                else
                    expandablelayout_bank.collapse();
            }
        });
        togglebutton_pickup.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if(isChecked)
                    expandablelayout_pickup.expand();

                else
                    expandablelayout_pickup.collapse();
            }
        });
        togglebutton_login.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if(isChecked)
                    expandablelayout_login.expand();

                else
                    expandablelayout_login.collapse();
            }
        });


        return view;


    }



    @Override
    public void onClick(View view) {
        if(view.getId()== R.id.basic){
            Intent i= new Intent(getActivity(),BasicEdit.class);
            getActivity().startActivity(i);
        }
        if(view.getId()== R.id.bank){
            Intent i= new Intent(getActivity(),BankEdit.class);
            getActivity().startActivity(i);
        }
        if(view.getId()== R.id.pickup){
            Intent i= new Intent(getActivity(),PickupEdit.class);
           getActivity().startActivity(i);
        }


    }
}
