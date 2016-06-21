package com.marcokoopman.weatherapp2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

/**
 * Created by Marco on 20-6-2016.
 */
public class login extends AppCompatActivity {

    public EditText userMacadress;
    public String userMacadressString;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginscreen);
    }

    public void loginChecker(View view)
    {
        userMacadress = (EditText) findViewById(R.id.macadresstext);
        userMacadressString = userMacadress.getText().toString();

        if(userMacadressString != "")
        {
            SharedPreferences sharedMacadress = getSharedPreferences("macadress", Context.MODE_PRIVATE);

            SharedPreferences.Editor editor = sharedMacadress.edit();

            editor.putString("userMac", userMacadressString);

            editor.commit();
            MainActivity.isLoggedIn = true;

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

}
