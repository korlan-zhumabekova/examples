package com.example.korlan.myapplication;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class List extends AppCompatActivity {

    private ListView lv;
  //  private ArrayAdapter<ItemInfo> adapter;
    private ArrayAdapter<Items> adapt;
    private String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        lv = (ListView)findViewById(R.id.list);
        result = getIntent().getStringExtra("result");

        if (result.equals("[]")){
            Intent i = new Intent(this, AddActivity.class);
            startActivity(i);
        }

        try {
            JSONArray j = new JSONArray(result);
            if (j.length()==1){
                JSONObject equip = j.getJSONObject(0);
                Intent i = new Intent(List.this, About.class);
                i.putExtra("item",equip.toString());
                startActivity(i);
               /* adapter = new ArrayAdapter<ItemInfo>(this, R.layout.activity_info, R.id.text);
                JSONObject jo = new JSONObject(j.getString(0));
                Iterator<String> iter = jo.keys();
                while (iter.hasNext()) {
                    String s = iter.next();
                    ItemInfo item =
                            new ItemInfo(s, jo.getString(s));
                    adapter.add(item);
                }
                lv.setAdapter(adapter);
                */
            } else if (j.length()>1){
                adapt = new ArrayAdapter<Items>(this, R.layout.activity_info, R.id.text);
                for (int i=0; i<j.length(); i++) {
                    JSONObject jo = new JSONObject(j.getString(i));
                    Items items = new Items(jo);
                    adapt.add(items);
                }
                lv.setAdapter(adapt);

                AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        JSONObject equip = adapt.getItem(position).getItems();
                        Intent i = new Intent(List.this, About.class);
                        i.putExtra("item",equip.toString());
                        startActivity(i);
                    }

                };
                lv.setOnItemClickListener(listener);
            }

            // Attach the adapter to the ListView


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
