package com.example.manish.food_order.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.manish.food_order.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {
    private static final String EMAIL = "email";
    LoginButton loginButton;
    TextView textView;
    CallbackManager callbackManager;
    SharedPreferences sharedPref;
    String timezone;
    String name;
    Button btnContinue;
    Boolean a=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try{
            super.onCreate(savedInstanceState);
            FacebookSdk.sdkInitialize(getApplicationContext());
            setContentView(R.layout.activity_main);

            final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
            int variable = sharedPref.getInt("variable", 0);

           //    startActivity(new Intent(MainActivity.this, SplashActivity.class));
            btnContinue = (Button)findViewById(R.id.buttonContinue);
            if(Profile.getCurrentProfile() != null || AccessToken.getCurrentAccessToken() != null){
                LoginManager.getInstance().logOut();
                btnContinue.setVisibility(View.INVISIBLE);
            }
            AppEventsLogger.activateApp(this);
            btnContinue.setVisibility(View.INVISIBLE);
            textView=(TextView)findViewById(R.id.textView);
            loginButton=(LoginButton)findViewById(R.id.login_button);

            callbackManager = CallbackManager.Factory.create();

            loginButton.registerCallback(callbackManager,
                    new FacebookCallback<LoginResult>() {
                        @Override
                        public void onSuccess(LoginResult loginResult) {
                            //textView.setText(loginResult.getAccessToken().getUserId()+"\n"+loginResult.getAccessToken().getToken());
                            AccessToken accessToken=loginResult.getAccessToken();
                            GraphRequest request = GraphRequest.newMeRequest(
                                    accessToken,
                                    new GraphRequest.GraphJSONObjectCallback() {
                                        @Override
                                        public void onCompleted(
                                                JSONObject object,
                                                GraphResponse response) {
                                            try {
                                                a=true;
                                                 timezone = object.getString("timezone");
                                                name=object.getString("name");
                                                Intent intent = new Intent(MainActivity.this, DescriptionWithLoginActivity.class);
                                                intent.putExtra("name", name);
                                                intent.putExtra("timezone", timezone);


                                           //     startActivity(intent1);
                                                startActivity(intent);




                                                btnContinue.setVisibility(View.VISIBLE);
                                            } catch (JSONException e) {
                                                e.printStackTrace();

                                            }
                                        }
                                    });
                            Bundle parameters = new Bundle();
                            parameters.putString("fields", "name,timezone");
                            request.setParameters(parameters);
                            request.executeAsync();


                        }

                        @Override
                        public void onCancel() {
                            textView.setText("denied");
                        }

                        @Override
                        public void onError(FacebookException exception) {
                           textView.setText("Error");
                        }
                    });



        }
       catch (Exception e){
            textView.setText(e.getMessage());
       }

          }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void AllRecipeActivity(View view) {
        startActivity(new Intent(MainActivity.this,ItemsActivity.class));
    }

    public void Continue(View view) {
        Intent intent = new Intent(MainActivity.this, DescriptionWithLoginActivity.class);
        intent.putExtra("name", name);
        intent.putExtra("timezone",timezone);
        startActivity(intent);
    }
}
