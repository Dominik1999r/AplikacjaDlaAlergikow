package com.cielicki.dominik.allergyapp.data;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.cielicki.dominik.allergyapprestapi.db.Chat;

/**
 * Klasa tworząca kolejkę requestów wysyłanych do REST API.
 */
public class RequestQueueSingleton {
    private static RequestQueueSingleton instance;
    private RequestQueue requestQueue;

    public interface VolleyChatCallback {
        void onSuccess(Chat chat);
    }

    private RequestQueueSingleton(Context context) {
        requestQueue = Volley.newRequestQueue(context);
    }

    /**
     * Zwraca lub tworzy instancję kolejki requestów.
     *
     * @param context Kontekst.
     * @return Instancja kolejki requestów.
     */
    public static RequestQueueSingleton getInstance(Context context) {
        if (instance == null) {
            instance = new RequestQueueSingleton(context);
        }

        return instance;
    }

    /**
     * Dodaje request do kolejki.
     *
     * @param jsonRequest Obiekt requestu.
     * @param <T> Typ requestu.
     */
    public <T> void addToQueue (JsonRequest<T> jsonRequest) {
        requestQueue.add(jsonRequest);
    }
}
