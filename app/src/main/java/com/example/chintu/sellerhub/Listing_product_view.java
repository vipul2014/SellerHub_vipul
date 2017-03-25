package com.example.chintu.sellerhub;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.androidquery.util.AQUtility.getContext;
import static com.example.chintu.sellerhub.Config.DATA_URL;

/**
 * Created by hp lap on 21-03-2017.
 */

public class Listing_product_view extends AppCompatActivity implements View.OnClickListener {
    private TextView Title,CreatedOn,Description,Category,Style,Surface,Stock,MRP,Selling_price,Delivery_Charges,Height,Width,Breadth;
    private ImageLoader imageLoader;
    protected NetworkImageView img1,img2,img3,img4,img5;
    ProgressDialog loading;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_product);
        Title=(TextView)findViewById(R.id.tv_title);
        img1=(NetworkImageView) findViewById(R.id.img1);
        img2=(NetworkImageView) findViewById(R.id.img2);
        img3=(NetworkImageView) findViewById(R.id.img3);
        img4=(NetworkImageView) findViewById(R.id.img4);
        img5=(NetworkImageView) findViewById(R.id.img5);

        CreatedOn=(TextView)findViewById(R.id.tv_createdOn);
        Description=(TextView)findViewById(R.id.tv_description);
        Category=(TextView)findViewById(R.id.tv_category);
        Style=(TextView)findViewById(R.id.tv_style);
        Surface=(TextView)findViewById(R.id.tv_surface);
        MRP=(TextView)findViewById(R.id.tv_MRP);
        Selling_price=(TextView)findViewById(R.id.tv_selling_price);
        Delivery_Charges=(TextView)findViewById(R.id.tv_delivery_charge);
        Height=(TextView)findViewById(R.id.tv_height);
        Width=(TextView)findViewById(R.id.tv_width);
        Breadth=(TextView)findViewById(R.id.tv_length);
        Stock=(TextView)findViewById(R.id.tv_stock);
        getData();

        //https://www.simplifiedcoding.net/retrieve-data-from-mysql-database-in-android-using-volley/
    }
    private void getData() {


        loading = ProgressDialog.show(this,"Please wait...","Fetching...",false,false);

        String url = DATA_URL+getIntent().getStringExtra("artwork_id");

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                showJSON(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Listing_product_view.this,error.getMessage().toString(),Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private void showJSON(String response){

        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Config.JSON_ARRAY);
            JSONObject collegeData = result.getJSONObject(0);
            Title.setText(collegeData.getString(Config.KEY_TITLE));
          CreatedOn.setText(collegeData.getString(Config.KEY_CREATEDON));
            Description.setText(collegeData.getString(Config.KEY_DESCRIPTION));
            Category.setText(collegeData.getString(Config.KEY_CATEGORY));
            Style .setText(collegeData.getString(Config.KEY_STYLE));
            Surface.setText(collegeData.getString(Config.KEY_SURFACE));
            Selling_price.setText(collegeData.getString(Config.KEY_SELLINGPRICE));
          //  Height.setText(collegeData.getString(Config.KEY_SIZE));
           // Width.setText(collegeData.getString(Config.KEY_NAME););
            //Breadth.setText(collegeData.getString(Config.KEY_NAME););
            Stock.setText(String.valueOf(collegeData.getInt(Config.KEY_STOCK)));
            /* if(collegeData.getString(Config.KEY_IMG1)!="")
            {
                imageLoader = CustomVolleyRequest.getInstance(getContext())
                        .getImageLoader();
                imageLoader.get(Config.KEY_IMG1, ImageLoader.getImageListener(img1,
                        R.drawable.default_product,  R.drawable.default_product));
                img1.setImageUrl(Config.KEY_IMG1, imageLoader);
            }
           if(collegeData.getString(Config.KEY_IMG2)!="")
            {
                imageLoader = CustomVolleyRequest.getInstance(getContext())
                        .getImageLoader();
                imageLoader.get(Config.KEY_IMG2, ImageLoader.getImageListener(img2,
                        R.drawable.default_product,  R.drawable.default_product));
                img2.setImageUrl(Config.KEY_IMG2, imageLoader);
            }
            if(collegeData.getString(Config.KEY_IMG3)!="")
            {
                imageLoader = CustomVolleyRequest.getInstance(getContext())
                        .getImageLoader();
                imageLoader.get(Config.KEY_IMG3, ImageLoader.getImageListener(img3,
                        R.drawable.default_product,  R.drawable.default_product));
                img3.setImageUrl(Config.KEY_IMG3, imageLoader);
            }
            if(collegeData.getString(Config.KEY_IMG4)!="")
            {
                imageLoader = CustomVolleyRequest.getInstance(getContext())
                        .getImageLoader();
                imageLoader.get(Config.KEY_IMG4, ImageLoader.getImageListener(img4,
                        R.drawable.default_product,  R.drawable.default_product));
                img4.setImageUrl(Config.KEY_IMG4, imageLoader);
            }
            if(collegeData.getString(Config.KEY_IMG5)!="")
            {
                imageLoader = CustomVolleyRequest.getInstance(getContext())
                        .getImageLoader();
                imageLoader.get(Config.KEY_IMG5, ImageLoader.getImageListener(img5,
                        R.drawable.default_product,  R.drawable.default_product));
                img5.setImageUrl(Config.KEY_IMG5, imageLoader);
            } */

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View view) {

    }
}
