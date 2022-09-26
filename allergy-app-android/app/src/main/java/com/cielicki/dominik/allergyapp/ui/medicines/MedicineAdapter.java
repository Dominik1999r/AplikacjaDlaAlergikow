package com.cielicki.dominik.allergyapp.ui.medicines;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cielicki.dominik.allergyapp.R;
import com.cielicki.dominik.allergyapprestapi.db.Medicine;
import com.cielicki.dominik.allergyapprestapi.db.model.MedicineList;

import java.math.BigDecimal;

/**
 * Adapter dla RecyclerView wyświetlającego listę leków.
 */
public class MedicineAdapter extends RecyclerView.Adapter<MedicineAdapter.ViewHolder> {

    private MedicineList medicineList;
    private MedicinesViewModel medicinesViewModel;

    MedicineAdapter(MedicineList medicineList, MedicinesViewModel medicinesViewModel) {
        this.medicineList = medicineList;
        this.medicinesViewModel = medicinesViewModel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View medicineView = layoutInflater.inflate(R.layout.medicine_row, parent, false);

        return new ViewHolder(context, medicineView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Medicine medicine = medicineList.getMedicineList().get(position);

        TextView nameTextView = holder.nameTextView;
        nameTextView.setText(medicine.getName());

        TextView priceRangeTextView = holder.priceRangeTextView;
        priceRangeTextView.setText(medicine.getPriceLow() + "zł - " + medicine.getPriceHigh() + "zł");


        BigDecimal avgRating = medicine.getAverageScore();

        if (avgRating != null) {
            holder.averageRating.setRating(avgRating.floatValue());

        } else {
            holder.averageRating.setRating(0);
        }

        Button button = holder.messageButton;
        button.setText("Szczegóły");
        button.setOnClickListener((event) -> {
            medicinesViewModel.setCurrentMedicine(medicine);
            Fragment fragment = ((AppCompatActivity)holder.context).getSupportFragmentManager().getFragments().get(0);
            FragmentManager fragmentManager = fragment.getChildFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.medicines_fragment, DetailsFragment.class, null).addToBackStack("details").setReorderingAllowed(true).commit();
        });
    }

    @Override
    public int getItemCount() {
        return medicineList.getMedicineList().size();
    }

    /**
     * Klasa przedstawiająca komponent wyświetlający wiersz listy leków.
     */
    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView nameTextView;
        public TextView priceRangeTextView;
        public RatingBar averageRating;
        public Button messageButton;
        public Context context;

        public ViewHolder(@NonNull Context context, @NonNull View itemView) {
            super(itemView);

            nameTextView = (TextView) itemView.findViewById(R.id.medicine_name);
            priceRangeTextView = (TextView) itemView.findViewById(R.id.medicine_price_range);
            averageRating = (RatingBar) itemView.findViewById(R.id.medicine_average_rating);
            messageButton = (Button) itemView.findViewById(R.id.details_button);
            this.context = context;
        }
    }
}
