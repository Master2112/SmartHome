package com.marcokoopman.weatherapp2;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by Marco on 14-6-2016.
 */
public class Prefrences extends AppCompatActivity{

    TextView usernamePref;
    ListView prefList;
    List<String> fullPrefList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prefrences);

        prefList = (ListView) findViewById(R.id.listView);
        fullPrefList = new ArrayList<String>();

        SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String name = sharedPref.getString("username", "");
        String password = sharedPref.getString("email", "");
        int id = sharedPref.getInt("id", 0);
        String arrayString = sharedPref.getString("prefsString","");

        try {
            JSONArray prefData = new JSONArray(arrayString);

            for(int i = 0 ; i < prefData.length(); i++)
            {
                fullPrefList.add(FormatAction(prefData.getJSONObject(i)));
            }
            
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,fullPrefList);
        prefList.setAdapter(arrayAdapter);



        usernamePref = (TextView) findViewById(R.id.usernamePref);

        usernamePref.setText("All Prefrences");

    }

    protected String FormatAction(JSONObject obj)
    {
        String str = "";
        try
        {
            str += obj.getString("action") + "\n";
            str += obj.getString("time") + "\n";
            str += obj.getString("date") + "\n";
        }
        catch(JSONException e)
        {
            str += "errorlel";
        }

        return str;
    }

}
