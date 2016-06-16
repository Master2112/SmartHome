package com.marcokoopman.weatherapp2;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by Marco on 14-6-2016.
 */
public class Prefrences extends AppCompatActivity{

    TextView usernamePref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prefrences);

        SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String name = sharedPref.getString("username", "");
        String password = sharedPref.getString("email", "");
        int id = sharedPref.getInt("id", 0);

        usernamePref = (TextView) findViewById(R.id.usernamePref);

        usernamePref.setText(name);

    }

}
