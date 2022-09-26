package com.cielicki.dominik.allergyapp.ui.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.cielicki.dominik.allergyapp.R;
import com.cielicki.dominik.allergyapp.common.SettingsEnum;
import com.cielicki.dominik.allergyapp.common.Utils;
import com.cielicki.dominik.allergyapp.common.VoivodeshipEnum;
import com.cielicki.dominik.allergyapp.databinding.FragmentSettingsBinding;
import com.cielicki.dominik.allergyapp.ui.home.HomeViewModel;
import com.cielicki.dominik.allergyapprestapi.db.UserSettings;

/**
 * Klasa implementująca zachowanie zakladki ustawień.
 */
public class SettingsFragment extends Fragment {

    private SettingsViewModel settingsViewModel;
    private HomeViewModel homeViewModel;
    private FragmentSettingsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        settingsViewModel = new ViewModelProvider(this).get(SettingsViewModel.class);
        homeViewModel = new ViewModelProvider(getActivity()).get(HomeViewModel.class);

        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Spinner voivodeshipList = binding.settingVoivodeshipSpinner;
        ArrayAdapter<VoivodeshipEnum> adapter = new ArrayAdapter<VoivodeshipEnum>(getContext(), R.layout.simple_spinner_item);
        adapter.addAll(VoivodeshipEnum.values());
        voivodeshipList.setAdapter(adapter);

        UserSettings generalCharSetting = homeViewModel.getUserSettingsHashMap().get(SettingsEnum.GENERAL_CHAT);

        if (generalCharSetting == null) {
            binding.settingGeneralChat.setChecked(true);

        } else if (generalCharSetting.getValue().equals("0")) {
            binding.settingGeneralChat.setChecked(false);

        } else {
            binding.settingGeneralChat.setChecked(true);
        }

        UserSettings startLocationSetting = homeViewModel.getUserSettingsHashMap().get(SettingsEnum.START_LOCATION);

        if (startLocationSetting != null) {
            VoivodeshipEnum[] voivodeshipEnums = VoivodeshipEnum.values();

            for (int i = 0; i < voivodeshipEnums.length; i++) {
                if (startLocationSetting.getValue().equals(voivodeshipEnums[i].voivodeship.getId().toString())) {
                    voivodeshipList.setSelection(i);
                    break;
                }
            }
        }

        voivodeshipList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                UserSettings userSetting = homeViewModel.getUserSettingsHashMap().get(SettingsEnum.START_LOCATION);

                if (userSetting != null) {
                    VoivodeshipEnum voivodeshipEnum = (VoivodeshipEnum) parent.getItemAtPosition(position);
                    userSetting.setValue(voivodeshipEnum.voivodeship.getId().toString());

                } else {
                    userSetting = new UserSettings();
                    userSetting.getId().setSetting(SettingsEnum.START_LOCATION.getSetting());
                    userSetting.getId().setUser(homeViewModel.getCurrentUser().getValue());

                    VoivodeshipEnum voivodeshipEnum = (VoivodeshipEnum) parent.getItemAtPosition(position);
                    userSetting.setValue(voivodeshipEnum.voivodeship.getId().toString());
                }

                Utils.saveSetting(getContext(), userSetting);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.settingGeneralChat.setOnCheckedChangeListener((buttonView, isChecked) -> {
            UserSettings userSetting = homeViewModel.getUserSettingsHashMap().get(SettingsEnum.GENERAL_CHAT);

            if (isChecked) {
                if (userSetting != null) {
                    userSetting.setValue("1");

                } else {
                    userSetting = new UserSettings();
                    userSetting.getId().setSetting(SettingsEnum.GENERAL_CHAT.getSetting());
                    userSetting.getId().setUser(homeViewModel.getCurrentUser().getValue());
                    userSetting.setValue("1");
                    homeViewModel.getUserSettingsHashMap().put(SettingsEnum.GENERAL_CHAT, userSetting);
                }

            } else {
                if (userSetting != null) {
                    userSetting.setValue("0");

                } else {
                    userSetting = new UserSettings();
                    userSetting.getId().setSetting(SettingsEnum.GENERAL_CHAT.getSetting());
                    userSetting.getId().setUser(homeViewModel.getCurrentUser().getValue());
                    userSetting.setValue("0");
                    homeViewModel.getUserSettingsHashMap().put(SettingsEnum.GENERAL_CHAT, userSetting);
                }

            }
            Utils.saveSetting(this.getContext(), userSetting);
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}