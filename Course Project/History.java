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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class History extends AppCompatActivity {

    private ListView l;
    private SearchHistoryTask sht;
    private EditText tv;
    public static ID id;
    private SearchPeopleTask spt;
    private Person p;
    private ArrayAdapter<Items> adapt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        l = (ListView)findViewById(R.id.people);
        adapt = new ArrayAdapter<Items>(this, R.layout.activity_history, R.id.text1);
        String equipId = getIntent().getStringExtra("id");
        Log.d("history", equipId);
        tv = (EditText)findViewById(R.id.etext);
        id = new ID();
        sht = new SearchHistoryTask(this,tv);
        sht.execute(equipId);
        String p = id.getID();
       // String people = tv.getText().toString();
        Log.d("history",p);

      /*  try {
            JSONArray j = new JSONArray(people);
            for (int i=0; i<j.length(); i++) {
                JSONObject person = j.getJSONObject(i);
                String personid = person.getString("PersonID");
                Log.d("history",personid);
                spt = new SearchPeopleTask(this,p);
                spt.execute(personid);
                String pid = p.getID();
                JSONArray array = new JSONArray(pid);
                JSONObject obj = array.getJSONObject(0);
                Items items = new Items(obj);
                adapt.add(items);
            }
            l.setAdapter(adapt);

            AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    JSONObject person = adapt.getItem(position).getItems();
                    Intent i = new Intent(History.this, Person.class);
                    i.putExtra("person",person.toString());
                    startActivity(i);
                }

            };
            l.setOnItemClickListener(listener);
        } catch (JSONException e) {
            e.printStackTrace();
        }
*/

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_history, menu);
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
