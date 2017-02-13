package com.example.chintu.sellerhub;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class FragmentActivity extends Fragment {
    Button next, get_cam, get_gal;
    EditText p1_title, p1_desc;
    ImageView p1_image;

    @Nullable

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_first, container, false);
        next = (Button) v.findViewById(R.id.btn_p1);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewPager mViewPager
                mViewPager.setCurrentItem(2);

            }
        });
        return v;
    }
}




