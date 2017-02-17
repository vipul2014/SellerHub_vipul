package com.example.chintu.sellerhub;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import de.greenrobot.event.EventBus;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class FragmentTwo extends Fragment implements View.OnClickListener {
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE=100;
    public static final int MEDIA_TYPE_IMAGE=1;
    public static final int RESULT_LOAD_IMG=2;

    private static final String IMAGE_DIRECTORY_NAME="Port folio";
    private Uri fileUri;
    String imgDecodableString;

    Button next, get_cam, get_gal;
    EditText p2_title, p2_desc;
    ImageView p2_image;

    Bitmap bitmap2;

    static String e_desc,e_img,e_title;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_second, container, false);
        setRetainInstance(true);

        next = (Button) v.findViewById(R.id.btn_p2);
        get_cam=(Button)v.findViewById(R.id.camera_btn2);
        get_gal=(Button)v.findViewById(R.id.galry_btn2);
        p2_image=(ImageView)v.findViewById(R.id.p2_img);
        p2_title=(EditText)v.findViewById(R.id.p2_title);
        p2_desc=(EditText)v.findViewById(R.id.p2_desc);
        next.setOnClickListener(this);
        get_gal.setOnClickListener(this);
        get_cam.setOnClickListener(this);
        return v;
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("img2",e_img);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState!=null){
            e_img=savedInstanceState.getString("img2","null");
            byte[] decodedString = Base64.decode(e_img, Base64.DEFAULT);
            bitmap2 = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            p2_image.setImageBitmap(bitmap2);
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.camera_btn2:captureImage();
                break;
            case R.id.galry_btn2:loadImage();
                break;
            case R.id.btn_p2: sendData();
                break;
        }

    }
    private void sendData() {
        Bitmap bitmap = ((BitmapDrawable) p2_image.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        e_img = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        e_desc=p2_desc.getText().toString();
        e_title=p2_title.getText().toString();
        //if(validation()) {
            //EventBus.getDefault().post(new Frag2(e_title,e_desc,e_img));
        //}
    }

    private boolean validation() {
        boolean validation = true;
        if ("".equals(e_title)) {
            p2_title.setError("Enter Title");
            validation = false;
        } else if ("".equals(e_desc)) {
            p2_desc.setError("Enter the description");
            validation = false;
        }


        return validation;
    }

    private void loadImage() {
        Intent galleryIntent= new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent,RESULT_LOAD_IMG);
    }
    private void captureImage() {
        Intent intent= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        fileUri=getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,fileUri);

        startActivityForResult(intent,CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
    }

    private Uri getOutputMediaFileUri(int mediaTypeImage) {
        return Uri.fromFile(getOutputMediaFile(mediaTypeImage));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==CAMERA_CAPTURE_IMAGE_REQUEST_CODE){
            if(resultCode==RESULT_OK){
                previewCapturedImage();
            }
            else if(resultCode==RESULT_CANCELED){
                Toast.makeText(getActivity().getApplicationContext(), "USER CANCELLED IMAGE CAPTURE", Toast.LENGTH_SHORT).show();
            }
        }
        else if(requestCode==RESULT_LOAD_IMG){
            if(resultCode==RESULT_OK){
                Uri selectedImage=data.getData();
                String[] filePathColumn={MediaStore.Images.Media.DATA};
                Cursor cursor=getActivity().getContentResolver().query(selectedImage,filePathColumn,null,null,null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgDecodableString = cursor.getString(columnIndex);
                cursor.close();
                p2_image.setImageBitmap(BitmapFactory
                        .decodeFile(imgDecodableString));
            }
            else
            {
                Toast.makeText(getActivity().getApplicationContext(), "ABE image utha le", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void previewCapturedImage() {
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 8;
            final Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(), options);
            p2_image.setImageBitmap(bitmap);
        }
        catch(NullPointerException e){
            e.printStackTrace();
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
    static class Frag2{

        Frag2(){

        }
        static  public String getdesc(){
            return e_desc;
        }
        static public String getimg(){
            return e_img;
        }
       static public String gettitle(){
            return e_title;
        }
    }

}
