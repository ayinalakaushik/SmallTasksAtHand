package com.smalltasksathand.kaushik.jobsearch;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;


public class MainActivity extends Activity {
    double longitude;
    double latitude;
    int radius;
    public final static String EXTRA_MESSAGE = "";
    class Connection {
        public void get(int radius, double longitude,double latitude) throws IOException {
            RequestTask requestTask = new RequestTask(radius,longitude,latitude);
            requestTask.execute();
        }


        class RequestTask extends AsyncTask<String, String, String> {
            int radius;
            double longitude,latitude;
            final String Tag = null;
            String responseString = "";
            BufferedReader in;


            public RequestTask(int radius, double longitude,double latitude){
                this.radius=radius;
                this.longitude=longitude;
                this.latitude=latitude;
            }

            @Override
            protected String doInBackground(String... uri) {
                try {
                   /* HttpClient client = new DefaultHttpClient();
                    HttpGet request = new HttpGet();
                    String url ="https://api.mongolab.com/api/1/databases/group3/collections/Jobs?q={loc:{$geoWithin:{$centerSphere:[[-94.5844458,39.0418465],1000]}}}&apiKey=5gq1g1JubzqFIgdxCK8oDJ6-ec1wyTI5";
                   System.out.print(url);
                    URL u=new URL(url);
                    request.setURI(new URI(url));
                    HttpResponse response = client.execute(request);
                    response.getStatusLine().getStatusCode();

                    in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                    StringBuffer sb = new StringBuffer("");
                    String l = "";
                    String nl = System.getProperty("line.separator");
                    while ((l = in.readLine()) !=null){
                        sb.append(l + nl);
                    }
                    in.close();
                    responseString = sb.toString();*/
                    radius=radius*(1609);
                    URL url= new URL("https://api.mongolab.com/api/1/databases/group3/collections/Jobs?q={loc:{$geoWithin:{$centerSphere:[["+longitude+","+latitude+"],"+radius+"]}}}&apiKey=5gq1g1JubzqFIgdxCK8oDJ6-ec1wyTI5") ;
                    BufferedReader br=new BufferedReader(new InputStreamReader(url.openStream()));
                    responseString=br.readLine();
                    
                    return responseString;
                } catch (Exception e) {
                    e.printStackTrace();
                }  /*finally{
                    if (in != null){
                        try{
                            in.close();
                            return responseString;
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }*/

            return responseString;
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                //Do anything with response..
                Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
                intent.putExtra(EXTRA_MESSAGE, result);
                startActivity(intent);

                //execute();

            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button search = (Button)findViewById(R.id.button);
        final EditText r=(EditText)findViewById(R.id.editText);
       // final TextView result = (TextView) findViewById(R.id.result);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
               String provider=locationManager.NETWORK_PROVIDER;
               Location location = locationManager.getLastKnownLocation(provider);
               longitude=location.getLongitude();
               latitude=location.getLatitude();
                radius=Integer.parseInt(r.getText().toString());
                Connection con=new Connection();
                //Toast.makeText(getApplicationContext(),r.getText().toString()+longitude+latitude,Toast.LENGTH_SHORT).show();
                try {
                    con.get(radius,longitude,latitude);
                } catch (IOException e) {
                    e.printStackTrace();
                }


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
