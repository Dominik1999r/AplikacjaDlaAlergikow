package com.cielicki.dominik.allergyapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.cielicki.dominik.allergyapp.common.Utils;
import com.cielicki.dominik.allergyapp.data.Endpoints;
import com.cielicki.dominik.allergyapp.data.RequestQueueSingleton;
import com.cielicki.dominik.allergyapp.databinding.ActivityLoginBinding;
import com.cielicki.dominik.allergyapprestapi.db.User;
import com.cielicki.dominik.allergyapprestapi.db.model.UserList;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import androidx.navigation.ui.AppBarConfiguration;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Aktywność służąca do logowania.
 */
public class LoginActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        stopLoading();

        CallbackManager callbackManager = CallbackManager.Factory.create();

        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList("email"));

        // Obsługa logowania do Facebook'a.
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                startLoading();
                getEmail(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                // Do nothing
            }

            @Override
            public void onError(FacebookException exception) {
                stopLoading();
                // Do nothing
            }
        });

        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();

        if (isLoggedIn) {
            startLoading();
            getEmail(accessToken);
        }
    }

    /**
     * Pobieranie adresu email z Facebook'a.
     *
     * @param accessToken
     */
    private void getEmail(AccessToken accessToken) {
        GraphRequest request = GraphRequest.newMeRequest(
                accessToken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.v("LoginActivity", response.toString());

                        // Application code
                        try {
                            String email = object.getString("email");
                            fetchUser(email);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            stopLoading();
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "email");
        request.setParameters(parameters);
        request.executeAsync();
    }

    /**
     * Pobierania użytkowina z RestApi.
     *
     * @param email Email użytkownika.
     */
    private void fetchUser(String email) {
        try {
            Profile profile = Profile.getCurrentProfile();
            User user = new User();
            user.setName(profile.getFirstName());
            user.setLastName(profile.getLastName());
            user.setUsername(profile.getName());
            user.setEmail(email);
            JSONObject userJsonObject = new JSONObject(Utils.getGson().toJson(user));

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.POST, Endpoints.USER.getEndpointUrl() + "/getUser", userJsonObject, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            UserList userList = Utils.getGson().fromJson(response.toString(), UserList.class);
                            if (userList.getUserList().size() > 0) {
                                openApp(userList.getUserList().get(0));

                            } else {
                                addUser(user);
                            }
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // TODO: Handle error
                            Log.d("TEST", "onErrorResponse: ");
                        }
                    }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("X-ALLERGY-APP", "RNQdP1DabMnp8n0w");

                    return headers;
                }
            };

            RequestQueueSingleton.getInstance(this).addToQueue(jsonObjectRequest);
        } catch (JSONException e) {
            // Do nothing
        }
    }

    /**
     * Stworzenie nowego użytkownika w aplikacji.
     *
     * @param user Obiekt użytkownika.
     */
    private void addUser(User user) {
        try {
            JSONObject userJsonObject = new JSONObject(Utils.getGson().toJson(user));

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.POST, Endpoints.USER.getEndpointUrl() + "/addUser", userJsonObject, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            User user = Utils.getGson().fromJson(response.toString(), User.class);
                            if (user != null) {
                                openApp(user);

                            } else {
                                // TODO: ERROR
                            }
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // TODO: Handle error

                        }
                    }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("X-ALLERGY-APP", "RNQdP1DabMnp8n0w");

                    return headers;
                }
            };

            RequestQueueSingleton.getInstance(this).addToQueue(jsonObjectRequest);
        } catch (JSONException e) {
            // Do nothing
        }
    }

    /**
     * Uruchomienie MainActivity w przypadku poprawnego logowania.
     * Dodatkowo dodawany jest AccessTokenTracker obserwujący token logowania do Facebook'a.
     *
     * @param user Obiekt użytkownika, który zostanie przekazany do MainActivity.
     */
    public void openApp(User user) {
        AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(@Nullable AccessToken oldAccessToken, @Nullable AccessToken currentAccessToken) {
                if (currentAccessToken == null) {
                    restartApp();
                }
            }
        };

        accessTokenTracker.startTracking();

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("USER", user);
        startActivity(intent);
    }

    /**
     * Restatuje aplikację po wylogowaniu.
     */
    public void restartApp() {
        Intent mStartActivity = new Intent(this, LoginActivity.class);
        int mPendingIntentId = 123456;
        PendingIntent mPendingIntent = PendingIntent.getActivity(this, mPendingIntentId, mStartActivity, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager mgr = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent);
        System.exit(0);
    }

    /**
     * Ukrywa przycisk i pokazuje progressBar.
     */
    private void startLoading() {
        binding.loginButton.setVisibility(View.GONE);
        binding.progressBar.setVisibility(View.VISIBLE);
    }

    /**
     * Ukrywa progressBar i pokazuje przycisk.
     */
    private void stopLoading() {
        binding.loginButton.setVisibility(View.VISIBLE);
        binding.progressBar.setVisibility(View.GONE);
    }
}