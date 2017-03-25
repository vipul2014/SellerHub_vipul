package com.example.chintu.sellerhub;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import org.json.JSONObject;

import java.util.List;


public class Listing_Instock_fragment extends Fragment implements View.OnClickListener {
    private RecyclerView recyclerView;
    private AQuery aQuery;
    private List<Listing_Recycler_DataCollect> ListData;
    private MyAdapter mAdapter;
    private ImageLoader imageLoader;
    TextView artwork_no;

    public int lastPosition=-1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_listing, container, false);
        artwork_no=(TextView)v.findViewById(R.id.artwork);
        aQuery = new AQuery(getActivity());
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view_artwork);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        loadMalls("http://artbirdz.hol.es/Artbirdz%20seller%20hub/Active_Listin.php");
        return v;
    }

    private void loadMalls(String url) {
        if (isConnection()) {
            //String url = UrlConstants.BASE_URL + UrlConstants.GET_MALLS_LISTING + "?latitude=" + "28.5038941" + "&longitude=" + "77.0973031";
            //String url = "http://192.168.1.230:8686/quikrydetestapp/public/provider/get_schedule_bookings?id=84&token=2y10In3SyIgQDSMfRRAYecuiXOFOfs5kseDfeGpnAJTXj4T1CXKmpvkjK";

            ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Fetching Data Please wait...");
            aQuery.progress(progressDialog).ajax(url, null, JSONObject.class, new AjaxCallback<JSONObject>() {
                @Override
                public void callback(String url, JSONObject json, AjaxStatus status) {
                    if (json != null) {
                        ListData = ConversionHelper.getcomment(json);
                        mAdapter = new Listing_Instock_fragment.MyAdapter();
                        artwork_no.setText(String.valueOf(mAdapter.getItemCount()));
                        recyclerView.setAdapter(mAdapter);
                        mAdapter.notifyDataSetChanged();

                    } else {
                        Toast.makeText(aQuery.getContext(), "request_couldnt_be_completed", Toast.LENGTH_LONG).show();
                    }
                }
            });
        } else {
            showConnectionErrorDialog();
        }
    }

    private void showConnectionErrorDialog() {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getActivity());
        builder.setTitle("No Internet !!");
        builder.setMessage("Not connected to the network right now. Please turn it on and try again later");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                getActivity().finish();
            }
        });

        android.support.v7.app.AlertDialog dialog = builder.create();
        dialog.show();
    }

    private boolean isConnection() {
        ConnectivityManager connectivityManager=(ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {

            return true;
        } else {
            return false;
        }

    }

    @Override
    public void onClick(View view) {

    }

    public class MyAdapter extends RecyclerView.Adapter<Listing_Instock_fragment.MyAdapter.ViewHolder> {

        @Override
        public Listing_Instock_fragment.MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemLayoutView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.artwork_recycler,parent,false);
            Listing_Instock_fragment.MyAdapter.ViewHolder viewHolder = new Listing_Instock_fragment.MyAdapter.ViewHolder(itemLayoutView);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(Listing_Instock_fragment.MyAdapter.ViewHolder viewHolder, int position) {

            // MallsListBean storeBean = mallsList.get(position);
            final Listing_Recycler_DataCollect tutorListBeans = ListData.get(position);

            viewHolder.title.setText(String.valueOf(tutorListBeans.getTitle()));
            viewHolder.ID.setText(String.valueOf(tutorListBeans.getID()));
            viewHolder.Price.setText(tutorListBeans.getPrice());
            viewHolder.Units.setText(String.valueOf(tutorListBeans.getUnits()));


            String url =tutorListBeans.getProduct_img();

            imageLoader = CustomVolleyRequest.getInstance(getContext())
                    .getImageLoader();
            imageLoader.get(url, ImageLoader.getImageListener(viewHolder.image,
                    R.drawable.profile_icon, android.R.drawable
                            .ic_dialog_alert));
            viewHolder.image.setImageUrl(url, imageLoader);


            Animation animation = AnimationUtils.loadAnimation(getContext(),
                    (position > lastPosition) ? R.anim.up_from_bottom
                            : R.anim.down_from_top);
            viewHolder.itemView.startAnimation(animation);
            lastPosition = position;
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //www.worldbestlearningcenter.com/tips/Android-send-intent-from-fragment-to-activity.htm
                    //for data transfer from fragment to activity
                    Intent intent=new Intent(getActivity(), Listing_product_view.class);
                    //add data to the Intent object
                    intent.putExtra("artwork_id", String.valueOf(tutorListBeans.getID()));
                    //start the second activity
                    startActivity(intent);

                    Toast.makeText(getContext(),"clicked="+ String.valueOf(tutorListBeans.getTitle()) ,Toast.LENGTH_SHORT).show();

                }
            });
            //viewHolder.storeCount.setText(mallsListBean.getStoreCount() + " Stores");

            /*DecimalFormat df = new DecimalFormat("#.#");
            if(mallsListBean.getDistance() != null) {
                viewHolder.distance.setText(df.format(mallsListBean.getDistance()) + " KM");
            }else{
                viewHolder.distance.setText("");
            }

            viewHolder.locality.setText(mallsListBean.getAddress());
            */
            // viewHolder.txtViewTitle.setTag(R.string.mallIdTag, mallsListBean.getMallId());


        }






        public class ViewHolder extends RecyclerView.ViewHolder {

            protected TextView title;
            protected TextView ID;
            protected TextView Price;
            protected TextView Units;

            protected NetworkImageView image;


            public ViewHolder(final View itemLayoutView) {
                super(itemLayoutView);

                title = (TextView) itemLayoutView.findViewById(R.id.artwork_title_recycler);
                ID = (TextView) itemLayoutView.findViewById(R.id.artwork_id_recycler);
                Price = (TextView) itemLayoutView.findViewById(R.id.artwork_price_recycler);
                Units = (TextView) itemLayoutView.findViewById(R.id.artwork_units_recycler);
                image = (NetworkImageView) itemLayoutView.findViewById(R.id.artwork_image_recycler);





            }
        }
        @Override
        public void onViewDetachedFromWindow(Listing_Instock_fragment.MyAdapter.ViewHolder holder) {
            super.onViewDetachedFromWindow(holder);
            holder.itemView.clearAnimation();
        }

        @Override
        public int getItemCount() {
            return ListData.size();
        }

    }
}
