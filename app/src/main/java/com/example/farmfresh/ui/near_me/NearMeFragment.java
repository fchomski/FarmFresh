package com.example.farmfresh.ui.near_me;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModelProviders;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;

import com.example.farmfresh.BuildConfig;
import com.example.farmfresh.R;
import com.example.farmfresh.model.data.Connect;
import com.example.farmfresh.model.data.data.Coordinate;
import com.example.farmfresh.model.data.data.User;
import com.example.farmfresh.model.data.enums.Key;
import com.example.farmfresh.ui.maps.NearMeMap;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.osmdroid.api.IMapController;
import org.osmdroid.config.IConfigurationProvider;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import java.util.ArrayList;
import java.util.prefs.PreferenceChangeEvent;

import static com.example.farmfresh.model.data.enums.Key.Coordinate.LAT;
import static com.example.farmfresh.model.data.enums.Key.Coordinate.LNG;

public class NearMeFragment extends Fragment {
    private ArrayList<User> sellerList;
    private MapView mMap;
    private GeoPoint startPoint;
    private IMapController mapController;
    private IConfigurationProvider provider;

    private ArrayList<Pair<Marker, User>> markderList = new ArrayList<>();
    private Button searchBtn;
    private EditText searchWord;

    private User userOnCard;  // current user get displayed on card.

    private View root;

    public static NearMeFragment newInstance() {
        return new NearMeFragment();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_nearme, container, false);

        searchBtn = (Button) root.findViewById(R.id.nearmeSearchBtn);
        searchWord = (EditText) root.findViewById(R.id.nearmeSearchInput);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View e) {
                userOnCard = searchSeller(searchWord.getText().toString());
            }
        });

        mMap = (MapView) root.findViewById(R.id.map);
        mapSetUp();
        try {
            Connect c = new Connect(root.getContext());
            sellerList = c.getSellers();
            System.out.println(sellerList.toString());
            pinFarms();
            System.out.println(markderList);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        }

        return root;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void mapSetUp() {
        mMap.setTileSource(TileSourceFactory.MAPNIK);
        mMap.setMinZoomLevel(6.0);
        mMap.setMaxZoomLevel(30.0);
        mMap.setBuiltInZoomControls(true);

        provider = Configuration.getInstance();
        provider.load(root.getContext(),
                PreferenceManager.getDefaultSharedPreferences(root.getContext()));

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

                if (inRange(point, 10.0) && point != null) {
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

    private User searchSeller(String name) {
        for (User u : sellerList) {
            if (name.equals(u.get(Key.User.USER_NAME))) {
                return u;
            }
        }
        return null;
    }

    private void ShowSellerInfo() {
        // show the user info on the card beneath.

    }
}
