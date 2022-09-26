package com.cielicki.dominik.allergyapp.common;

import android.content.Context;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.cielicki.dominik.allergyapp.data.Endpoints;
import com.cielicki.dominik.allergyapp.data.RequestQueueSingleton;
import com.cielicki.dominik.allergyapprestapi.db.UserSettings;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Klasa zwierająca funkcjonalności wykorzystywana w innych klasach.
 */
public class Utils {
    private static SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
    private static SimpleDateFormat messageDf = new SimpleDateFormat("dd MMMM yyyy");
    private static SimpleDateFormat messageTimestampDf = new SimpleDateFormat("HH:mm:ss");
    private static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX").create();

    /**
     * Wyświetla powiadomienie.
     *
     * @param context Kontekst, w którym ma zostać wyświetlone powiadomienie.
     * @param view Widok, w którym ma zostać wyświetlone powiadomienie.
     * @param message Tekst wiadomości.
     */
    public static void showMessage(Context context, View view, String message) {
        Snackbar snackbar = Snackbar.make(context, view, message, 500);
        snackbar.show();
    }

    /**
     * Formatuje podaną datę do formatu dd:MM:yyyy.
     *
     * @param date Data.
     * @return Data jako string w formacie dd:MM:yyyy.
     */
    public static String formatDate(Date date) {
        return df.format(date);
    }

    /**
     * Formatuje podaną datę do formatu dd MMMM yyyy (np. 01 styczeń 1991).
     *
     * @param date Data.
     * @return Data jako string w formacie dd MMMM yyyy (np. 01 styczeń 1991).
     */
    public static String formatMessageDate(Date date) {
        return messageDf.format(date);
    }

    /**
     * Formatuje podaną datę do formatu HH:mm:ss.
     *
     * @param date Data.
     * @return Data jako string w formacie HH:mm:ss.
     */
    public static String formatMessageTimestamp(Date date) {
        return messageTimestampDf.format(date);
    }

    /**
     * Zwraca instancje Gson z formatem daty.
     *
     * @return Zwraca instancje Gson z formatem daty.
     */
    public static Gson getGson() {
        return gson;
    }

    // TODO: Komentarz
    public static void saveSetting(Context context, UserSettings userSettings) {
        try {
            JSONObject userSettingJsonObject = new JSONObject(Utils.getGson().toJson(userSettings));

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.POST, Endpoints.USER_SETTINGS.getEndpointUrl() + "/addUserSetting", userSettingJsonObject, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                        }
                    }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("X-ALLERGY-APP", "RNQdP1DabMnp8n0w");

                    return headers;
                }
            };

            RequestQueueSingleton.getInstance(context).addToQueue(jsonObjectRequest);
        } catch (JSONException e) {
            // Do nothing
        }
    }
}
