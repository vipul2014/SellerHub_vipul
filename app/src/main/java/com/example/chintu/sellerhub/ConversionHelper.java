package com.example.chintu.sellerhub;

/**
 * Created by Mohit chauhan on 8/2/2016.
 */

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ConversionHelper {
    private final String KEY_SUCCESS = "success";
    public static List<Listing_Recycler_DataCollect> getcomment(JSONObject jObject) {

        try {
            JSONArray mallsListArray = jObject.getJSONArray("server_response");

            List<Listing_Recycler_DataCollect> tutorregister = new ArrayList<>();
            for (int i = 0; i < mallsListArray.length(); i++) {
                JSONObject tutorjson = mallsListArray.getJSONObject(i);
                Listing_Recycler_DataCollect tutorListBean = getcomments(tutorjson);
                tutorregister.add(tutorListBean);
            }
            return tutorregister;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Listing_Recycler_DataCollect getcomments(JSONObject jObject) throws JSONException {
        Listing_Recycler_DataCollect tutorData = new Listing_Recycler_DataCollect();

        tutorData.setTitle(jObject.getString("image"));
        tutorData.setID(jObject.getInt("ID"));
        tutorData.setPrice(jObject.getInt("image"));
        tutorData.setUnits(jObject.getInt("image"));
        tutorData.setProduct_img(jObject.getString("image"));






        return tutorData;
    }
}