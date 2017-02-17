package com.example.chintu.sellerhub;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.EventBusBuilder;
import de.greenrobot.event.ThreadMode;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static com.example.chintu.sellerhub.FragmentActivity.*;

public class FragmentThird extends Fragment implements View.OnClickListener{
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE=100;
    public static final int MEDIA_TYPE_IMAGE=1;
    public static final int RESULT_LOAD_IMG=2;

    private static final String IMAGE_DIRECTORY_NAME="Port folio";
    private Uri fileUri;
    String imgDecodableString;

    Button next, get_cam, get_gal;
    EditText p3_title, p3_desc;
    ImageView p3_image;
    TextView sample;
    boolean dataAdd=false;

    Bitmap bitmap2;

    ProgressDialog progressDialog;

    static  String e_desc,e_img,e_title,e1_desc,e1_img,e1_title,e2_desc,e2_img,e2_title,artistid;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //EventBus e= EventBus.getDefault();
        //e.register(this);
        View v = inflater.inflate(R.layout.fragment_third, container, false);
        setRetainInstance(true);
        next = (Button) v.findViewById(R.id.btn_p3);
        get_cam = (Button) v.findViewById(R.id.camera_btn3);
        get_gal = (Button) v.findViewById(R.id.galry_btn3);
        p3_image = (ImageView) v.findViewById(R.id.p3_img);
        p3_title = (EditText) v.findViewById(R.id.p3_title);
        p3_desc = (EditText) v.findViewById(R.id.p3_desc);
        sample=(TextView)v.findViewById(R.id.sample) ;
        next.setOnClickListener(this);
        get_gal.setOnClickListener(this);
        get_cam.setOnClickListener(this);


        return v;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("img3",e_img);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState!=null){
            e_img=savedInstanceState.getString("img3","null");
            byte[] decodedString = Base64.decode(e_img, Base64.DEFAULT);
            bitmap2 = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            p3_image.setImageBitmap(bitmap2);
        }
    }

    private void getbitmap() {
        Bitmap bitmap = ((BitmapDrawable) p3_image.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        e_img = Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.camera_btn3:captureImage();
                break;
            case R.id.galry_btn3:loadImage();
                break;
            case R.id.btn_p3: sendData();
                break;
        }
    }
    private void sendData() {

        e_desc = p3_desc.getText().toString();
        e_title = p3_title.getText().toString();
        final SharedPreferences mSharedPreference = PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext());
        artistid = (mSharedPreference.getString("artist_id", "Default_Value"));
        if (validation()) {
            e1_desc=Frag1.getdesc();
            e1_title=Frag1.gettitle();
            e1_img=Frag1.getimg();
            e2_desc= FragmentTwo.Frag2.getdesc();
            e2_title= FragmentTwo.Frag2.gettitle();
            e2_img= FragmentTwo.Frag2.getimg();
           // sample.setText(e1_img);
            FragmentThird.Background background1 = new FragmentThird.Background();
            background1.execute(artistid, e1_img, e1_title, e1_desc,e2_img,e2_title,e2_desc,e_img,e_title,e_desc);


        }
    }

    public boolean isConnection() {
        ConnectivityManager manage = (ConnectivityManager) FragmentThird.this.getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manage.getActiveNetworkInfo();
        if (info != null && info.isConnectedOrConnecting()) {

            return true;
        } else {
            return false;
        }
    }



    private class Background extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            if (isConnection()) {
                progressDialog = new ProgressDialog(getActivity());
                progressDialog.setMessage("SUBMITTING..");
                progressDialog.setCancelable(false);
                progressDialog.show();
            } else {
                Toast.makeText(getActivity().getApplicationContext(), "Make sure you are connected to Internet", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            artistid = params[0];
            e2_img = params[1];
            e2_title = params[2];
            e2_desc = params[3];

            try {
                URL url = new URL("http://artbirdz.hol.es/sellerApp/registration/portfolio.php");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                String data = URLEncoder.encode("artist_id", "UTF-8") + "=" + URLEncoder.encode(artistid, "UTF-8")
                        + "&" +

                        URLEncoder.encode("img", "UTF-8") + "=" + URLEncoder.encode(e1_img, "UTF-8") + "&" +

                        URLEncoder.encode("title", "UTF-8") + "=" + URLEncoder.encode(e1_title, "UTF-8") + "&" +

                        URLEncoder.encode("description", "UTF-8") + "=" + URLEncoder.encode(e1_desc, "UTF-8")
                        + "&" +
                        URLEncoder.encode("img2", "UTF-8") + "=" + URLEncoder.encode(e2_img, "UTF-8") + "&" +

                        URLEncoder.encode("title2", "UTF-8") + "=" + URLEncoder.encode(e2_title, "UTF-8") + "&" +

                        URLEncoder.encode("description2", "UTF-8") + "=" + URLEncoder.encode(e2_desc, "UTF-8")
                        + "&" +
                        URLEncoder.encode("img3", "UTF-8") + "=" + URLEncoder.encode(e_img, "UTF-8") + "&" +

                        URLEncoder.encode("title3", "UTF-8") + "=" + URLEncoder.encode(e_title, "UTF-8") + "&" +

                        URLEncoder.encode("description3", "UTF-8") + "=" + URLEncoder.encode(e_desc, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();
                InputStream IS = httpURLConnection.getInputStream();
                IS.close();
                //httpURLConnection.connect();
                httpURLConnection.disconnect();
                dataAdd=true;
                return "Updated";
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();

            }

            return null;

        }

        @Override
        protected void onPostExecute(String s) {

            progressDialog.dismiss();
            Toast.makeText(getActivity().getApplicationContext(), s, Toast.LENGTH_LONG).show();

        }
    }



    private boolean validation() {
        boolean validation = true;
        if ("".equals(e_title)) {
            p3_title.setError("Enter Title");
            validation = false;
        } else if ("".equals(e_desc)) {
            p3_desc.setError("Enter the description");
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
                p3_image.setImageBitmap(BitmapFactory
                        .decodeFile(imgDecodableString));
                getbitmap();
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
            p3_image.setImageBitmap(bitmap);
            getbitmap();
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
/**public void onEvent(FragmentActivity.Frag1 event){
    e1_title=event.gettitle();

    e1_img=event.getimg();
    e1_desc=event.getdesc();
    sample.setText(e1_desc);
}
    public void onEvent(FragmentTwo.Frag2 event){
      e2_title=event.gettitle();
        e2_desc=event.getdesc();
        e2_img=event.getimg();
    }
*/

    }


