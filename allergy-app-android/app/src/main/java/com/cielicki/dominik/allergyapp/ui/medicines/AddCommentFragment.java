package com.cielicki.dominik.allergyapp.ui.medicines;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.cielicki.dominik.allergyapp.common.Utils;
import com.cielicki.dominik.allergyapp.data.Endpoints;
import com.cielicki.dominik.allergyapp.data.RequestQueueSingleton;
import com.cielicki.dominik.allergyapp.databinding.AddCommentFragmentBinding;
import com.cielicki.dominik.allergyapp.ui.home.HomeViewModel;
import com.cielicki.dominik.allergyapprestapi.db.Medicine;
import com.cielicki.dominik.allergyapprestapi.db.MedicineComment;
import com.cielicki.dominik.allergyapprestapi.db.MedicineCommentId;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Klasa implementująca zachowanie w oknie dodawania komentarza dla leku.
 */
public class AddCommentFragment extends Fragment {

    private MedicinesViewModel mViewModel;
    private HomeViewModel mHomeViewModel;
    private AddCommentFragmentBinding binding;
    private Medicine medicine;
    private boolean isRatingSet = false;

    public static AddCommentFragment newInstance() {
        return new AddCommentFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(getParentFragment()).get(MedicinesViewModel.class);
        mHomeViewModel = new ViewModelProvider(getActivity()).get(HomeViewModel.class);

        binding = AddCommentFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Button saveCommentButton = binding.button;
        EditText commentEditText = binding.textView;
        RatingBar ratingBar = binding.ratingBar;

        // Zachowania przy kliknięciu przycisku "Zapisz opinię".
        saveCommentButton.setOnClickListener((view) -> {
            if (commentEditText.getText().length() == 0) {
                Utils.showMessage(getContext(), view, "Komentarz nie może być pusty.");
                return;
            }

            if (! isRatingSet) {
                Utils.showMessage(getContext(), view, "Ocena nie została ustawiona.");
                return;
            }

            sendComment(commentEditText.getText().toString(), ratingBar.getRating());
            getActivity().onBackPressed();
        });

        ratingBar.setOnRatingBarChangeListener((ratingBar1, rating, fromUser) -> {
            if (fromUser) {
               isRatingSet = true;
            }
        });

        return root;
    }

    /**
     * Dodaje komentarz do leku.
     *
     * @param comment Tekst komentarza.
     * @param rating Ocena leku.
     */
    private void sendComment(String comment, float rating) {
        MedicineComment medicineComment = new MedicineComment();
        medicineComment.setComment(comment);
        medicineComment.setRating(new BigDecimal(rating));

        MedicineCommentId medicineCommentId = new MedicineCommentId();
        medicineCommentId.setDate(new Date());
        medicineCommentId.setMedicine(mViewModel.getCurrentMedicine().getValue());
        medicineCommentId.setUser(mHomeViewModel.getCurrentUser().getValue());

        medicineComment.setId(medicineCommentId);

        JSONObject jsonObject;
        try {
             jsonObject = new JSONObject(Utils.getGson().toJson(medicineComment));
        } catch (JSONException e) {
            return;
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, Endpoints.MEDICINE_COMMENT.getEndpointUrl() + "/addMedicineComment", jsonObject, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        mViewModel.setCurrentMedicine(mViewModel.getCurrentMedicine().getValue());
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.d("AddComment", "Error " + error.toString());
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("X-ALLERGY-APP", "RNQdP1DabMnp8n0w");

                return headers;
            }
        };

        RequestQueueSingleton.getInstance(getContext()).addToQueue(jsonObjectRequest);
        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(binding.getRoot().getWindowToken(), 0);
    }
}