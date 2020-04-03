package com.example.farmfresh.ui.home;

import android.os.Bundle;

import com.example.farmfresh.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class HomeViewModel extends AppCompatActivity {
    SupportMapFragment mapFragment;
    GoogleMap gMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home);

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map) ;
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                gMap = googleMap;
                LatLng lc1 = new LatLng(49.877688, -119.435542);
                MarkerOptions m1 = new MarkerOptions();
                m1.position(lc1);
                m1.title("Kelowna Farmers' and Crafters' Market");
                gMap.addMarker(m1);

                gMap.animateCamera((CameraUpdateFactory.newLatLngZoom(lc1,12)));
            }
        });

    }
}