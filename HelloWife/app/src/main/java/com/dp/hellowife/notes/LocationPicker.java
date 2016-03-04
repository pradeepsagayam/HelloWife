package com.dp.hellowife.notes;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.dp.hellowife.R;
import com.dp.hellowife.utils.PermissionUtils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

/**
 * Created by akshayas on 2/10/2016.
 */
public class LocationPicker extends AppCompatActivity {

    private GoogleMap googleMap;
    UiSettings mapUiSettings;

    LocationManager locationManager;

    Geocoder geocoder;
    List<Address> addresses;
    String address, city, state, country, postalCode, knownName;

    Handler handler;

    /**
     * Request code for location permission request.
     *
     * @see #onRequestPermissionsResult(int, String[], int[])
     */
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;


    /**
     * Flag indicating whether a requested permission has been denied after returning in
     * {@link #onRequestPermissionsResult(int, String[], int[])}.
     */
    private boolean mPermissionDenied = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_picker);
        geocoder = new Geocoder(this);
        handler = new Handler();
        setupLocationPicker();
    }

    private void setupLocationPicker() {
        try {
            initializeGoogleMaps();

            displayCurrentLocation();

            if (googleMap.isMyLocationEnabled()) {
                setMyLocationButtonClickListener();
            }
            setMapClickListener();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void displayCurrentLocation() {
        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        Location currentLocation = null;
        try {
            String bestProvider = locationManager.getBestProvider(criteria, true);
            if (null != bestProvider)
                currentLocation = locationManager.getLastKnownLocation(bestProvider);
        } catch (SecurityException e) {
            e.printStackTrace();
        }

        if (null != currentLocation) {
            double latitude = currentLocation.getLatitude();
            double longitude = currentLocation.getLongitude();
            String place=getAddress(latitude, longitude);
            drawMarker(new LatLng(latitude, longitude),place);
        }
    }

    private void initializeGoogleMaps() {
        if (null == googleMap) {
            googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        }
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        enableMyLocation();
        setMapControls();

    }

    private void setMapControls() {
        mapUiSettings = googleMap.getUiSettings();
        mapUiSettings.setZoomGesturesEnabled(true);
        mapUiSettings.setRotateGesturesEnabled(true);
        mapUiSettings.setTiltGesturesEnabled(true);
        mapUiSettings.setZoomControlsEnabled(true);
    }

    /**
     * Enables the My Location layer if the fine location permission has been granted.
     */
    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    android.Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else if (googleMap != null) {
            // Access to the location has been granted to the app.
            googleMap.setMyLocationEnabled(true);
        }
    }

    private void setMapClickListener() {
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                final String place = getAddress(latLng.latitude, latLng.longitude);
                drawMarker(latLng, place);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        sendPlaceToNotesActivity(place);
                    }
                }, 2000);
            }
        });
    }

    private void setMyLocationButtonClickListener() {
        googleMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                LatLng coordinates = new LatLng(googleMap.getMyLocation().getLatitude(), googleMap.getMyLocation().getLongitude());
                final String place = getAddress(coordinates.latitude, coordinates.longitude);
                drawMarker(coordinates, place);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        sendPlaceToNotesActivity(place);
                    }
                }, 2000);
                // Return false so that we don't consume the event and the default behavior still occurs
                // (the camera animates to the user's current position).
                return false;
            }
        });
    }

    private void drawMarker(LatLng latLng, String title) {
        googleMap.clear();
        MarkerOptions marker = new MarkerOptions().position(latLng);
        marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE));
        marker.title(title);
        googleMap.addMarker(marker);
        CameraPosition cameraPos = new CameraPosition.Builder().target(latLng).zoom(15).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPos));
    }

    private String getAddress(Double latitude, Double longitude) {

        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            if (addresses.size() > 0) {
                address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                city = addresses.get(0).getLocality();
                state = addresses.get(0).getAdminArea();
                country = addresses.get(0).getCountryName();
                postalCode = addresses.get(0).getPostalCode();
                knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL
            } else {
                Log.v("LocationPicker", "Cannot fetch address..");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return address +", " +city;
    }

    private void sendPlaceToNotesActivity(String place) {
        Intent intent = new Intent();
        intent.putExtra("place", place);
        setResult(RESULT_OK, intent);
        finish();
    }


    @Override
    protected void onResume() {
        super.onResume();
        initializeGoogleMaps();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                android.Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
            enableMyLocation();
        } else {
            // Display the missing permission error dialog when the fragments resume.
            mPermissionDenied = true;
        }
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (mPermissionDenied) {
            // Permission was not granted, display error dialog.
            showMissingPermissionError();
            mPermissionDenied = false;
        }
    }

    /**
     * Displays a dialog with error message explaining that the location permission is missing.
     */
    private void showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog
                .newInstance(true).show(getSupportFragmentManager(), "dialog");
    }
}
