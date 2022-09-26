package com.cielicki.dominik.allergyapp.ui.medicines;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cielicki.dominik.allergyapp.R;
import com.cielicki.dominik.allergyapprestapi.db.MedicineComment;
import com.cielicki.dominik.allergyapprestapi.db.model.MedicineCommentList;

import java.text.SimpleDateFormat;

/**
 * Adapter dla RecyclerView wyświetlającego listę komentarzy dla leków.
 */
public class MedicineCommentAdapter extends RecyclerView.Adapter<MedicineCommentAdapter.ViewHolder> {

    private MedicineCommentList medicineCommentList;
    private MedicinesViewModel medicinesViewModel;

    MedicineCommentAdapter(MedicineCommentList medicineCommentList, MedicinesViewModel medicinesViewModel) {
        this.medicineCommentList = medicineCommentList;
        this.medicinesViewModel = medicinesViewModel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View medicineView = layoutInflater.inflate(R.layout.medicine_comment, parent, false);

        return new ViewHolder(context, medicineView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MedicineComment medicineComment = medicineCommentList.getMedicineCommentList().get(position);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        holder.dateTextView.setText(dateFormat.format(medicineComment.getId().getDate()));
        holder.commentTextView.setText(medicineComment.getComment());
        holder.commentRatingBar.setRating(medicineComment.getRating().floatValue());
    }

    @Override
    public int getItemCount() {
        return medicineCommentList.getMedicineCommentList().size();
    }

    /**
     * Klasa przedstawiająca komponent wyświetlający pojedynczy komentarz na liście komentarzy.
     */
    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView dateTextView;
        public TextView commentTextView;
        public RatingBar commentRatingBar;
        public Context context;

        public ViewHolder(@NonNull Context context, @NonNull View itemView) {
            super(itemView);

            dateTextView = (TextView) itemView.findViewById(R.id.medicine_comment_date);
            commentTextView = (TextView) itemView.findViewById(R.id.medicine_comment_comment);
            commentRatingBar = (RatingBar) itemView.findViewById(R.id.medicine_comment_rating);
            this.context = context;
        }
    }
}
