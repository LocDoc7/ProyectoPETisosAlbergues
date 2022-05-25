package com.ay.proyectopetisos;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ay.proyectopetisos.databinding.ActivityMapsRegBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivityReg extends FragmentActivity implements GoogleMap.OnMarkerDragListener,OnMapReadyCallback {

    private GoogleMap mMap;
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderCLient;
    private Geocoder geocoder;
    Button btn_confirmar;
    private Marker markerActual;
    private static final int REQUEST_CODE = 101;
    private ActivityMapsRegBinding binding;
    private List<Address> address;
    String ubi_descripcion,ubi_long,ubi_lat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsRegBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        btn_confirmar = findViewById(R.id.btn_confirmarubi);
        fusedLocationProviderCLient = LocationServices.getFusedLocationProviderClient(this);
        getLocalizacion();
        btn_confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("ubidescripcion",ubi_descripcion);
                intent.putExtra("latitud",ubi_lat);
                intent.putExtra("longitud",ubi_long);
                setResult(Activity.RESULT_OK,intent);
                finish();
            }
        });

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        /*SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);*/
    }

    private void getLocalizacion() {
        int permiso = ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION);
        if (permiso == PackageManager.PERMISSION_DENIED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_FINE_LOCATION)){
            }else{
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
            }
        }
        Task<Location> task = fusedLocationProviderCLient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null){
                    currentLocation = location;
                    getDireccion(currentLocation.getLatitude(),currentLocation.getLongitude());
                    /*Toast.makeText(MapsActivityReg.this, currentLocation.getLatitude()+" "+currentLocation.getLongitude(), Toast.LENGTH_SHORT).show();*/
                    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                            .findFragmentById(R.id.map);
                    mapFragment.getMapAsync(MapsActivityReg.this);
                }
            }
        });
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        LatLng latLng = new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude());
        markerActual = googleMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .title("Estas aquí")
                    .draggable(true)
        );

        /*MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("Estas aqui");*/
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,17));
        /*googleMap.addMarker(markerOptions);*/
        googleMap.setOnMarkerDragListener(this);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case REQUEST_CODE:
                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    getLocalizacion();
                }
                break;
        }
    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        if(marker.equals(markerActual)){
            getDireccion(marker.getPosition().latitude,marker.getPosition().longitude);
//            Toast.makeText(this, marker.getPosition().latitude+" "+marker.getPosition().longitude, Toast.LENGTH_SHORT).show();
        }
    }

    private void getDireccion(double mLat, double mLng){
        geocoder = new Geocoder(this, Locale.getDefault());
        try {
            address = geocoder.getFromLocation(mLat,mLng,1);
        }catch (IOException e){
            e.printStackTrace();
        }
        if (address != null){
            ubi_lat = String.valueOf(mLat);
            ubi_long = String.valueOf(mLng);
            ubi_descripcion = address.get(0).getAddressLine(0);
            Toast.makeText(this, ubi_descripcion, Toast.LENGTH_SHORT).show();
        }
    }
}