package com.example.farmfresh.ui.maps;


import android.os.Bundle;
import android.preference.PreferenceManager;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.example.farmfresh.R;
import com.example.farmfresh.ui.near_me.NearMeViewModel;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.config.IConfigurationProvider;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

public class PinLocationMap extends FragmentActivity {
    private NearMeViewModel mViewModel;
    private MapView mMap;
    private GeoPoint startPoint;
    private IMapController mapController;
    private IConfigurationProvider provider;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_on_map);
    }

    private void mapSetUp() {
        mMap.setTileSource(TileSourceFactory.MAPNIK);
        mMap.setMinZoomLevel(6.0);
        mMap.setMaxZoomLevel(25.0);
        mMap.setMultiTouchControls(true);
        mMap.setBuiltInZoomControls(true);

        provider = Configuration.getInstance();
        provider.load(this, PreferenceManager.getDefaultSharedPreferences(this));

        startPoint = new GeoPoint(49.88, -119.49);

        mapController = mMap.getController();
        mapController.setCenter(startPoint);
        mapController.setZoom(15);
    }

    @Override
    public void onResume() {
        super.onResume();
        mMap.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMap.onPause();
    }
}
