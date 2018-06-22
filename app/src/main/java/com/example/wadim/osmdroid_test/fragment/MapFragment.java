package com.example.wadim.osmdroid_test.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.wadim.osmdroid_test.R;
import com.example.wadim.osmdroid_test.Route;
import com.example.wadim.osmdroid_test.app.MyApplication;
import com.example.wadim.osmdroid_test.helper.MyOwnLocationOverlay;
import com.example.wadim.osmdroid_test.helper.RouteGeoPoint;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.osmdroid.api.IGeoPoint;
import org.osmdroid.api.IMapController;
import org.osmdroid.bonuspack.kml.KmlDocument;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.FolderOverlay;
import org.osmdroid.views.overlay.MinimapOverlay;
import org.osmdroid.views.overlay.Polygon;
import org.osmdroid.views.overlay.Polyline;
import org.osmdroid.views.overlay.gridlines.LatLonGridlineOverlay2;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;
import org.osmdroid.views.overlay.simplefastpoint.LabelledGeoPoint;
import org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlay;
import org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlayOptions;
import org.osmdroid.views.overlay.simplefastpoint.SimplePointTheme;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.example.wadim.osmdroid_test.fragment.SettingsFragment.GRID_FIELD;
import static com.example.wadim.osmdroid_test.fragment.SettingsFragment.PREFERENCES_NAME;
import static com.example.wadim.osmdroid_test.fragment.SettingsFragment.PREFERENCES_TEXT_FIELD;


public class MapFragment extends Fragment  {
    private SharedPreferences mPrefs;
    private MapView map;
    private MyOwnLocationOverlay mLocationOverlay;
    private MinimapOverlay mMinimapOverlay;
    public List<Route> itemsList;


    public MapFragment() {
        // Required empty public constructor
    }

    public static MapFragment newInstance(String param1, String param2) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fetchStoreItems();
        //Note! we are programmatically construction the map view
        //be sure to handle application lifecycle correct (see note in on pause)
        map = new MapView(inflater.getContext());
        // Call this method to turn off hardware acceleration at the View level but only if you run into problems ( please report them too!)
        // setHardwareAccelerationOff();
        //update, no longer needed, the mapView is hardware acceleration off by defaul tnow

        return map;
    }

    public  void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final Context context = this.getActivity();
        final DisplayMetrics dm = context.getResources().getDisplayMetrics();
        Configuration.getInstance().load(context, PreferenceManager.getDefaultSharedPreferences(context));

        map.setTileSource(TileSourceFactory.OpenTopo);

        map.setBuiltInZoomControls(false);
        map.setMultiTouchControls(true);

        IMapController mapController = map.getController();
        mapController.setZoom(13);
        initMyLocationNewOverlay();
        mapController.setCenter(((MyApplication) getActivity().getApplication()).getGeoPoint());

        SharedPreferences preferences = this.getActivity().getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        Integer showGrid = preferences.getInt(GRID_FIELD,0);
        if(showGrid == 1){
            LatLonGridlineOverlay2 overlay = new LatLonGridlineOverlay2();
            map.getOverlays().add(overlay);
        }

        mMinimapOverlay = new MinimapOverlay(context, map.getTileRequestCompleteHandler());
        mMinimapOverlay.setWidth(dm.widthPixels / 5);
        mMinimapOverlay.setHeight(dm.heightPixels / 5);
        mMinimapOverlay.setPadding(50);
        mMinimapOverlay.setZoomDifference(4);
        map.getOverlays().add(this.mMinimapOverlay);
    }

    private void initMyLocationNewOverlay() {
        GpsMyLocationProvider provider = new GpsMyLocationProvider(getActivity());
        provider.addLocationSource(LocationManager.NETWORK_PROVIDER);
        mLocationOverlay = new MyOwnLocationOverlay(provider, map);
        mLocationOverlay.setDrawAccuracyEnabled(true);
        mLocationOverlay.enableMyLocation();
        //mLocationOverlay.disableFollowLocation();
        //mLocationOverlay.setDrawAccuracyEnabled(true);

        SharedPreferences preferences = this.getActivity().getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        Integer textFromPreferences = Integer.parseInt(preferences.getString(PREFERENCES_TEXT_FIELD, "5"));
        mLocationOverlay.setKmeters(textFromPreferences);

        mLocationOverlay.runOnFirstFix(new Runnable()
        { public void run()
            {
                ((MyApplication) getActivity().getApplication()).setLat( mLocationOverlay.getMyLocation().getLatitude());
                ((MyApplication) getActivity().getApplication()).setLon( mLocationOverlay.getMyLocation().getLongitude());
                IMapController mapController = map.getController();
                mapController.animateTo(mLocationOverlay.getMyLocation());
            }
        });
        map.getOverlays().add(mLocationOverlay);
        map.invalidate();
    }

    private void fetchStoreItems() {
        JsonArrayRequest request = new JsonArrayRequest("http://ec2-18-197-4-23.eu-central-1.compute.amazonaws.com/api/routes/",
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (response == null || response.length() == 0) {
                            Toast.makeText(getActivity(), "Couldn't fetch the store items! Pleas try again.", Toast.LENGTH_LONG).show();
                            return;
                        }
                        String test = response.toString();
                        List<Route> routes= new Gson().fromJson(
                                test,
                                new TypeToken<List<Route>>() {
                        }.getType());

                        List<IGeoPoint> points = new ArrayList<>();
                        for (Iterator<Route> i = routes.iterator(); i.hasNext();) {
                            Route route = i.next();
                            points.add(new RouteGeoPoint(route));
                        }

                        SimplePointTheme pt = new SimplePointTheme(points, true);

                        Paint textStyle = new Paint();
                        textStyle.setStyle(Paint.Style.FILL);
                        textStyle.setColor(Color.parseColor("#0000ff"));
                        textStyle.setTextAlign(Paint.Align.CENTER);
                        textStyle.setTextSize(24);

                        SimpleFastPointOverlayOptions opt = SimpleFastPointOverlayOptions.getDefaultStyle()
                                .setAlgorithm(SimpleFastPointOverlayOptions.RenderingAlgorithm.MAXIMUM_OPTIMIZATION)
                                .setRadius(9).setIsClickable(true).setCellSize(25).setTextStyle(textStyle);

                        final SimpleFastPointOverlay sfpo = new SimpleFastPointOverlay(pt, opt);
                        sfpo.setOnClickListener(new SimpleFastPointOverlay.OnClickListener() {
                            @Override
                            public void onClick(SimpleFastPointOverlay.PointAdapter points, Integer point) {
                                int routeId = ((RouteGeoPoint) points.get(point)).getId();
                                Fragment fragment = DetailsFragment.newInstance(routeId, 0);
                                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                transaction.replace(R.id.frame_container, fragment);
                                transaction.addToBackStack(null);
                                transaction.commit();
                                ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
                                actionBar.setTitle("Route Details");
                            }
                        });
                        map.getOverlays().add(sfpo);
                        map.invalidate();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // error in getting json
                Log.e("UNITOPO", "Error: " + error.getMessage());
                Toast.makeText(getActivity(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        MyApplication instance = MyApplication.getInstance();
        instance.addToRequestQueue(request);
    }
}
