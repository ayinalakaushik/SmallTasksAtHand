package com.smalltasksathand.kaushik.smalltasksathand;

import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

import java.io.IOException;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText description = (EditText) findViewById(R.id.description);
        Button post= (Button) findViewById(R.id.button);
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  Toast.makeText(getApplicationContext(),description.getText(),Toast.LENGTH_SHORT).show();
                //System.out.print("sucess");
                try {
                    connection con= new connection();
                    double longitude,latitude;
                    LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    Criteria c=new Criteria();
                    String provider=locationManager.getBestProvider(c,false);
                    Location location = locationManager.getLastKnownLocation(provider);
                    longitude=location.getLongitude();
                    latitude=location.getLatitude();
                    /*GoogleApiClient mGoogleApiClient=new GoogleApiClient.Builder(this)
                            .addConnectionCallbacks(this)
                            .addOnConnectionFailedListener(this)
                            .addApi(LocationServices.API)
                            .build();*/

                    con.push("12345",description.getText().toString(),longitude,latitude);
                } catch (IOException e) {
                    Toast.makeText(getApplicationContext(),"error",Toast.LENGTH_SHORT);
                    e.printStackTrace();
                }
                Toast.makeText(getApplicationContext(),"task posted",Toast.LENGTH_SHORT).show();
            }
        });

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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
