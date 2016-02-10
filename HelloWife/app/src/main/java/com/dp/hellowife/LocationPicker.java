package com.dp.hellowife;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.dp.hellowife.utils.PermissionUtils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

/**
 * Created by akshayas on 2/10/2016.
 */
public class LocationPicker extends AppCompatActivity {

    GoogleMap googleMap;
    LatLng coordinate;
    Geocoder geocoder;
    List<Address> addresses;
    //    LatLng coordinate = new LatLng(13.0827, 80.2707);
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private boolean mPermissionDenied = false;
    String address, city, state, country, postalCode, knownName;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_picker);
        geocoder = new Geocoder(this);
        setUpLocationPicker();
    }

    private void setUpLocationPicker() {
        try {
            if (null == googleMap) {
                googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
            }
            googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            enableMyLocation();
            googleMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
                @Override
                public boolean onMyLocationButtonClick() {
                    Location location = googleMap.getMyLocation();
                    Double latitude = location.getLatitude();
                    Double longitude = location.getLongitude();
                    drawMarker(latitude, longitude, getAddress(latitude, longitude));
                    sendPlaceToNotesActivity(getAddress(latitude, longitude));
                    return true;
                }
            });
            googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {
                    Double latitude = latLng.latitude;
                    Double longitude = latLng.longitude;
                    drawMarker(latitude, longitude, getAddress(latitude, longitude));
                    sendPlaceToNotesActivity(getAddress(latitude, longitude));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendPlaceToNotesActivity(String place) {
        Intent intent = new Intent();
        intent.putExtra("place" , place);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void drawMarker(Double latitude, Double longitude, String title) {
        Toast.makeText(this, "Place : " + title, Toast.LENGTH_SHORT).show();
        coordinate = new LatLng(latitude, longitude);
        googleMap.addMarker(new MarkerOptions().position(coordinate).title(title).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location)));
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(coordinate);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 50));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(10));
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
                System.out.println("AAAA addresses empty...");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return address;
    }

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
