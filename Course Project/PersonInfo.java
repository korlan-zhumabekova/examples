package com.example.korlan.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class PersonInfo extends AppCompatActivity {

    private ListView l;
    private ArrayAdapter<ItemInfo> adapter;
    private JSONObject j;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_info);
        l = (ListView)findViewById(R.id.desclist);
        adapter = new ArrayAdapter<ItemInfo>(this,R.layout.activity_person_info, R.id.personInfo);
        String person = getIntent().getStringExtra("person");
        try {
            j = new JSONObject(person);
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

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_person_info, menu);
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
