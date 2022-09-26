package com.cielicki.dominik.allergyapp.ui.medicines;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.cielicki.dominik.allergyapp.R;
import com.cielicki.dominik.allergyapp.databinding.DetailsFragmentBinding;
import com.cielicki.dominik.allergyapprestapi.db.model.MedicineCommentList;

/**
 * Klasa implementująca zachowanie w oknie szegółów leku.
 */
public class DetailsFragment extends Fragment {

    private MedicinesViewModel mViewModel;
    private DetailsFragmentBinding binding;

    public static DetailsFragment newInstance() {
        return new DetailsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(getParentFragment()).get(MedicinesViewModel.class);

        binding = DetailsFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        TextView nameView = binding.detailsMedicineName;
        TextView descriptionView = binding.detailsMedicineDescription;

        nameView.setText(mViewModel.getCurrentMedicine().getValue().getName());
        descriptionView.setText(mViewModel.getCurrentMedicine().getValue().getDescription());

        final RecyclerView recyclerView = binding.medicineDetailsRecyclerView;

        mViewModel.getMedicineCommentList().observe(getViewLifecycleOwner(), new Observer<MedicineCommentList>() {
            @Override
            public void onChanged(MedicineCommentList medicineCommentList) {
                MedicineCommentAdapter medicineCommentAdapter = new MedicineCommentAdapter(medicineCommentList, mViewModel);
                recyclerView.setAdapter(medicineCommentAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(DetailsFragment.this.getContext()));
            }
        });

        Button addCommentButton = binding.medicineAddCommentButton;

        addCommentButton.setOnClickListener((view) -> {
            FragmentManager fragmentManager = getParentFragment().getChildFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.medicines_fragment, AddCommentFragment.class, null).addToBackStack("medicine_comment").setReorderingAllowed(true).commit();
        });

        return root;
    }
}