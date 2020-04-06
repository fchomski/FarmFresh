package com.example.farmfresh.ui.maps;


import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;

import com.example.farmfresh.R;
import com.example.farmfresh.model.data.Connect;
import com.example.farmfresh.model.data.data.Coordinate;
import com.example.farmfresh.model.data.enums.Key;
import com.example.farmfresh.model.data.State;
import com.example.farmfresh.model.data.data.User;
import com.example.farmfresh.ui.near_me.NearMeViewModel;

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

import java.util.ArrayList;

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

        locateBtn = (Button) findViewById(R.id.pinLocateBtn);
        locateBtn.setOnClickListener(new View.OnClickListener() {
            // write location change into dataModel.
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View e) {
                // put the new user data with updated coordinate on to the DataModel.

                try {
                    Connect c = new Connect(getApplicationContext());
                    User u = state.getUser();
                    c.remove(Key.User.USER_NAME, u.get(Key.User.USER_NAME), User.class);
                    c.add(u, User.class);
                    c.sync();
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }

                info("Set new farm location");

                if (state.getUser().get(Key.User.USER_LOCATION) != null) finish();
            }
        });
        info("Please tap on the map to select the location of your farm");
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void mapSetUp() {
        mMap.setTileSource(TileSourceFactory.MAPNIK);
        mMap.setMinZoomLevel(6.0);
        mMap.setMaxZoomLevel(30.0);
        mMap.setBuiltInZoomControls(true);

        provider = Configuration.getInstance();
        provider.load(this, PreferenceManager.getDefaultSharedPreferences(this));

        startPoint = new GeoPoint(49.88, -119.49);

        mapController = mMap.getController();
        mapController.setCenter(startPoint);
        mapController.setZoom(16);

    }

    private void info(String msg) {
        Toast toast;
        String toasterMsg;
        toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT);
        toast.setMargin(50, 50);
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
        // update the coordinate of user in State singleton.
        // The coordinate will be synced onFinish
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

