package com.tasol.indolytics.maptest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

/**
 * Created by tasol on 13/10/17.
 */

public class Splash extends Activity {
    java.util.List<MapsPojo> mapList=new ArrayList<>();
    MapDBHelper mapDBHelper;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mapDBHelper=new MapDBHelper(Splash.this);
        if(!mapDBHelper.isTableExist("Maps")){
            mapDBHelper.addTable();
        }
        CommonUtility.setNetworkStateAvailability(this);
        if(CommonUtility.isNetworkAvailable()){
            Toast.makeText(this,"Network available ",Toast.LENGTH_LONG).show();
            getDataFromNet();
        }else {
            Toast.makeText(this,"Network Not available ",Toast.LENGTH_LONG).show();
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(Splash.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        }, 3000);
    }

    public void getDataFromNet(){
        Log.v("@@@WWE","Call Init");
        RequestQueue queue = Volley.newRequestQueue(this);
        final String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=23.037448,%2072.512274&radius=500&type=restaurant&key=AIzaSyAutNGRMYO3LylNHCSJCgMiTZ5wDIpw7cw";

        // prepare the Request
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        try{
                            if(response!=null&&response.length()>0){
                                JSONArray results=response.getJSONArray("results");
                                for (int i = 0; i < results.length(); i++) {
                                    JSONObject row=results.getJSONObject(i);
                                    JSONObject geometry=row.getJSONObject("geometry");
                                    JSONObject location=geometry.getJSONObject("location");
                                    mapList.add(new MapsPojo(String.valueOf(i),location.getString("lng"),row.getString("name"),row.getString("icon"),location.getString("lat")));
                                    if(mapDBHelper.countAllRows()<=20){
                                        mapDBHelper.addTableRow(String.valueOf(i),row.getString("name"),row.getString("icon"),location.getString("lat"),location.getString("lng"));
                                        Log.v("@@@WWE","Rows are inserted ");
                                    }
                                }
                                Log.v("@@@WWE","List : "+mapList.toString());
                            }
                        }catch (Exception je){
                            je.printStackTrace();
                        }
                        Log.d("Response", response.toString());
                        Log.v("@@@WWE","Response : "+response.toString());
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response",error.getMessage());
                        Log.v("@@@WWE","Response : "+error.getMessage());
                    }
                }
        );

// add it to the RequestQueue
        queue.add(getRequest);
    }
}
