package com.example.korlan.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Korlan on 18.09.2015.
 */
public class SearchHistoryTask extends AsyncTask<String, Void, String> {

    private Context context;
    private String url_params;
    public static ID id;
    private EditText t;


    final public static String TAG = "SearchHistoryTask";

    public SearchHistoryTask(Context context, EditText e) {
        this.context=context;
        this.t = e;
    }

    @Override
    protected String doInBackground(String... params) {
        String result = "Unsuccessful connection.";

        try
        {
            // This call to encode is only available in API 19. whereas the simpler one below is deprecated
            // String url_params = URLEncoder.encode(params[0], java.nio.charset.StandardCharsets.US_ASCII.name());
            url_params = URLEncoder.encode(params[0]);

            URL url = new URL("http://10.110.111.204/ems/index.php/application/get_json_responsible/" + url_params);
            Log.d(TAG, "URL is: " + url);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if ( conn.getResponseCode() != 200 )
            {
                // OMDB sends error messages encoded in the JSON, rather than using the HTTP codes
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            result = br.readLine();

            try
            {
                JSONArray j = new JSONArray(result);
                Log.d("sht",j.toString());
                JSONObject jo = j.getJSONObject(0);
                Log.d("sht",jo.toString());
                // If key doesn't exist, then exception is thrown
                if ( jo.has( "Response" )) {
                    if (jo.getString("Response").equalsIgnoreCase("False")) {
                        Log.d(TAG, "Error reported from source: " + jo.getString("Error"));
                        result = jo.getString( "Error" );
                    }
                    else
                        result = processJSON( jo );
                }
            }
            catch (JSONException je)
            {
                Log.d(TAG, "JSONException: " + je.getMessage());

            }

            conn.disconnect();

        } catch (MalformedURLException e) {

            Log.d(TAG, "Malformed URL.");
            e.printStackTrace();
        } catch (IOException e) {

            Log.d(TAG, "IOException was thrown.");
            e.printStackTrace();
        }

        Log.d(TAG, result);
        return result;
    }

    private String processJSON( JSONObject jo ) throws JSONException {

        return (jo.toString());
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        History.id.setObject(s);
    }
}
