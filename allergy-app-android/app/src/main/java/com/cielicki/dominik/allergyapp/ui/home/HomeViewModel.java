package com.cielicki.dominik.allergyapp.ui.home;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.cielicki.dominik.allergyapp.MainActivity;
import com.cielicki.dominik.allergyapp.common.SettingsEnum;
import com.cielicki.dominik.allergyapp.common.Utils;
import com.cielicki.dominik.allergyapp.common.VoivodeshipEnum;
import com.cielicki.dominik.allergyapp.data.Endpoints;
import com.cielicki.dominik.allergyapp.data.RequestQueueSingleton;
import com.cielicki.dominik.allergyapprestapi.db.User;
import com.cielicki.dominik.allergyapprestapi.db.UserSettings;
import com.cielicki.dominik.allergyapprestapi.db.Voivodeship;
import com.cielicki.dominik.allergyapprestapi.db.model.QuestionList;
import com.cielicki.dominik.allergyapprestapi.db.model.UserSettingsList;
import com.cielicki.dominik.allergyapprestapi.db.model.VoivodeshipAllergenList;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Klasa służąca do przechowywania informacji, których nie chcemy wielokrotnie pobierać.
 * Na przykład czy zmianie orientacji telefonu.
 */
public class HomeViewModel extends AndroidViewModel {
    private static final String TAG = "HomeViewModel";

