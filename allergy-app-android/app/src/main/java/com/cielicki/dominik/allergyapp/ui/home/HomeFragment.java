package com.cielicki.dominik.allergyapp.ui.home;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cielicki.dominik.allergyapp.MainActivity;
import com.cielicki.dominik.allergyapp.common.SettingsEnum;
import com.cielicki.dominik.allergyapp.common.VoivodeshipEnum;
import com.cielicki.dominik.allergyapp.databinding.FragmentHomeBinding;
import com.cielicki.dominik.allergyapprestapi.db.UserSettings;
import com.cielicki.dominik.allergyapprestapi.db.Voivodeship;
import com.cielicki.dominik.allergyapprestapi.db.model.VoivodeshipAllergenList;

/**
 * Klasa implementująca zachowanie zakladki domowej.
 */
public class HomeFragment extends Fragment implements View.OnTouchListener {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(getActivity()).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.imageMap.setOnTouchListener(this);

        final RecyclerView recyclerView = binding.allergenRecyclerView;

        homeViewModel.getCurrentVoivodeshipAllergenList().observe(getViewLifecycleOwner(), new Observer<VoivodeshipAllergenList>() {
            @Override
            public void onChanged(VoivodeshipAllergenList voivodeshipAllergenList) {
                AllergenAdapter allergenAdapter = new AllergenAdapter(voivodeshipAllergenList, homeViewModel);
                recyclerView.setAdapter(allergenAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(HomeFragment.this.getContext()));
            }
        });

        homeViewModel.getCurrentVoivodeship().observe(getViewLifecycleOwner(), new Observer<Voivodeship>() {
            @Override
            public void onChanged(Voivodeship voivodeship) {
                binding.voivodeshipLabel.setText(voivodeship.getName());
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            int xCoord = (int) event.getX();
            int yCoord = (int) event.getY();

            ImageView colouredMap = binding.colouredMap;
            Bitmap bitmap = ((BitmapDrawable) colouredMap.getDrawable()).getBitmap();

            double scale = bitmap.getWidth() / (double) binding.imageMap.getWidth();
            int pixel = bitmap.getPixel((int) (scale * xCoord), (int) (scale * yCoord));

            VoivodeshipEnum voivodeshipEnum = VoivodeshipEnum.getVoivodeshipFromColor(pixel);

            if (voivodeshipEnum != null && voivodeshipEnum != VoivodeshipEnum.LOKALIZACJA) {
                homeViewModel.setCurrentVoivodeship(voivodeshipEnum.voivodeship);
            }
        }

        return true;
    }
}