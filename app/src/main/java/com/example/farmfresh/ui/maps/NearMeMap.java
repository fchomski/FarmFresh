package com.example.farmfresh.ui.maps;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Pair;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;

import com.example.farmfresh.R;
import com.example.farmfresh.model.data.Connect;
import com.example.farmfresh.model.data.data.Coordinate;
import com.example.farmfresh.model.data.data.User;
import com.example.farmfresh.model.data.enums.Key;

import org.json.JSONException;
import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.config.IConfigurationProvider;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.MapEventsOverlay;
import org.osmdroid.views.overlay.Marker;

import java.util.ArrayList;

import static com.example.farmfresh.model.data.enums.Key.Coordinate.LAT;
import static com.example.farmfresh.model.data.enums.Key.Coordinate.LNG;

public class NearMeMap extends FragmentActivity {
    private ArrayList<User> sellerList;
    private MapView mMap;
    private GeoPoint startPoint;
    private IMapController mapController;
    private IConfigurationProvider provider;

    private ArrayList<Pair<Marker, User>> markderList;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            Connect c = new Connect(getApplicationContext());
            sellerList = c.getSellers();
            System.out.println(sellerList.toString());
            pinFarms();
            System.out.println(markderList);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        mMap = (MapView) findViewById(R.id.pinmap);
        mapSetUp();
    }

    private void info(String msg) {
        Toast toast;
        String toasterMsg;
        toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT);
        toast.setMargin(50, 50);
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

    private void pinFarms() {
        Coordinate coord;
        GeoPoint point;
        for (User seller: sellerList) {
            coord = (Coordinate) seller.get(Key.User.USER_LOCATION);
            if (coord != null) {
                point = new GeoPoint(
                        (Double) coord.get(LAT),
                        (Double) coord.get(LNG));

                if (inRange(point, 10.0)) {
                    markderList.add(
                            new Pair(newMarker(point), seller));
                }
            }
            // seller with no coordinate get ignored.
        }
    }

    private boolean inRange(GeoPoint p, Double range) {
        double lat = p.getLatitude();
        double lng = p.getLongitude();
        double centerLat = startPoint.getLatitude();
        double centerLng = startPoint.getLongitude();
        double radius = range / 2.0;

        return  (lat > centerLat - radius  &&
                 lat < centerLat + radius &&
                 lng > centerLng - radius &&
                 lng < centerLng + radius);
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

}
