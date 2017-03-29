package com.example.chintu.sellerhub.orders_module;

import com.example.chintu.sellerhub.listing_module.Listing_Recycler_DataCollect;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hp lap on 28-03-2017.
 */

public class Orders_ConversionHelper {
        private final String KEY_SUCCESS = "success";
        public static List<Orders_Recycler_DataCollect> getcomment(JSONObject jObject) {

            try {
                JSONArray mallsListArray = jObject.getJSONArray("server_response");


                List<Orders_Recycler_DataCollect> tutorregister = new ArrayList<>();
                for (int i = 0; i < mallsListArray.length(); i++) {
                    JSONObject tutorjson = mallsListArray.getJSONObject(i);
                    Orders_Recycler_DataCollect tutorListBean = getcomments(tutorjson);
                    tutorregister.add(tutorListBean);
                }
                return tutorregister;

            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }

        private static Orders_Recycler_DataCollect getcomments(JSONObject jObject) throws JSONException {
            Orders_Recycler_DataCollect tutorData = new Orders_Recycler_DataCollect();








            return tutorData;
        }
}
