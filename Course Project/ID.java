package com.example.korlan.myapplication;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Korlan on 04.12.2015.
 */
public class ID {

    private String result;

    public ID (){
      result="";
    }


    public void setObject (String jo){
        Log.d("ID", jo);
        result = jo;
    }

    public String getID() {
        return result;
    }


}
