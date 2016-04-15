package com.pontus.mjspamapp;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        InputStream imageStream = null;
        try {
            imageStream = getAssets().open("auction_af.png");


        Drawable d = Drawable.createFromStream(imageStream, null);

        ImageView iv = (ImageView) findViewById(R.id.imageView);

        iv.setImageDrawable(d);
        } catch (IOException e) {
            e.printStackTrace();
        }

        TextView textView = (TextView) findViewById(R.id.textView_link);
        assert textView != null;
        textView.setText(Html.fromHtml("<a href=\"http://mjspam.azurewebsites.net/\">MJspam</a>"));
        textView.setMovementMethod(android.text.method.LinkMovementMethod.getInstance());
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
                Intent iOngoing = new Intent(MainActivity.this, Ongoing.class);
                startActivity(iOngoing);
                break;
            case R.id.action_home:
                Toast.makeText(MainActivity.this, "You are already here!", Toast.LENGTH_SHORT).show();
        }

        //noinspection SimplifiableIfStatement
        //if (id == R.id.action_settings) {
        //return true;
        // }

        return super.onOptionsItemSelected(item);
    }
}
