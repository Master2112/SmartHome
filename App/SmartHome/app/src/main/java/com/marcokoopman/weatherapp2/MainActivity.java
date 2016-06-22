package com.marcokoopman.weatherapp2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    // check of een user is ingelogd
    public static boolean isLoggedIn = false;

    boolean isDone = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_main);

         //krijg de login info om de linkbuilder te maken
         SharedPreferences getMac = getSharedPreferences("macadress", Context.MODE_PRIVATE);
         String macAdress = getMac.getString("userMac", "");

        if(isLoggedIn == false)
        {
            Intent intent = new Intent(this, login.class);
            startActivity(intent);
        }

         UserInfoHandler infoHandler = LinkBuilder("smarthome", "users?email=", macAdress);

         while (!isDone) {
             // wacht tot de 2e thread klaar is xD
         }

         TextView usernameView = (TextView) findViewById(R.id.homeTitle);
         usernameView.setText("Welcome " + infoHandler.username);

         TextView usernameinfoView = (TextView) findViewById(R.id.UsernameView);
         usernameinfoView.setText(infoHandler.username);

        TextView userEmailView = (TextView) findViewById(R.id.textView2);
        userEmailView.setText(infoHandler.email);

        TextView userMacAdressView = (TextView) findViewById(R.id.macadressView);
        userMacAdressView.setText(infoHandler.phoneId);



     }



    // de netwerk checker
    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if(networkInfo != null && networkInfo.isConnected())
        {
            isAvailable = true;
        }
        return isAvailable;
    }

    // een error gever
    private void alertUserAboutError() {
        AlertDialogFragment dialog = new AlertDialogFragment();
        dialog.show(getFragmentManager(), "error_dialog");
    }


    public UserInfoHandler LinkBuilder(String device, String datatype, String id)
    {

        String url =  "http://timfalken.com/hr/"+device+"/"+datatype + id;
        final UserInfoHandler infoHandler = new UserInfoHandler();

        if(isNetworkAvailable()) {
            // maakt een okHTTP client aan (plug-in)
            OkHttpClient client = new OkHttpClient();
            // vraag een URL op en bouw deze
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Call call = client.newCall(request);

            // een calback met een Failure en een response. Op response haalt hij data op
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                // haal API data op
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        // Hier zit alle data in van de JSON URL die je aanroept
                        String jsonData = response.body().string();
                        JSONObject APIdata = new JSONObject(jsonData);

                        if (response.isSuccessful()) {
                            // Haal hier gegevens op van de API
                            String username = APIdata.getString("name");
                            String email = APIdata.getString("email");
                            int id = APIdata.getInt("id");
                            String macAdress = APIdata.getString("phoneId");

                            JSONArray prefsArray = APIdata.getJSONArray("prefs");

                            //functie die alle data zet
                            infoHandler.username = username;
                            infoHandler.email = email;
                            infoHandler.id = id;
                            infoHandler.phoneId = macAdress;
                            infoHandler.prefArray = prefsArray;

                            String arrayString = prefsArray.toString();

                            // maak een session aan genaamd sharedprefs en stop hier alle data in
                            SharedPreferences sharedPrefs = getSharedPreferences("userInfo",Context.MODE_PRIVATE);

                            SharedPreferences.Editor editor = sharedPrefs.edit();

                            // dit is de informatie die in sharedprefs staat
                            editor.putString("username", username);
                            editor.putString("email", email);
                            editor.putInt("id", id);
                            editor.putString("macAdress", macAdress);
                            editor.putString("prefsString", arrayString);



                            // hier voegt hij alle informatie toe
                            editor.commit();


                            isDone = true;
                        } else {
                            alertUserAboutError();
                        }
                    } catch (IOException e) {
                        Log.e(TAG, "Exception caught:", e);
                    }
                    catch(JSONException e){
                        Log.e(TAG, "Exception caught:", e);
                    }
                }
            });
        }
        // Als er geen netwerk is gevonden door de netwerk checker krijg je deze error
        else {
            Toast.makeText(this, "Network is fcked", Toast.LENGTH_LONG).show();
        }
        return infoHandler;
    }

    public void GotoViewPrefrences(View view)
    {
        Intent intent = new Intent(this, Prefrences.class);
        startActivity(intent);
    }

}
