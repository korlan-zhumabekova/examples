package com.example.korlan.myapplication;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Korlan on 03.12.2015.
 */
public class Items {
    private JSONObject jo;



    public Items(JSONObject j) {

        this.jo = j;

    }

    public JSONObject getItems(){
        return jo;
    }

    @Override
    public String toString() {
        String n = "";
        try {

            n = jo.getString("Name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "Name: " + n;
    }



}
