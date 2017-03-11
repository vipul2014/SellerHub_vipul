package com.example.chintu.sellerhub;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by hp lap on 21-02-2017.
 */


public class profile_fragment extends Fragment implements View.OnClickListener {
    Button Basic_detail,Social_detail,Bank_detail,Pickup_location;
    private ToggleButton togglebutton,togglebutton_social,togglebutton_bank,togglebutton_pickup,togglebutton_login;
    ExpandableRelativeLayout expandableLayout,expandablelayout_social,expandablelayout_bank,expandablelayout_pickup,expandablelayout_login;

    private static String url="url for fetching profile info";
    ProgressDialog pDialog;
    private String TAG = profile_fragment.class.getSimpleName();
    String profilePic,name,gender,DOB,contact,email,address,description,fblink,instalink,twitterlink,accountholdername,bankname,ifsc,accountnumber,pickuplocation,username2;
    TextView tv_gender,tv_name,tv_DOB,tv_contact,tv_email,tv_address,tv_description,tv_fblink,tv_instalink,tv_twitterlink,tv_accountholdername,tv_bankname,tv_ifsc,tv_accountnumber,tv_pickuplocation,tv_username2;
    RoundedImageView im_profilePic;
    Button picupload;
    CoordinatorLayout coordinatorLayout;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle("Profile");
        View view = inflater.inflate(R.layout.profile_activity, container, false);
        Basic_detail = (Button) view.findViewById(R.id.basic);
        Social_detail = (Button) view.findViewById(R.id.social);
        Bank_detail = (Button) view.findViewById(R.id.bank);
        Pickup_location = (Button) view.findViewById(R.id.pickup);
        tv_name=(TextView)view.findViewById(R.id.name);
        tv_gender=(TextView)view.findViewById(R.id.gender);
        tv_DOB=(TextView)view.findViewById(R.id.DOB);
        tv_contact=(TextView)view.findViewById(R.id.contact);
        tv_email=(TextView)view.findViewById(R.id.email);
        tv_address=(TextView)view.findViewById(R.id.address);
        tv_description=(TextView)view.findViewById(R.id.description);
        tv_fblink=(TextView)view.findViewById(R.id.fblink);
        tv_instalink=(TextView)view.findViewById(R.id.instalink);
        tv_twitterlink=(TextView)view.findViewById(R.id.twitterlink);
        tv_accountholdername=(TextView)view.findViewById(R.id.accountholdername);
        tv_bankname=(TextView)view.findViewById(R.id.bankname);
        tv_accountnumber=(TextView)view.findViewById(R.id.accountnumber);
        tv_pickuplocation=(TextView)view.findViewById(R.id.pickup_location);
        tv_username2=(TextView)view.findViewById(R.id.username2);
        tv_ifsc=(TextView)view.findViewById(R.id.ifsc);
        im_profilePic=(RoundedImageView) view.findViewById(R.id.profile_pic);
        picupload=(Button)view.findViewById(R.id.profile_pic_edit);
        picupload.setOnClickListener(this);
        coordinatorLayout=(CoordinatorLayout)view.findViewById(R.id.coordinatorLayout);



       // new GetProfile().execute();




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
            i.putExtra("name",name);
            i.putExtra("gender",gender);
            i.putExtra("contact",contact);
            i.putExtra("email",email);
            i.putExtra("address",address);
            i.putExtra("DOB",DOB);
            i.putExtra("description",description);
            getActivity().startActivity(i);
        }
        if(view.getId()== R.id.bank){
            Intent i= new Intent(getActivity(),BankEdit.class);
            i.putExtra("accountholdername",accountholdername);
            i.putExtra("bankname",bankname);
            i.putExtra("ifsc",ifsc);
            i.putExtra("accontnumber",accountnumber);
            getActivity().startActivity(i);
        }
        if(view.getId()== R.id.pickup){
            Intent i= new Intent(getActivity(),PickupEdit.class);
            i.putExtra("pickuplocation",pickuplocation);
           getActivity().startActivity(i);
        }

        if(view.getId()==R.id.profile_pic_edit){
            FireMissilesDialogFragment fireMissilesDialogFragment=new FireMissilesDialogFragment();
            fireMissilesDialogFragment.show(getFragmentManager(),"null");
        }


    }

    static  public class FireMissilesDialogFragment extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("SELECT IMAGE")
                    .setPositiveButton("CAMERA", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // FIRE ZE MISSILES!
                        }
                    })
                    .setNegativeButton("GALLERY", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                        }
                    });
            builder.setNeutralButton("REMOVE",new DialogInterface.OnClickListener(){

                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            // Create the AlertDialog object and return it
            return builder.create();
        }
    }

    private class GetProfile extends AsyncTask<Void,Void,Void>
    {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray pesonalInfo = jsonObj.getJSONArray("ProfileInfo");//response array name

                    // looping through All Contacts

                        JSONObject c = pesonalInfo.getJSONObject(0);


                    name= c.getString("name");
                    gender= c.getString("gender");
                    DOB= c.getString("DOB");
                    contact= c.getString("contact");
                    email= c.getString("email");
                    address= c.getString("address");
                    description= c.getString("description");
                    fblink= c.getString("fblink");
                   instalink= c.getString("instalink");
                    twitterlink= c.getString("twitterlink");
                    accountholdername= c.getString("accountholdername");
                    bankname= c.getString("bankname");
                    ifsc= c.getString("ifsc");
                    accountnumber= c.getString("accountnumber");
                    pickuplocation= c.getString("picuplication");
                    username2= c.getString("username2");





                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());

                            Toast.makeText(getActivity().getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();


                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");

                        Toast.makeText(getActivity().getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();


            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            tv_name.setText(name);
            tv_gender.setText(gender);
            tv_DOB.setText(DOB);
            tv_contact.setText(contact);
            tv_email.setText(email);
            tv_address.setText(address);
            tv_description.setText(description);
            tv_fblink.setText(fblink);
            tv_instalink.setText(instalink);
            tv_twitterlink.setText(twitterlink);
            tv_accountholdername.setText(accountholdername);
            tv_bankname.setText(bankname);
            tv_ifsc.setText(ifsc);
            tv_accountnumber.setText(accountnumber);
            tv_pickuplocation.setText(pickuplocation);
            tv_username2.setText(username2);






        }

    }
    }

