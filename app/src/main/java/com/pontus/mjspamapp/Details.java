package com.pontus.mjspamapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Details extends AppCompatActivity {

    private Auction a;
    private String link_val = "http://mjspam.azurewebsites.net/secondindex.html?auctionid=";
    int auctionid = 0;
    double highestBid = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        auctionid = getIntent().getIntExtra("auctionid", 0);

        RequestQueue queue = Volley.newRequestQueue(this);

        final ArrayList<Double> bidList = new ArrayList<>();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, "http://nackademiska.azurewebsites.net/1/getoffers?auctionid=" + auctionid, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                        try {
                            for (int i = 0; i < response.length(); i++) {

                                JSONObject j = null;
                                j = response.getJSONObject(i);
                                double bid = j.getDouble("Offer");
                                bidList.add(bid);
                            }

                            double tempHigh = 0;
                            for (int i = 0; i < bidList.size(); i++) {

                                if (bidList.get(i) > tempHigh) {
                                    tempHigh = bidList.get(i);
                                }
                            }
                            highestBid = tempHigh;

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

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, "http://nackademiska.azurewebsites.net/1/getauctiondetails?auctionid=" + auctionid, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                                 a = new Auction(
                                        response.getInt("Id"),
                                        response.getString("Name"),
                                        response.getString("Description"),
                                        response.getString("StartTime"),
                                        response.getString("EndTime"),
                                        response.getInt("CategoryId"),
                                        response.getInt("SupplierId"),
                                        response.getDouble("AcceptPrice"),
                                        response.getBoolean("Sold"));


                            byte[] base64String = Base64.decode(response.getString("Image"), Base64.DEFAULT);
                            Bitmap bitmap = BitmapFactory.decodeByteArray(base64String, 0, base64String.length);
                            ImageView imageView2;
                            imageView2 = (ImageView) findViewById(R.id.imageView2);
                            imageView2.setImageBitmap(bitmap);



                            TextView textView = (TextView) findViewById(R.id.textView_title);
                            TextView textView3 = (TextView) findViewById(R.id.textView_description);
                            TextView textView4 = (TextView) findViewById(R.id.textView_bids);



                            textView.setText(a.getName());
                            textView3.setText("Acceptpris:  " + a.getAcceptPrice() + "kr" + "\n\n" +
                                            "Ledande bud:  " + highestBid + "kr" + "\n");
                            textView4.setText(a.getDescription() + "\n\n\n" +
                                            "Startade: " + a.getStartTime()  + "\n\n" +
                                            "Slutar: " + a.getEndTime());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }, new Response.ErrorListener() {


                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        queue.add(jsonObjectRequest);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                link_val += auctionid;
                String body = "<a href=\"" + link_val + "\">" + link_val + "</a>";

                Intent sendMail = new Intent(Intent.ACTION_SEND);
                sendMail.setType("text/mail");

                sendMail.putExtra(Intent.EXTRA_SUBJECT, "Kolla in den här auktionen!");
                sendMail.putExtra(Intent.EXTRA_TEXT, a.getName() + "\n\n" +
                        a.getDescription() + "\n\n" +
                        "Startade: " + a.getStartTime()  + "\n" +
                        "Slutar: " + a.getEndTime()  + "\n\n" +
                        "Acceptpris" + a.getAcceptPrice() + "kr" + "\n" +
                        "Högsta bud:  " + highestBid + "kr" +
                        Html.fromHtml(body));


                startActivity(Intent.createChooser(sendMail, "Välj email-app"));
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
                Intent iOngoing = new Intent(Details.this, Ongoing.class);
                startActivity(iOngoing);
                break;
            case R.id.action_home:
                Intent iMainActivity = new Intent(Details.this, MainActivity.class);
                startActivity(iMainActivity);

        }

        return super.onOptionsItemSelected(item);
    }


    }