    private final MutableLiveData<User> currentUser = new MutableLiveData<User>();
    private final MutableLiveData<VoivodeshipAllergenList> currentVoivodeshipAllergenList = new MutableLiveData<VoivodeshipAllergenList>();
    private final MutableLiveData<Voivodeship> currentVoivodeship = new MutableLiveData<Voivodeship>();
    private final MutableLiveData<QuestionList> questionList = new MutableLiveData<QuestionList>();
    private final HashMap<Long, VoivodeshipAllergenList> voivodeshipAllergenListHashMap = new HashMap<>();
    private final HashMap<SettingsEnum, UserSettings> userSettingsHashMap = new HashMap<>();
    private boolean isGeoPermissionGranted = false;
    private MainActivity mainActivity = null;
    private Application application;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
        fetchAllergenList(null);
        fetchQuestionList();
    }

    public boolean isGeoPermissionGranted() {
        return isGeoPermissionGranted;
    }

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @SuppressLint("MissingPermission")
    public void setGeoPermissionGranted(boolean isGeoPermissionGranted) {
        this.isGeoPermissionGranted = isGeoPermissionGranted;

        if (isGeoPermissionGranted) {
            LocationManager locationManager = (LocationManager) application.getSystemService(Context.LOCATION_SERVICE);
            List<String> providers = locationManager.getProviders(true);

            Location location = null;

            for (int i=providers.size()-1; i>=0; i--) {
                location = locationManager.getLastKnownLocation(providers.get(i));
                if (location != null) {
                    break;
                }
            }

            double[] gps = new double[2];

            if (location != null) {
                gps[0] = location.getLatitude();
                gps[1] = location.getLongitude();

                Geocoder geocoder = new Geocoder(application.getBaseContext(), Locale.getDefault());

                List<Address> addresses;

                try {
                    addresses = geocoder.getFromLocation(gps[0], gps[1], 1);

                    if (addresses.size() > 0) {
                        VoivodeshipEnum voivodeshipEnum = VoivodeshipEnum.getVoivodeshipByName(addresses.get(0).getAdminArea());

                        if (voivodeshipEnum != null) {
                            setCurrentVoivodeship(voivodeshipEnum.voivodeship);
                        }
                    }
                } catch (IOException e) {

                }
            }

        }
    }

    // TODO Komentarz
    public void setCurrentVoivodeship(Voivodeship currentVoivodeship) {
        this.currentVoivodeship.setValue(currentVoivodeship);

        if (voivodeshipAllergenListHashMap.keySet().contains(currentVoivodeship.getId())) {
            this.currentVoivodeshipAllergenList.setValue(voivodeshipAllergenListHashMap.get(currentVoivodeship.getId()));

        } else {
            voivodeshipAllergenListHashMap.put(currentVoivodeship.getId(), new VoivodeshipAllergenList());
            fetchAllergenList(currentVoivodeship);
            this.currentVoivodeshipAllergenList.setValue(voivodeshipAllergenListHashMap.get(currentVoivodeship.getId()));
        }

    }

    // TODO: Komentarz
    public HashMap<SettingsEnum, UserSettings> getUserSettingsHashMap() {
        return userSettingsHashMap;
    }

    // TODO Komentarz
    public MutableLiveData<Voivodeship> getCurrentVoivodeship() {
        return this.currentVoivodeship;
    }

    /**
     * Pobiera ustawienia dla podanego użytkownika.
     *
     * @param user Obiekt użytkownika.
     */
    private void fetchSettings(User user) {
        try {
            JSONObject userJsonObject = new JSONObject(Utils.getGson().toJson(user));

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.POST, Endpoints.USER_SETTINGS.getEndpointUrl() + "/getUserSettings", userJsonObject, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            UserSettingsList userSettingsList = Utils.getGson().fromJson(response.toString(), UserSettingsList.class);

                            for (UserSettings userSettings: userSettingsList.getUserSettingsList()) {
                                userSettingsHashMap.put(SettingsEnum.getSetting(userSettings), userSettings);
                            }

                            UserSettings userSetting = getUserSettingsHashMap().get(SettingsEnum.START_LOCATION);

                            if (userSetting != null && VoivodeshipEnum.getVoivodeshipById(Long.parseLong(userSetting.getValue())) == VoivodeshipEnum.LOKALIZACJA) {
                                mainActivity.checkGeoPermission();

                            } else if (userSetting != null) {
                                for (VoivodeshipEnum voivodeshipEnum: VoivodeshipEnum.values()) {
                                    if (voivodeshipEnum.voivodeship.getId().toString().equals(userSetting.getValue())) {
                                        setCurrentVoivodeship(voivodeshipEnum.voivodeship);
                                        break;
                                    }
                                }
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

            RequestQueueSingleton.getInstance(application).addToQueue(jsonObjectRequest);
        } catch (JSONException e) {
            // Do nothing
        }
    }

    /**
     * Zwraca bieżacego użytkowika dla aplikacji.
     *
     * @return Obiekt bieżącego użytkonika.
     */
    public LiveData<User> getCurrentUser() {
        return currentUser;
    }

    /**
     * Ustawia bieżącego użytkownika dla aplikacji.
     * @param user
     */
    public void setCurrentUser(User user) {
        this.currentUser.setValue(user);
        fetchSettings(user);
    }

    /**
     * Zwraca listę alergenów w województwie.
     *
     * @return Zwraca listę alergenów w województwie.
     */
    public LiveData<VoivodeshipAllergenList> getCurrentVoivodeshipAllergenList() {
        return currentVoivodeshipAllergenList;
    }

    /**
     * Zwraca listę pytań.
     *
     * @return Zwraca listę pytań.
     */
    public LiveData<QuestionList> getQuestionList() {
        return questionList;
    }

    /**
     * Pobiera listę alergneów dla podanego województwa.
     *
     * @param voivodeship Województwo.
     */
    public void fetchAllergenList(Voivodeship voivodeship) {
        if (voivodeship == null) {
            return;
        }

        try {
            JSONObject voivodeshipJsonObject = new JSONObject(Utils.getGson().toJson(voivodeship));
            JSONObject array = new JSONObject();
            array.put("voivodeship", voivodeshipJsonObject);
            JSONObject date = new JSONObject();
            date.put("date", Utils.getGson().toJson(new Date()).replace("\"", ""));
//            JsonPrimitive date = Utils.getGson().fromJson(Utils.jsonTimestampDf.format(new Date()), JsonPrimitive.class);
            array.put("date", date);


            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.POST, Endpoints.VOIVODESHIP_ALLERGEN.getEndpointUrl() + "/getVoivodeshipAllergensByVoivodeshipAndDate", array, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            VoivodeshipAllergenList allergens = Utils.getGson().fromJson(response.toString(), VoivodeshipAllergenList.class);
                            voivodeshipAllergenListHashMap.put(voivodeship.getId(), allergens);
                            currentVoivodeshipAllergenList.setValue(voivodeshipAllergenListHashMap.get(voivodeship.getId()));
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // TODO: Handle error
                            Log.d(TAG, "ERROR on response");
                        }
                    }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("X-ALLERGY-APP", "RNQdP1DabMnp8n0w");

                    return headers;
                }
            };

            RequestQueueSingleton.getInstance(application).addToQueue(jsonObjectRequest);
        } catch (JSONException e) {
            // Do nothing
            Log.d(TAG, "fetchAllergenList: ", e);
        }
    }

    /**
     * Pobiera listę pytań.
     */
    public void fetchQuestionList() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, Endpoints.QUESTION.getEndpointUrl() + "/getQuestions", null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        QuestionList questions = Utils.getGson().fromJson(response.toString(), QuestionList.class);
                        questionList.setValue(questions);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.d(TAG, "ERROR on response");
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("X-ALLERGY-APP", "RNQdP1DabMnp8n0w");

                return headers;
            }
        };

        RequestQueueSingleton.getInstance(application).addToQueue(jsonObjectRequest);
    }
}