package com.example.chintu.sellerhub.returns_module;

import com.example.chintu.sellerhub.orders_module.Orders_Recycler_DataCollect;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hp lap on 30-03-2017.
 */

public class Returns_ConversionHelper{
    private final String KEY_SUCCESS = "success";
    public static List<Returns_Recycler_DataCollect> getcomment(JSONObject jObject) {

        try {
            JSONArray mallsListArray = jObject.getJSONArray("server_response");


            List<Returns_Recycler_DataCollect> tutorregister = new ArrayList<>();
            for (int i = 0; i < mallsListArray.length(); i++) {
                JSONObject tutorjson = mallsListArray.getJSONObject(i);
                Returns_Recycler_DataCollect tutorListBean = getcomments(tutorjson);
                tutorregister.add(tutorListBean);
            }
            return tutorregister;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Returns_Recycler_DataCollect getcomments(JSONObject jObject) throws JSONException {
        Returns_Recycler_DataCollect tutorData = new Returns_Recycler_DataCollect();








        return tutorData;
    }
}

