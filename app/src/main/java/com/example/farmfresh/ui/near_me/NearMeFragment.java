package com.example.farmfresh.ui.near_me;

import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;

import com.example.farmfresh.BuildConfig;
import com.example.farmfresh.R;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.IConfigurationProvider;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

import java.util.prefs.PreferenceChangeEvent;

public class NearMeFragment extends Fragment {

    private NearMeViewModel mViewModel;
    private MapView mMap;
    private GeoPoint startPoint;
    private IMapController mapController;
    private IConfigurationProvider provider;

    public static NearMeFragment newInstance() {
        return new NearMeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root;
        root = inflater.inflate(R.layout.fragment_nearme, container, false);
        mMap = (MapView) root.findViewById(R.id.map);
        mapSetUp();

        return root;
    }

    private void mapSetUp() {
        mMap.setTileSource(TileSourceFactory.MAPNIK);
        mMap.setMinZoomLevel(6.0);
        mMap.setMaxZoomLevel(25.0);
        mMap.setMultiTouchControls(true);
        mMap.setBuiltInZoomControls(true);

        provider = Configuration.getInstance();
        provider.load(getContext(), PreferenceManager.getDefaultSharedPreferences(getContext()));

        startPoint = new GeoPoint(49.88, -119.49);

        mapController = mMap.getController();
        mapController.setCenter(startPoint);
        mapController.setZoom(15);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(NearMeViewModel.class);
        // TODO: Use the ViewModel
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
