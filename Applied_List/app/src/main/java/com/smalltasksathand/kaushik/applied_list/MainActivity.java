package com.smalltasksathand.kaushik.applied_list;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;


public class MainActivity extends Activity {
    public final static String EXTRA_MESSAGE = "";
    class Connection {
        public void get() throws IOException {
            RequestTask requestTask = new RequestTask();
            requestTask.execute();
        }


        class RequestTask extends AsyncTask<String, String, String> {
            final String Tag = null;
            String responseString = "";
            BufferedReader in;


            public RequestTask(){

            }

            @Override
            protected String doInBackground(String... uri) {
                try {

                  //  radius=radius*1000;

                    String oid="5506312ce4b0603bec07c83c";
                    URL url= new URL("https://api.mongolab.com/api/1/databases/group3/collections/applied?q={}&apiKey=5gq1g1JubzqFIgdxCK8oDJ6-ec1wyTI5") ;
                    BufferedReader br=new BufferedReader(new InputStreamReader(url.openStream()));
                    responseString=br.readLine();

                    return responseString;
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return responseString;
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                //Do anything with response..
                Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
                intent.putExtra(EXTRA_MESSAGE, result);
                startActivity(intent);

            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button b=(Button) findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Connection con=new Connection();
                try {
                    con.get();
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
