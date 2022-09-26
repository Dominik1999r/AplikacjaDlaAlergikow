package com.cielicki.dominik.allergyapp.ui.medicines;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cielicki.dominik.allergyapp.R;
import com.cielicki.dominik.allergyapp.databinding.FragmentMedicinesBinding;
import com.cielicki.dominik.allergyapprestapi.db.Medicine;
import com.cielicki.dominik.allergyapprestapi.db.model.MedicineList;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

/**
 * Klasa implementująca zachowanie zakladki z lekami.
 */
public class MedicinesFragment extends Fragment {

    private MedicinesViewModel medicinesViewModel;
    private FragmentMedicinesBinding binding;
    private LinearLayoutManager layoutManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        medicinesViewModel =
//                new ViewModelProvider(this).get(MedicinesViewModel.class);
                new ViewModelProvider(getParentFragment()).get(MedicinesViewModel.class);


        binding = FragmentMedicinesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final RecyclerView recyclerView = binding.recyclerView;

        medicinesViewModel.getMedicineList().observe(getViewLifecycleOwner(), new Observer<MedicineList>() {
            @Override
            public void onChanged(MedicineList medicineList) {
                MedicineAdapter medicineAdapter = new MedicineAdapter(medicineList, medicinesViewModel);
                recyclerView.setAdapter(medicineAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(MedicinesFragment.this.getContext()));
            }
        });

        final Spinner spinner = binding.spinner;

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.simple_spinner_item);
        adapter.addAll("Alfabetycznie", "Według ocen");
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (medicinesViewModel.getMedicineList().getValue() == null) {
                    return;
                }

                if (position == 0) {
                    MedicineList medicineList = medicinesViewModel.getMedicineList().getValue();
                    List<Medicine> medicines = medicineList.getMedicineList();
                    Collections.sort(medicines, (o1, o2) -> {
                        return o1.getName().compareTo(o2.getName());
                    });

                    medicinesViewModel.setMedicineList(medicineList);

                } else if (position == 1) {
                    MedicineList medicineList = medicinesViewModel.getMedicineList().getValue();
                    List<Medicine> medicines = medicineList.getMedicineList();
                    Collections.sort(medicines, (o1, o2) -> {
                        BigDecimal value1 = o1.getAverageScore() == null ? new BigDecimal(0) : o1.getAverageScore();
                        BigDecimal value2 = o2.getAverageScore() == null ? new BigDecimal(0) : o2.getAverageScore();

                        return -1 * value1.compareTo(value2);
                    });

                    medicinesViewModel.setMedicineList(medicineList);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}