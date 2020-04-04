package com.example.farmfresh.ui.maps;


import android.graphics.CornerPathEffect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;

import com.example.farmfresh.R;
import com.example.farmfresh.model.data.Connect;
import com.example.farmfresh.model.data.Coordinate;
import com.example.farmfresh.model.data.Key;
import com.example.farmfresh.model.data.State;
import com.example.farmfresh.model.data.User;
import com.example.farmfresh.ui.near_me.NearMeViewModel;
import com.google.android.material.badge.BadgeUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.config.IConfigurationProvider;
import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.MapEventsOverlay;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Overlay;

public class PinLocationMap extends FragmentActivity {
    private NearMeViewModel mViewModel;
    private MapView mMap;
    private GeoPoint startPoint;
    private IMapController mapController;
    private IConfigurationProvider provider;

    private MapEventsReceiver mReceiver;
    private Coordinate c;

    private Marker marker;

    private State state;

    private Button locateBtn;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_on_map);
        state = State.getInstance();

        mMap = (MapView) findViewById(R.id.pinmap);

        mapEventSetup();
        mapSetUp();
        mMap.getOverlays().add(new MapEventsOverlay(mReceiver));

        locateBtn = (Button) findViewById(R.id.locateFarmBtn);
        locateBtn.setOnClickListener(new View.OnClickListener() {
            // write location change into dataModel.
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View e) {
                try {
                    Connect connect = new Connect(getApplicationContext());
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
                finish();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void mapSetUp() {
        mMap.setTileSource(TileSourceFactory.MAPNIK);
        mMap.setMinZoomLevel(6.0);
        mMap.setMaxZoomLevel(25.0);
        mMap.setBuiltInZoomControls(true);

        provider = Configuration.getInstance();
        provider.load(this, PreferenceManager.getDefaultSharedPreferences(this));

        startPoint = new GeoPoint(49.88, -119.49);

        mapController = mMap.getController();
        mapController.setCenter(startPoint);
        mapController.setZoom(15);

        marker = newMarker(startPoint);
    }

    private Marker newMarker(GeoPoint p) {
        Drawable icon;
        Marker m = new Marker(mMap);
        m.setPosition(p);
        mMap.getOverlays().add(m);
        icon = getResources().getDrawable(R.drawable.map_marker);
        icon = new ScaleDrawable(icon, 0, 0.5f, 0.5f).getDrawable();
        m.setIcon(icon);
        return m;
    }

    private void removeMarker(Marker m) {
        mMap.getOverlays().remove(m);
        mMap.invalidate();
    }

    private void mapEventSetup() {
        mReceiver = new MapEventsReceiver() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public boolean singleTapConfirmedHelper(GeoPoint geoPoint) {
                // update marker.
                removeMarker(marker);
                marker = newMarker(geoPoint);

                System.out.println(geoPoint.toString());
                JSONObject obj = new JSONObject();
                try {
                    Connect connect = new Connect(getApplicationContext());
                    obj.put("lat", geoPoint.getLatitude());
                    obj.put("lng", geoPoint.getLongitude());
                    c = new Coordinate().fromJson(obj);
                    // TODO update the data.

                    // update the state of current user.
                    User u = state.getUser();
                    u.put(Key.User.USER_LOCATION, c);
                    state.setUser(u);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return false;
            }

            @Override
            public boolean longPressHelper(GeoPoint geoPoint) {
                return false;
            }
        };

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

