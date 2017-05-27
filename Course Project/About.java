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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class About extends AppCompatActivity {

    private ListView l;
    private ArrayAdapter<ItemInfo> adapter;
    private JSONObject j;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        l = (ListView)findViewById(R.id.desclist);
        adapter = new ArrayAdapter<ItemInfo>(this,R.layout.activity_about, R.id.textAbout);
        String item = getIntent().getStringExtra("item");

        try {
            j = new JSONObject(item);
            Iterator<String> iter = j.keys();
            while (iter.hasNext()) {
                String s = iter.next();
                ItemInfo info =
                        new ItemInfo(s, j.getString(s));
                adapter.add(info);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        l.setAdapter(adapter);

        AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String key = adapter.getItem(position).getItemInfo();
                Log.d("about",key);

                try {
                    Log.d("about",j.getString("Borrowed"));
                    if (key.equals("Borrowed") && j.getString("Borrowed").equals("1")){
                        Log.d("about", "if stat");
                        Intent i = new Intent(About.this, History.class);
                        try {
                            String itemid = j.getString("EquipmentID");
                            Log.d("about",itemid);
                            i.putExtra("id", itemid);
                            startActivity(i);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        };
        l.setOnItemClickListener(listener);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_about, menu);
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
