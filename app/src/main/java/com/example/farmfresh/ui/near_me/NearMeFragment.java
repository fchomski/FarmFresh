package com.example.farmfresh.ui.near_me;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.farmfresh.R;
import com.example.farmfresh.model.data.Connect;
import com.example.farmfresh.model.data.data.Coordinate;
import com.example.farmfresh.model.data.data.Item;
import com.example.farmfresh.model.data.data.User;
import com.example.farmfresh.model.data.enums.Key;

import org.json.JSONException;
import org.osmdroid.api.IMapController;
import org.osmdroid.config.IConfigurationProvider;
import org.osmdroid.config.Configuration;
import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;

import static com.example.farmfresh.model.data.enums.Key.Coordinate.LAT;
import static com.example.farmfresh.model.data.enums.Key.Coordinate.LNG;

public class NearMeFragment extends Fragment {
    private ArrayList<User> sellerList;
    private MapView mMap;
    private GeoPoint startPoint;
    private IMapController mapController;
    private IConfigurationProvider provider;

    private MapEventsReceiver mReceiver;

    private ArrayList<Pair<Marker, User>> markderList ;
    private Button searchBtn;
    private EditText searchWord;

    private LinearLayout gallery;

    private TextView farmerName;
    private CardView card;

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
        markderList = new ArrayList<>();
        mMap = (MapView) root.findViewById(R.id.map);
        searchBtn = (Button) root.findViewById(R.id.nearmeSearchBtn);
        searchWord = (EditText) root.findViewById(R.id.nearmeSearchInput);
        farmerName = (TextView) root.findViewById(R.id.nearMeFarmerName);
        card = (CardView) root.findViewById(R.id.nearme_card);
        gallery = (LinearLayout) root.findViewById(R.id.nearme_linear);

        // setup

        card.setVisibility(View.INVISIBLE);
        buttonEventSetup();
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
        Marker marker;
        for (User seller: sellerList) {
            coord = (Coordinate) seller.get(Key.User.USER_LOCATION);
            if (coord != null) {
                point = new GeoPoint(
                        (Double) coord.get(LAT),
                        (Double) coord.get(LNG));

                if (inRange(point, 10.0) && point != null) {
                    marker = newMarker(point);
                    markerEventRegister(marker, seller);
                    markderList.add(
                            new Pair(marker, seller));
                }
            }
            // seller with no coordinate get ignored.
        }
    }

    private void markerEventRegister(Marker marker, final User user) {
        marker.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
            // onclick show the farm info
            //todo
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public boolean onMarkerClick(Marker marker, MapView mapView) {
                showCard(user);
                return false;
            }
        });
    }

    private void buttonEventSetup() {
        // search event
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View e) {
                InputMethodManager im = (InputMethodManager)
                        getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                userOnCard = searchSeller(searchWord.getText().toString());
                showCard(userOnCard);
                im.hideSoftInputFromWindow(
                        getActivity().getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }
        });
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void showCard(final User user) {

        userOnCard = user;
        farmerName.setText(user.get(Key.User.USER_NAME).toString());
        ArrayList<Item> items = new ArrayList<>();
        try {
            Connect c = new Connect(getContext());
            items = c.getSellerItems(userOnCard);
            gallerySetup(items);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        }

        System.out.println(items.toString());

        card.setVisibility(View.VISIBLE);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void gallerySetup(ArrayList<Item> items) {
        gallery.removeAllViews();
        int sz = items.size();
        ArrayList<HashMap<String, Object>> list = new ArrayList<>();
        ArrayList<Bitmap> itemImages = new ArrayList<>();

        // create image list
        for (int i = 0; i < sz; ++i) {
            Item item = items.get(i);
            ImageView newImgView = new ImageView(root.getContext());

            String base64Image = (String) item.get(Key.Item.IMAGE_BASE64);
            newImgView.setPadding(2, 2, 2, 2);
            byte[] byteArray = base64ImgToByteArray(base64Image);
            newImgView.setImageBitmap(BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length));
            gallery.addView(newImgView);
            newImgView.requestLayout();
            newImgView.getLayoutParams().height = 300;
            newImgView.getLayoutParams().width = 300;
            newImgView.setScaleType(ImageView.ScaleType.FIT_XY);

        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private byte[] base64ImgToByteArray(String base64Image) {
        Base64.Decoder decoder = Base64.getDecoder();
        return decoder.decode(base64Image);
    }
}
