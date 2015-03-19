package com.smalltasksathand.kaushik.applied_list;

/**
 * Created by kaushik on 3/15/2015.
 */
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by kaushik on 2/24/2015.
 */
public class connection1 {
    private static final String TAG ="connection" ;

    public void push(String a_id) throws IOException {
        RequestTask requestTask = new RequestTask(a_id);
        requestTask.execute("");
    }


    class RequestTask extends AsyncTask<String, String, String> {
        String a_id;


        public RequestTask(String a_id){

            this.a_id=a_id;
        }

        @Override
        protected String doInBackground(String... uri) {
            String responseString = "";
            try {
                Log.i(TAG,"start of try");
                HttpClient client = new DefaultHttpClient();
                String url = "https://api.mongolab.com/api/1/databases/group3/collections/applied/"+a_id+"?apiKey=5gq1g1JubzqFIgdxCK8oDJ6-ec1wyTI5";
                HttpDelete request = new HttpDelete(url);
                //creating json objects
                // double latLong[] = {longitude,latitude};
                //JSONArray jsonArray = new JSONArray(latLong);
                //JSONObject jobj = new JSONObject().put("type", "point");
                //jobj.put("coordinates", jsonArray);
                // JSONObject location= new JSONObject();
                // location.put("type","Point");
                // location.put("coordinates",jsonArray);
               JSONObject document = new JSONObject();

                document.put("description",null);
               // document.put("accept_id",System.currentTimeMillis());

                // document.put( "loc",location);

              //  request.setEntity(new ByteArrayEntity(document.toString().getBytes("UTF-8")));
                request.setHeader("Content-Type", "application/json");
                HttpResponse response = client.execute(request);
                StatusLine statusLine = response.getStatusLine();
                if(statusLine.getStatusCode() == HttpStatus.SC_OK){
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    response.getEntity().writeTo(out);
                    responseString = out.toString();
                    out.close();
                    //..more logic
                } else{
                    //Closes the connection.
                    response.getEntity().getContent().close();
                    throw new IOException(statusLine.getReasonPhrase());
                }
                Log.i(TAG, "executed the code");

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return responseString;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            //Do anything with response..
        }
    }
}
