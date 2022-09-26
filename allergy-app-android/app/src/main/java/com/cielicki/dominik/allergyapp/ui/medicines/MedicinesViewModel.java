package com.cielicki.dominik.allergyapp.ui.medicines;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.cielicki.dominik.allergyapp.common.Utils;
import com.cielicki.dominik.allergyapp.data.Endpoints;
import com.cielicki.dominik.allergyapp.data.RequestQueueSingleton;
import com.cielicki.dominik.allergyapprestapi.db.Medicine;
import com.cielicki.dominik.allergyapprestapi.db.model.MedicineCommentList;
import com.cielicki.dominik.allergyapprestapi.db.model.MedicineList;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Klasa służąca do przechowywania informacji, których nie chcemy wielokrotnie pobierać.
 * Na przykład czy zmianie orientacji telefonu.
 */
public class MedicinesViewModel extends ViewModel {

    private final MutableLiveData<MedicineList> medicineList = new MutableLiveData<MedicineList>();
    private MutableLiveData<MedicineCommentList> medicineCommentList = new MutableLiveData<MedicineCommentList>();
    private final MutableLiveData<Medicine> currentMedicine = new MutableLiveData<Medicine>();
    private Application application;

    {
        fetchMedicines();
    }

    public MedicinesViewModel() {
    }

    public MedicinesViewModel(@NonNull Application application) {
        this.application = application;
    }

    public LiveData<MedicineList> getMedicineList() {
        return medicineList;
    }

    public void setMedicineList(MedicineList list) {
        medicineList.setValue(list);
    }

    /**
     * Pobiera bieżący lek.
     *
     * @return Obiekt bieżącego leku.
     */
    public LiveData<Medicine> getCurrentMedicine() {
        return currentMedicine;
    }

    /**
     * Ustawie bieżący lek.
     *
     * @param medicine Obiekt bieżącego leku.
     */
    public void setCurrentMedicine(Medicine medicine) {
        currentMedicine.setValue(medicine);
        fetchMedicineComments(medicine);
        fetchMedicineRating(medicine);
    }

    public LiveData<MedicineCommentList> getMedicineCommentList() {
        return medicineCommentList;
    }

    /**
     * Pobiera komentarze dla podanego leku z serwera.
     *
     * @param medicine Obiekt leku.
     */
    private void fetchMedicineComments(Medicine medicine) {
        try {
            JSONObject medicineJsonObject = new JSONObject(Utils.getGson().toJson(medicine));

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, Endpoints.MEDICINE_COMMENT.getEndpointUrl() + "/getMedicineComments", medicineJsonObject, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        medicineCommentList.setValue(Utils.getGson().fromJson(response.toString(), MedicineCommentList.class));

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        medicineCommentList = new MutableLiveData<MedicineCommentList>();

                    }
                }){
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
     * Pobiera listę leków z serwera.
     */
    private void fetchMedicines() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, Endpoints.MEDICINE.getEndpointUrl() + "/getMedicines", null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        medicineList.setValue(Utils.getGson().fromJson(response.toString(), MedicineList.class));

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
    }

    /**
     * Pobiera średnią ocen dla podanego leku.
     * @param medicine Obiekt leku.
     */
    private void fetchMedicineRating(Medicine medicine) {
        try {
            JSONObject medicineJsonObject = new JSONObject(Utils.getGson().toJson(medicine));

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.POST, Endpoints.MEDICINE.getEndpointUrl() + "/getMedicineRating", medicineJsonObject, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                medicine.setAverageScore(new BigDecimal(response.getDouble("rating")));
                                medicineList.setValue(medicineList.getValue());
                            } catch (JSONException e) {

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
}