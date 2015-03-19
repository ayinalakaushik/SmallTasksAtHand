package com.smalltasksathand.kaushik.applied_list;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class ResultActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);


        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        ListView l= new ListView(this);
        setContentView(l);
        final ArrayList<JSONObject> j= new ArrayList<JSONObject>();
        int count=0;
        String temp="";
        for(int i=0;i<message.length();i++)
        {
            char c=message.charAt(i);

            if(c=='{')
                count++;
            if(count>0)
                temp=temp+c;
            if(c=='}')
            {
                count--;
                if(count==0)
                {
                    try {
                        j.add(new JSONObject(temp));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //System.out.println(temp);
                    temp="";
                }


            }
        }

        //converting arraylist to String array of jobs
        String jobs[]=new String[j.size()];
        for(int i=0;i<j.size();i++)
            try {
                jobs[i]=j.get(i).getString("description");
            } catch (JSONException e) {
                e.printStackTrace();
            }

        ArrayAdapter<String> ad= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,jobs);
        l.setAdapter(ad);
        Toast.makeText(getApplicationContext(),"Click on task to accept",Toast.LENGTH_LONG).show();
        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //  Toast.makeText(getApplicationContext(),"pressed "+id,Toast.LENGTH_SHORT).show();
                connection con= new connection();
                connection1 con1=new connection1();
                try {
                    JSONObject temp=j.get((int) id);
                    String tid=(temp.get("tid")).toString();
                    String e_id=(temp.get("id")).toString();
                    String descripton=(temp.get("description").toString());
                    String a_id=(temp.get("a_id")).toString();

                    //Toast.makeText(getApplicationContext(),oid,Toast.LENGTH_SHORT).show();
                    con.push("123",tid,e_id,descripton,a_id);
                    con1.push(descripton);
                    Toast.makeText(getApplicationContext(),"Accepted task",Toast.LENGTH_SHORT).show();

                    //deleting the accepted task
                    connection1 c=new connection1();
                    JSONObject o=new JSONObject(j.get((int)id).toString());
                    String k=(o.get("_id")).toString();
                    JSONObject r=new JSONObject(k);
                    String rr=r.get("$oid").toString();
                    System.out.println(rr);
                    c.push(rr);

                    String TAG="";
                    Log.v(TAG,"test is success");
                    finish();
                   // startActivity(getIntent());
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_result, menu);
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
