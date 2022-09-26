package com.cielicki.dominik.allergyapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import com.cielicki.dominik.allergyapp.ui.home.HomeViewModel;
import com.cielicki.dominik.allergyapprestapi.db.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.cielicki.dominik.allergyapp.databinding.ActivityMainBinding;

/**
 * Głowna aktywność aplikacji.
 */
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private HomeViewModel homeViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_medicines, R.id.navigation_messages, R.id.navigation_settings)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        homeViewModel.setCurrentUser((User) getIntent().getExtras().get("USER"));
        homeViewModel.setMainActivity(this);
    }

    public void checkGeoPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 100);
        } else {
            homeViewModel.setGeoPermissionGranted(true);
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                homeViewModel.setGeoPermissionGranted(true);

            } else {
                homeViewModel.setGeoPermissionGranted(false);
            }
        }
    }

    @Override
    public void onBackPressed() {
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            if (fragment.isVisible() && hasBackStack(fragment)) {
                if (popFragment(fragment))
                    return;
            }
        }
        super.onBackPressed();
    }

    private boolean hasBackStack(Fragment fragment) {
        FragmentManager childFragmentManager = fragment.getChildFragmentManager();
        return childFragmentManager.getBackStackEntryCount() > 1;
    }

    private boolean popFragment(Fragment fragment) {
        FragmentManager fragmentManager = fragment.getChildFragmentManager();
        for (Fragment childFragment : fragmentManager.getFragments()) {
            if (childFragment.isVisible()) {
                if (hasBackStack(childFragment)) {
                    return popFragment(childFragment);

                } else {
                    fragmentManager.popBackStack();
                    return true;
                }
            }
        }
        return false;
    }
}