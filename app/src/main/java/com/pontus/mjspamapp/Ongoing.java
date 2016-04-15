package com.pontus.mjspamapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Ongoing extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ongoing);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ArrayList<Auction> auctionList = new ArrayList<Auction>();

        RequestQueue queue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, "http://nackademiska.azurewebsites.net/1/getongoingauctions", null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {

                try {
                    for (int i = 0; i<response.length(); i++) {

                        JSONObject j = null;

                        j = response.getJSONObject(i);

                        Auction a = new Auction(j.getInt("Id"), j.getString("Name"), j.getString("Description"), j.getString("StartTime"), j.getString("EndTime"), /*j.getString("Image"),*/ j.getInt("CategoryId"), j.getInt("SupplierId"), j.getDouble("AcceptPrice"), j.getBoolean("Sold"));

                        auctionList.add(a);
                    }

                    // skapa en adapter
                    ArrayAdapter<Auction> arrayAdapter = new ArrayAdapter<Auction>
                            (Ongoing.this, android.R.layout.simple_list_item_1, android.R.id.text1, auctionList);

                    // koppla till listview
                    ListView listView = (ListView) findViewById(R.id.listView_ongoing);

                    // skapa clickEvent
                    assert listView != null;
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                    Intent i = new Intent(Ongoing.this, Details.class);
                                    i.putExtra("auctionid", auctionList.get(position).getId());
                                    startActivity(i);

                            //Snackbar.make(view, String.valueOf("hello" + auctionList.size()) + "holla", Snackbar.LENGTH_LONG).show();

                        }
                    });

                    listView.setAdapter(arrayAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {


                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        queue.add(jsonArrayRequest);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id){
            case R.id.action_ongoing:
                Toast.makeText(Ongoing.this, "You are already here!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_home:
                Intent iOngoing = new Intent(Ongoing.this, MainActivity.class);
                startActivity(iOngoing);

        }

        return super.onOptionsItemSelected(item);
    }
}
