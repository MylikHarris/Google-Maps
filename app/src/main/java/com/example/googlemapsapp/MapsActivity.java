package com.example.googlemapsapp;

import androidx.fragment.app.FragmentActivity;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.googlemapsapp.databinding.ActivityMapsBinding;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private List addresses;
    private Geocoder gcd;
    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        addresses = new ArrayList();
        gcd = new Geocoder(this, Locale.getDefault());
    }

    String getLocalityAndCountry(LatLng position){
        String myLocalityAndCountry ="";

        try {
            addresses = gcd.getFromLocation(position.latitude, position.longitude, 1);
            if (!addresses.isEmpty()) {
                Address myAddress = (Address) addresses.get(0);

                String locality = myAddress.getLocality() == null ?
                        "Somewhere" : myAddress.getLocality();
                String country = myAddress.getCountryName() == null ?
                        "Unrecognizable" : myAddress.getCountryName();
                myLocalityAndCountry = locality + "," + country;
            }
        } catch (IOException e) {

        }

        return myLocalityAndCountry;
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-33.868820, 151.209290);
        String stringForSidney = getLocalityAndCountry(sydney);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in " + stringForSidney));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}