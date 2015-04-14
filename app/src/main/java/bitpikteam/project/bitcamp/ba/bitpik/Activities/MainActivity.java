package bitpikteam.project.bitcamp.ba.bitpik.Activities;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import bitpikteam.project.bitcamp.ba.bitpik.Models.Product;
import bitpikteam.project.bitcamp.ba.bitpik.R;
import bitpikteam.project.bitcamp.ba.bitpik.Services.ServiceRequests;
import bitpikteam.project.bitcamp.ba.bitpik.singletons.UserData;


public class MainActivity extends ActionBarActivity {

    private SharedPreferences mSharedPreferences;

    private Button mButtonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Set image;
        ImageView logoImage = (ImageView) findViewById(R.id.main_logo_image);
        logoImage.setImageResource(R.drawable.logobitpik);

        mSharedPreferences = getPreferences(Context.MODE_PRIVATE);
        String username = mSharedPreferences.getString(
                getString(R.string.user_username_key), null);
        String password = mSharedPreferences.getString(
                getString(R.string.user_password_key), null);

        if(username != null && password != null){
            setUserData(username, password);
            loginUser();
            //TODO
            //Send user to other activity
        }

        final EditText editUsername = (EditText) findViewById(R.id.edit_text_username);
        final EditText editPassword = (EditText) findViewById(R.id.edit_text_password);
        mButtonLogin = (Button) findViewById(R.id.button_login);

        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editUsername.getText().toString();
                String password = editPassword.getText().toString();
                // if null provjere ovdje ubaciti!
                setUserData(email, password);
                loginUser();

            }
        });

    }

    /**
     * Request for web service to login;
     * URL : /finduser;
     * This is the json :
     *  obj.put("username", mUsername);
     *  obj.put("password", mPassword);
     *
     */
    private void loginUser() {
        String urlpart1 = getString(R.string.service_base);
        String urlpart2 = getString(R.string.service_login);
        String url = urlpart1 + urlpart2;
        Callback callback = loginVerification();
        String json = UserData.getInstance().toJson();

        // Pozivamo post metodu, jer saljemo username i password;
        ServiceRequests.post(url, json, callback);
    }

    /**
     * Our Callback for login POST!
     * @return
     */
    private Callback loginVerification() {
        return new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                //ako nema mreze; ako nema interneta; itd...
                makeToastFromAnotherThread(R.string.toast_try_again);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                //samo jednom se poziva jedan response -> tj. response.body().string();
                String responseJson = response.body().string();
                Log.e("SOMETHING", responseJson);
                try {
                    // pretvara iz Stringa u JSON Object! (bar pokusava);
                    JSONObject user = new JSONObject(responseJson);
                    int id = user.getInt("id");
                    if (id > 0) {
                        String username = user.getString("username");
                        UserData userData = UserData.getInstance();
                        userData.setId(id);
                        userData.setUsername(username);
                        // We call this method thus to save the User data into his SharedPreferences;
                        saveUserCredentials();

                        // we call the method that redirects the now logged user to a new activity to the. new layout(page);
                        goToIndexPage();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    makeToastFromAnotherThread(R.string.toast_try_again_catch);
                }
            }
        };
    }

    /**
     * Sets the username and password to our Singleton class
     * UserData - where we save the User's logged in (User of the phone)
     * his username and password;
     * @param username
     * @param password
     */
    private void setUserData(String username, String password) {
        UserData userData = UserData.getInstance();
        userData.setUsername(username);
        userData.setPassword(password);
    }

    /**
     * This method is used when we want to save the username and password to
     * the user's SharedPreferences;
     */
    private void saveUserCredentials() {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        UserData userData = UserData.getInstance();
        editor.putString( getString(R.string.user_username_key), userData.getUsername() );
        editor.putString( getString(R.string.user_password_key), userData.getPassword() );
        editor.commit();
    }

    /**
     * We redirect the User to the PostActivity page(Activity)
     * We redirect the User to the Index html pages (Index activity & layout page);
     */
    private void goToIndexPage() {
        Intent indexpage = new Intent(this, IndexPage.class);
        startActivity(indexpage);
    }

    /**
     * Makes a Toast for the screen;
     * But having to use the Handler & Looper;
     */
    private void makeToastFromAnotherThread(final int messageId) {
        // It basically asks that the Main Thread does the follwing action in the run() method;
        // (As this Callback(Thread) cannot!);
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, messageId, Toast.LENGTH_SHORT).show();
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
