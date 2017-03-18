package com.example.chintu.sellerhub;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Map;


public class AddProduct extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemSelectedListener{
    AutoCompleteTextView et_title,et_surface,et_size,et_createdon,et_description,et_sellingprice;
    Spinner s_category,s_style;
    RadioGroup rg_packing;
    ImageView imgbig;
    ImageView[] img=new ImageView[6];
    Button getpic,save;
    static int[] img_set=new int[6];
    Bitmap[] img_bitmap=new Bitmap[6];
    Bitmap img_bit,bitmap;
    int big_img_ref;
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int RESULT_LOAD_IMG = 2;

    private String UPLOAD_URL ="enter url";

    private static final String IMAGE_DIRECTORY_NAME = "Port folio";
    public Uri fileUri;
    String imgDecodableString;
    RadioButton pack;
    int ref;
    String e_title,e_surface,e_size,e_createdon,e_description,e_sellingprice,e_category,e_style,e_packing;
    String[] e_img=new String[6];
    int[] img_stat=new int[6];
    int i;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_product);
        img_set[0]=img_set[1]=img_set[2]=img_set[3]=img_set[4]=img_set[5]=0;
        img_stat[0]=img_stat[1]=img_stat[2]=img_stat[3]=img_stat[4]=img_stat[5]=0;
        big_img_ref=0;
        ref=0;

        et_title=(AutoCompleteTextView) findViewById(R.id.act_title);
        et_surface=(AutoCompleteTextView) findViewById(R.id.act_surface);
        et_size=(AutoCompleteTextView) findViewById(R.id.act_size);
        et_createdon=(AutoCompleteTextView) findViewById(R.id.act_createdon);
        et_description=(AutoCompleteTextView) findViewById(R.id.act_description);
        et_sellingprice=(AutoCompleteTextView) findViewById(R.id.act_sellingprice);
        s_style=(Spinner)findViewById(R.id.s_style);
        s_category=(Spinner)findViewById(R.id.s_category);
        rg_packing=(RadioGroup)findViewById(R.id.rg_packing);
        imgbig=(ImageView)findViewById(R.id.big_1);
        img[0]=(ImageView)findViewById(R.id.img1);
        img[1]=(ImageView)findViewById(R.id.img2);
        img[2]=(ImageView)findViewById(R.id.img3);
        img[3]=(ImageView)findViewById(R.id.img4);
        img[4]=(ImageView)findViewById(R.id.img5);
        getpic=(Button)findViewById(R.id.btn_getpic);
        save=(Button)findViewById(R.id.btn_save);
        getpic.setOnClickListener(this);
        img[0].setOnClickListener(this);
        img[1].setOnClickListener(this);
        img[2].setOnClickListener(this);
        img[3].setOnClickListener(this);
        img[4].setOnClickListener(this);
        s_category.setOnItemSelectedListener(this);
        s_style.setOnItemSelectedListener(this);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_getpic: AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

                alertDialogBuilder.setPositiveButton("REMOVE PIC",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                if(img_stat[big_img_ref+1]==1){
                                    big_img_ref=big_img_ref+1;
                                    img_bit=img_bitmap[big_img_ref];
                                    imgbig.setImageBitmap(img_bit);
                                    for(i=big_img_ref-1;img_stat[i+1]==1;i++){
                                        img_bitmap[i]=img_bitmap[i+1];
                                        img[i].setImageBitmap(img_bitmap[i]);
                                    }
                                    img_stat[i]=0;
                                    img_bitmap[i]= BitmapFactory.decodeResource(getResources(), R.drawable.logo);
                                    img[i].setImageBitmap(img_bitmap[i]);
                                    img_set[i]=0;
                                }

                                else{
                                    img_bit=BitmapFactory.decodeResource(getResources(), R.drawable.logo);
                                    img[big_img_ref].setImageBitmap(img_bit);
                                    imgbig.setImageBitmap(img_bit);
                                    img_bitmap[big_img_ref]=img_bit;
                                    img_set[big_img_ref]=0;
                                    img_stat[big_img_ref]=0;
                                }
                                Toast.makeText(AddProduct.this,"Pic Removed",Toast.LENGTH_LONG).show();
                            }
                        });

                alertDialogBuilder.setNeutralButton("CHANGE PIC", new DialogInterface.OnClickListener() {


                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ref=big_img_ref;
                                alert();
                            }
                        }
                );

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                break;
            case R.id.img1 :

                if(img_set[0]==0){
                    ref=0;
                    alert();
                }
                else if(img_set[0]==1){
                    big_img_ref=0;
                    img_bit=img_bitmap[0];
                    imgbig.setImageBitmap(img_bit);
                }
                break;
            case R.id.img2:
                if(img_stat[0]==1){
                    if(img_set[1]==0){
                        ref=1;
                        alert();
                    }
                    else if(img_set[1]==1){
                        big_img_ref=1;
                        ;
                        img_bit=img_bitmap[1];
                        imgbig.setImageBitmap(img_bitmap[1]);
                    }}
                break;
            case R.id.img3:
                if(img_stat[1]==1){
                    if(img_set[2]==0){
                        ref=2;
                        alert();
                    }
                    else if(img_set[2]==1){
                        big_img_ref=2;
                        img_bit=img_bitmap[2];
                        imgbig.setImageBitmap(img_bitmap[2]);
                    }}
                break;
            case R.id.img4:
                if(img_stat[2]==1){
                    if(img_set[3]==0){
                        ref=3;
                        alert();
                    }
                    else if(img_set[3]==1){
                        big_img_ref=3;
                        img_bit=img_bitmap[3];
                        imgbig.setImageBitmap(img_bitmap[3]);
                    }}
                break;
            case R.id.img5:
                if(img_stat[3]==1){
                    if(img_set[4]==0){
                        ref=4;
                        alert();
                    }
                    else if(img_set[4]==1){
                        big_img_ref=4;
                        img_bit=img_bitmap[4];
                        imgbig.setImageBitmap(img_bitmap[4]);
                    }}
                break;
            case R.id.btn_save:
                getData();
                break;

        }
    }
    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void getData() {
        e_title=et_title.getText().toString();
        e_surface=et_surface.getText().toString();
        e_size=et_size.getText().toString();
        e_createdon=et_createdon.getText().toString();
        e_description=et_description.getText().toString();
        e_sellingprice=et_sellingprice.getText().toString();
        int selectId = rg_packing.getCheckedRadioButtonId();
        pack = (RadioButton) findViewById(selectId);
        e_packing = pack.getText().toString();
        for(i=0;i<5;i++){
            if(img_set[i]==1){
                e_img[i]=getStringImage(img_bitmap[i]);
            }
            else{
                e_img[i]="null";
            }
        }
        upload_data();

    }

    private void upload_data() {
        final ProgressDialog loading = ProgressDialog.show(this,"Uploading...","Please wait...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Disimissing the progress dialog
                        loading.dismiss();
                        //Showing toast message of the response
                        Toast.makeText(AddProduct.this, s , Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        loading.dismiss();

                        //Showing toast
                        Toast.makeText(AddProduct.this, volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new Hashtable<String, String>();

                //Adding parameters
                params.put("title", e_title);
                params.put("category", e_category);
                params.put("style",e_style);
                params.put("pic1",e_img[0]);
                params.put("pic2",e_img[1]);
                params.put("pic3",e_img[2]);
                params.put("pic4",e_img[3]);
                params.put("pic5",e_img[4]);
                params.put("surface",e_surface);
                params.put("size",e_size);
                params.put("createdon",e_createdon);
                params.put("packing",e_packing);
                params.put("description",e_description);
                params.put("sellingprice",e_sellingprice);


                //returning parameters
                return params;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Adding request to the queue
        requestQueue.add(stringRequest);

    }

    private void alert() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setPositiveButton("GALLERY",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {loadImage();

                    }
                });

        alertDialogBuilder.setNeutralButton("CAMERA", new DialogInterface.OnClickListener() {


                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        captureImage();
                    }
                }
        );

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    private void loadImage() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
    }

    private void captureImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
    }

    private Uri getOutputMediaFileUri(int mediaTypeImage) {
        return Uri.fromFile(getOutputMediaFile(mediaTypeImage));
    }

    private void previewCapturedImage() {
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 8;
            final Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(), options);
            img[ref].setImageBitmap(bitmap);
            big_img_ref=ref;
            img_bit=bitmap;
            img_bitmap[ref]=bitmap;
            imgbig.setImageBitmap(bitmap);
            img_set[ref]=1;
            img_stat[ref]=1;

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                previewCapturedImage();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(), "USER CANCELLED IMAGE CAPTURE", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == RESULT_LOAD_IMG) {
            if (resultCode == RESULT_OK) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgDecodableString = cursor.getString(columnIndex);
                cursor.close();
                bitmap=BitmapFactory
                        .decodeFile(imgDecodableString);
                img[ref].setImageBitmap(bitmap);
                big_img_ref=ref;
                img_bit=bitmap;
                img_bitmap[ref]=bitmap;
                imgbig.setImageBitmap(bitmap);
                img_set[ref]=1;
                img_stat[ref]=1;
            } else {
                Toast.makeText(getApplicationContext(), "ABE image utha le", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private File getOutputMediaFile(int type) {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), IMAGE_DIRECTORY_NAME);
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create" + IMAGE_DIRECTORY_NAME + "directory");
                return null;
            }
        }
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG" + timeStamp + ".jpg");
        } else {
            return null;
        }
        return mediaFile;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        e_category=parent.getSelectedItem().toString();
        e_style=parent.getSelectedItem().toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}


